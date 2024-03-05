package codemetropolis.toolchain.converter.sonarqube;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty.Type;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.converter.sonarqube.SonarMetric.MetricType;
import codemetropolis.toolchain.converter.sonarqube.SonarResource.Scope;

public class SonarQubeConverter extends CdfConverter {

	private static final String USERNAME_PARAM_NAME = "username";
	private static final String PASSWORD_PARAM_NAME = "password";
	private static final String SPLIT_DIRS_PARAM_NAME = "splitDirs";
	private static final String PROJECTS_PARAM_NAME = "projects";
	private static final String ROOT_CONTAINER_NAME = "projects";
	private static final String ROOT_CONTAINER_TYPE = "container";
	
	private Map<Integer, SonarResource> resources;
	private Map<Integer, CdfElement> cdfElements;
	
	public SonarQubeConverter(Map<String, String> params) {
		super(params);
		resources = new HashMap<>();
		cdfElements = new HashMap<>();
	}
/**
 * Method for getting the projects and turning them into cdfelements, returning a tree
 * containing them all.
 * */
	@Override
	public CdfTree createElements(String url) throws CodeMetropolisException {
		resources.putAll(getResources(url));

		List<SonarResource> projectResources = new ArrayList<>();
		List<CdfElement> processedProjects;

		CdfTree cdfTree = new CdfTree();	
		Iterator<Integer> iterator = resources.keySet().iterator();
		
		CdfElement rootElement = new CdfElement(ROOT_CONTAINER_NAME, ROOT_CONTAINER_TYPE);
		cdfTree.setRoot(rootElement);
		
		while(iterator.hasNext()){
			Integer rootId = iterator.next();
			SonarResource res = resources.get(rootId);
			if(Scope.PRJ.equals(res.getScope())){
				projectResources.add(res);
			}
		}
		Stream<SonarResource> resourceStream = projectResources.stream();
		if(projectResources.size() > (Runtime.getRuntime().availableProcessors()) * 10){ // not a computationally intensive process, parallelization only provides an advantage when a large number of projects is being processed
			resourceStream = resourceStream.parallel();
		}
		processedProjects = resourceStream.map(this::createCdfElement).collect(Collectors.toList());
		// in some cases, a SonarResource in projectResources may have a null element in childIdList causing the above line to fail. This is (most likely) caused by parallel downloading resulting in faulty data. Rerunning the tool usually fixes this issue

		for (CdfElement projectElement : processedProjects) {
			rootElement.addChildElement(projectElement);
		}
		System.gc();
		// when ran in parallel, threads sometimes free up slowly, making the next step in the converting process slow
		// (in some cases making it take longer to finish printing the result to file, negating the advantage won with parallel processing the projects)
		// calling gc fixes this issue in most cases

		String splitDirsParam = getParameter(SPLIT_DIRS_PARAM_NAME);
		if(splitDirsParam != null && Boolean.valueOf(splitDirsParam)) {
			processDirHierarchy(cdfTree);
		}
		
		return cdfTree;
	}
	
	private String[] getProjectsInParams() {
		String projectStr = getParameter(PROJECTS_PARAM_NAME);
		if(projectStr == null) return new String[0];
		return projectStr.split(",");
	}
	
	private Map<Integer, SonarResource> getResources(String url) throws SonarConnectException {
		Map<Integer, SonarResource> result;

		SonarClient sonarClient = new SonarClient(url, getParameter(USERNAME_PARAM_NAME), getParameter(PASSWORD_PARAM_NAME));
		sonarClient.init();
		
		String[] projectRegexList = getProjectsInParams();
		List<String> allProjects = sonarClient.getProjectKeys();
		Set<String> projects;

		if(projectRegexList.length == 0) {
			projects = new LinkedHashSet<>(allProjects);
		} else {
			projects = allProjects.stream().filter(p -> {
				for (String regex : projectRegexList) {
					if(p.matches(regex))
						return true;
				}
				return false;
			}).collect(Collectors.toSet());
		}

		Stream<String> projectStream = projects.stream();
		if(projects.size() > (Runtime.getRuntime().availableProcessors() * 2)){
			// usefulness of parallelization heavily depends on project size, which is unknown at this point.
			projectStream = projectStream.parallel();
		}
		result = projectStream
				.peek(projectKey -> fireConverterEvent(String.format(Resources.get("sonar_downloading_project"), projectKey)))
				.map(projectKey -> {
					try {
						return sonarClient.getProject(projectKey);
					} catch (SonarConnectException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(HashMap::new, HashMap::putAll, Map::putAll);
		
		return result;
	}
	
	private CdfElement createCdfElement(SonarResource resource){
		CdfElement cdfElement = new CdfElement();
		cdfElement.setName(resource.getName());	
		cdfElement.setType(resource.getScope().toString());
		cdfElement.setSourceId(String.valueOf(resource.getId()));
		cdfElement.addProperty("key", resource.getKey(), Type.STRING);
		addCdfProperties(cdfElement, resource.getMetrics());
		setChildren(cdfElement, resource);
		cdfElements.put(resource.getId(), cdfElement);
		
		return cdfElement;
	}
	
	private void addCdfProperties(CdfElement cdfElement, List<SonarMetric> metrics){
		for(SonarMetric metric : metrics){
			cdfElement.addProperty(metric.getName(), metric.getValue(), getMetricType(metric.getType()));
		}
	}
	
	private Type getMetricType(MetricType type){
		switch(type){
			case INT:
				return Type.INT;
			case FLOAT:
			case PERCENT:
			case MILLISEC:
				return Type.FLOAT;
			case DATA:
				return Type.STRING;
			default:
				return Type.STRING;
		}
	}
	
	private void setChildren(CdfElement cdfElement, SonarResource resource){
		if(cdfElements.containsKey(resource.getId())){
			CdfElement child = cdfElements.get(resource.getId());
			cdfElement.addChildElement(child);
		} else {
			for(Integer ids : resource.getChildIdList()){
				CdfElement child = createCdfElement(resources.get(ids));
				cdfElement.addChildElement(child);
			}
		}			
	}
	
	private void processDirHierarchy(CdfTree cdfTree) {
		CdfTree.Iterator it = cdfTree.iterator();
		while(it.hasNext()) {
			
			CdfElement prj = it.next();
			if(!"prj".equalsIgnoreCase(prj.getType())) {
				continue;
			}
			
			String prjKey = prj.getPropertyValue("key");
			for(CdfElement child : prj.getChildElements()) {
				
				String childKey = child.getPropertyValue("key");	
				String[] nodeNames = childKey.replaceFirst(prjKey + ":", "").split("/");
				if(nodeNames.length < 2) continue;
				
				CdfElement dir = prj;
				int i = 0;
				while(i < nodeNames.length - 1) {
					CdfElement matchingChild = getChildByName(dir, nodeNames[i]);
					if(matchingChild == null) break;
					dir = matchingChild;
					i++;
				}
				
				while(i < nodeNames.length - 1) {
					CdfElement newElement = new CdfElement(nodeNames[i], "dir");
					dir.addChildElement(newElement);
					dir = newElement;
					i++;
				}
				
				prj.removeChildElement(child);
				child.setName(nodeNames[nodeNames.length - 1]);
				dir.addChildElement(child);
			}	
		}
	}
	
	private CdfElement getChildByName(CdfElement parent, String name) {
		for(CdfElement child : parent.getChildElements()) {
			if(child.getName().equals(name)) return child;
		}
		return null;
	}
	
}
