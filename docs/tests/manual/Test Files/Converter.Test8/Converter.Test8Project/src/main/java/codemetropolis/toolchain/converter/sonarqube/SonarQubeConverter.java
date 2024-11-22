package codemetropolis.toolchain.converter.sonarqube;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	@Override
	public CdfTree createElements(String url) throws CodeMetropolisException {
		resources.putAll(getResources(url));
		
		CdfTree cdfTree = new CdfTree();	
		Iterator<Integer> iterator = resources.keySet().iterator();
		
		CdfElement rootElement = new CdfElement(ROOT_CONTAINER_NAME, ROOT_CONTAINER_TYPE);
		cdfTree.setRoot(rootElement);
		
		while(iterator.hasNext()){
			Integer rootId = iterator.next();
			SonarResource res = resources.get(rootId);
			if(Scope.PRJ.equals(res.getScope())){
				CdfElement projectElement = createCdfElement(res);
				rootElement.addChildElement(projectElement);
			}
		}
		
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
		Map<Integer, SonarResource> result = new HashMap<>();
		
		SonarClient sonarClient = new SonarClient(url, getParameter(USERNAME_PARAM_NAME), getParameter(PASSWORD_PARAM_NAME));
		sonarClient.init();
		
		String[] projectRegexList = getProjectsInParams();
		List<String> allProjects = sonarClient.getProjectKeys();
		Set<String> projects = new LinkedHashSet<>();
		
		if(projectRegexList.length == 0) {
			projects.addAll(allProjects);
		} else {
			for(String p : allProjects) {
				for(String regex : projectRegexList) {
					if(p.matches(regex)) {
						projects.add(p);
						break;
					}
				}
			}
		}
		
		for(String key : projects) {
			fireConverterEvent(String.format(Resources.get("sonar_downloading_project"), key));
			result.putAll(sonarClient.getProject(key));
		}
		
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
