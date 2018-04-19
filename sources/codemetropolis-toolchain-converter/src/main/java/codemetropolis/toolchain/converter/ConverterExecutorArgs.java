package codemetropolis.toolchain.converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.converter.control.ConverterType;

public class ConverterExecutorArgs extends ExecutorArgs {
	
	private ConverterType type;
	private String source;
	private String outputFile;
	private Map<String, String> params;
	private String relationFile;

	public ConverterExecutorArgs(ConverterType type, String source, String outputFile, String relationFile) {
		this(type, source, outputFile, null, relationFile);
	}
	
	public ConverterExecutorArgs(ConverterType type, String source, String outputFile, Map<String, String> params, String relationFile) {
		super();
		this.type = type;
		this.source = source;
		this.outputFile = outputFile;
		this.params = params;
		this.relationFile = relationFile;
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
	
	public Map<String, String> getParams() {
		return Collections.unmodifiableMap(params);
	}
	
	public String getParameter(String key) {
		return params.get(key);
	}

	public String getRelationFile() {
		return relationFile;
	}

	public void setRelationFile(String relationFile) {
		this.relationFile = relationFile;
	}

}
