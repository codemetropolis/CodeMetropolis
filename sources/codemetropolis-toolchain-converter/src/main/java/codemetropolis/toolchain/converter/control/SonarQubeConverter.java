package codemetropolis.toolchain.converter.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cdf.CdfConverter;
import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty.Type;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.sonarqube.SonarClient;
import codemetropolis.toolchain.commons.sonarqube.SonarMetric;
import codemetropolis.toolchain.commons.sonarqube.SonarMetric.MetricType;
import codemetropolis.toolchain.commons.sonarqube.SonarResource;
import codemetropolis.toolchain.commons.sonarqube.SonarResource.Scope;

public class SonarQubeConverter  implements CdfConverter {

	private Map<Integer, SonarResource> resources;
	private Map<Integer, CdfElement> cdfElements;
	
	
	public SonarQubeConverter(){
		resources = new HashMap<>();
		cdfElements = new HashMap<>();
	}

	@Override
	public CdfTree createElements() {
		resources.putAll(getResources());
		
		CdfTree cdfTree = new CdfTree();	
		Iterator<Integer> iterator = resources.keySet().iterator();
		
		while(iterator.hasNext()){
			Integer rootId = iterator.next();
			SonarResource res = resources.get(rootId);
			if(Scope.PRJ.equals(res.getScope())){
				CdfElement rootElement = createCdfElement(res);
				cdfTree.setRoot(rootElement);
			}
		}
		
		return cdfTree;
	}
	
	
	private Map<Integer, SonarResource> getResources() {
		Map<Integer, SonarResource> map = new HashMap<>();
		
		SonarClient sonarClient = new SonarClient("http://10.6.13.7:9000/api/");
		try {
			map.putAll(sonarClient.getProject("log4j"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private CdfElement createCdfElement(SonarResource resource){
		CdfElement cdfElement = new CdfElement();
		
		cdfElement.setName(resource.getName());	
		cdfElement.setType(resource.getScope().toString());

		addCdfProperties(cdfElement, resource.getMetricNamesandValues());
		setChildrend(cdfElement, resource);
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
	
	private void setChildrend(CdfElement cdfElement, SonarResource resource){
		if(cdfElements.containsKey(resource.getId())){
			CdfElement child = cdfElements.get(resource.getId());
			cdfElement.addChildElement(child);
		} else {
			for(Integer ids : resource.getChildrenResources()){
				CdfElement child = createCdfElement(resources.get(ids));
				cdfElement.addChildElement(child);
			}
		}
				
	}
	
	
}
