package codemetropolis.toolchain.mapping;

import org.kohsuke.args4j.Option;

public class CommandLineOptions {
	
	@Option(name="-h", aliases = { "--help" })
	private boolean showHelp = false;
	
	@Option(name="-i", aliases = { "--input" })
	private String inputFile = null;

	@Option(name="-o", aliases = { "--output" })
	private String outputFile = "mappingToPlacing.xml";
	 
	@Option(name="-m", aliases = { "--mapping" })
	private String mappingFile = null;
	
	@Option(name="-s", aliases = { "--scale" })
	private double scale = 1.0;
	
	@Option(name="-n", aliases = { "--nested" })
	private boolean nested = false;

	public boolean showHelp() {
		return showHelp;
	}

	public String getInputFile() {
		return inputFile;
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

	public boolean showNested() {
		return nested;
	}
	
}
