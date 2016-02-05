package codemetropolis.toolchain.converter;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;

public class ConverterExecutorArgs extends ExecutorArgs {
	
	private String inputFile;
	private String outputFile;
	
	public String getOutputFile(){
		return outputFile;
	}
	
	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public ConverterExecutorArgs(String inputFile, String outputFile) {
		super();
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

}
