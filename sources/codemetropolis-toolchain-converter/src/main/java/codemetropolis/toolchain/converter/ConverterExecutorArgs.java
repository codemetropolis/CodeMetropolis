package codemetropolis.toolchain.converter;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.converter.control.ConverterType;

public class ConverterExecutorArgs extends ExecutorArgs {
	
	private ConverterType type;
	private String source;
	private String outputFile;
	
	public ConverterExecutorArgs(ConverterType type, String source, String outputFile) {
		super();
		this.type = type;
		this.source = source;
		this.outputFile = outputFile;
	}

	public ConverterType getType() {
		return type;
	}
	
	public String getSource() {
		return source;
	}

	public String getOutputFile(){
		return outputFile;
	}

}
