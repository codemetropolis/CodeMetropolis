package codemetropolis.toolchain.placing;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;

public class PlacingExecutorArgs extends ExecutorArgs {
	
	private String inputFile;
	private String outputFile;
	private String layout;
	private boolean showMap;
	
	public PlacingExecutorArgs(String inputFile, String outputFile, String layout, boolean showMap) {
		super();
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.layout = layout;
		this.showMap = showMap;
	}
	
	public PlacingExecutorArgs(String inputFile, String outputFile) {
		this(inputFile, outputFile, "pack", false);
	}

	public String getInputFile() {
		return inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public String getLayout() {
		return layout;
	}
	
	public void setLayout(String layout) {
		this.layout = layout;
	}

	public boolean showMap() {
		return showMap;
	}
	
}
