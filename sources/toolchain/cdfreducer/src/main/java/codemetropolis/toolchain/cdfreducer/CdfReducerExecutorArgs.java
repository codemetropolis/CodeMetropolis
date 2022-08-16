package codemetropolis.toolchain.cdfreducer;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;

import java.util.Map;

public class CdfReducerExecutorArgs extends ExecutorArgs {

	private String inputFile;
	private String outputFile;
	private Map<String, String> params;
	
	public CdfReducerExecutorArgs(String inputFile, String outputFile,  Map<String, String> params) {
		super();
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.params = params;
	}

	public String getInputFile() {
		return inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public Map<String, String> getParams() {
		return params;
	}

}
