package codemetropolis.toolchain.converter.sonarqube;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cdf.CdfConverter;
import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty.Type;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;
import codemetropolis.toolchain.converter.sonarqube.SonarMetric.MetricType;
import codemetropolis.toolchain.converter.sonarqube.SonarResource.Scope;

public class SonarQubeConverter extends CdfConverter {

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
		
		return cdfTree;
	}
	
	private String[] getProjectsInParams() {
		String projectStr = getParameter(PROJECTS_PARAM_NAME);
		if(projectStr == null) return new String[0];
		return projectStr.split(",");
	}
	
	private Map<Integer, SonarResource> getResources(String url) throws SonarConnectException {
		Map<Integer, SonarResource> result = new HashMap<>();
		
		SonarClient sonarClient = new SonarClient(url);
		sonarClient.init();
		String[] projects = getProjectsInParams();
		if(projects.length == 0) {
			projects = sonarClient.getProjectKeys();
		}
		
		for(String key : projects) {
			result.putAll(sonarClient.getProject(key));
		}
		
		return result;
	}
	
	private CdfElement createCdfElement(SonarResource resource){
		CdfElement cdfElement = new CdfElement();
		cdfElement.setName(resource.getName());	
		cdfElement.setType(resource.getScope().toString());
		cdfElement.setSourceId(String.valueOf(resource.getId()));
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
	
}
