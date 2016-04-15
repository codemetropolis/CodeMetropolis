package codemetropolis.toolhchain.gui.beans;

import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolchain.gui.metricgenerators.MetricGenerator;

public class ExecutionOptions {
	private String projectName;	
	private MetricGenerator metricGenerator;
	private String mappingXml;
	private String minecraftRoot;
	private boolean showMap;
		
	public ExecutionOptions(String projectName,
			MetricGenerator metricGenerator, String mappingXml,
			String minecraftRoot, boolean showMap) {
		super();
		this.projectName = projectName;		
		this.metricGenerator = metricGenerator;
		this.mappingXml = mappingXml;
		this.minecraftRoot = minecraftRoot;
		this.showMap = showMap;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}	
	public MetricGenerator getMetricGenerator() {
		return metricGenerator;
	}
	public void setMetricGenerator(MetricGenerator metricGenerator) {
		this.metricGenerator = metricGenerator;
	}
	public String getMappingXml() {
		return mappingXml;
	}
	public void setMappingXml(String mappingXml) {
		this.mappingXml = mappingXml;
	}
	public String getMinecraftRoot() {
		return minecraftRoot;
	}
	public void setMinecraftRoot(String minecraftRoot) {
		this.minecraftRoot = minecraftRoot;
	}
	public boolean isShowMap() {
		return showMap;
	}
	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
	}
	
	
	
	
}
