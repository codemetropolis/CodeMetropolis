package codemetropolis.toolchain.mapping;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;

public class MappingExecutorArgs extends ExecutorArgs {
	
	private String graphFile;
	private String outputFile;
	private String mappingFile;
	private double scale;
	private boolean showNested;
	
	public MappingExecutorArgs(String graphFile, String outputFile, String mappingFile, double scale, boolean showNested) {
		super();
		this.graphFile = graphFile;
		this.outputFile = outputFile;
		this.mappingFile = mappingFile;
		this.scale = scale;
		this.showNested = showNested;
	}

	public String getGraphFile() {
		return graphFile;
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
	
	public boolean isShowNested() {
		return showNested;
	}
	
}
