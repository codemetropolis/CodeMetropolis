package codemetropolis.toolchain.converter;

import org.kohsuke.args4j.Option;

public class CommandLineOptions {
	
	@Option(name="-h", aliases = { "--help" })
	private boolean showHelp = false;
	
	@Option(name="-i", aliases = { "--input" })
	private String inputFile = null;
	
	@Option(name="-o", aliases = {"--output"})
	private String outputFile = null;
	
	public String getOutputFile(){
		return outputFile;
	}

	public String getInputFile() {
		return inputFile;
	}

	public boolean showHelp() {
		return showHelp;
	}
}
