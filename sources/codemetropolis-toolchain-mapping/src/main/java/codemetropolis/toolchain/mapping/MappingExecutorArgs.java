package codemetropolis.toolchain.mapping;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;

public class MappingExecutorArgs extends ExecutorArgs {
	
	private String cdfFile;
	private String outputFile;
	private String mappingFile;
	private double scale;
	private boolean hierarchyValidation;
	
	public MappingExecutorArgs(String cdfFile, String outputFile, String mappingFile, double scale, boolean hierarchyValidation) {
		super();
		this.cdfFile = cdfFile;
		this.outputFile = outputFile;
		this.mappingFile = mappingFile;
		this.scale = scale;
		this.hierarchyValidation = hierarchyValidation;
	}

	public String getCdfFile() {
		return cdfFile;
	}
	
	public String getOutputFile() {
		return outputFile;
	}
	
	public String getMappingFile() {
		return mappingFile;
	}
	
	public double getScale() {
		return scale;
	}
	
	public boolean isHierarchyValidationEnabled() {
		return hierarchyValidation;
	}
	
}
