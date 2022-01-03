package codemetropolis.toolchain.placing;

import org.kohsuke.args4j.Option;

public class CommandLineOptions {
	
	@Option(name="-h", aliases = { "--help" }, usage="shows basic info")
	private boolean showHelp = false;
	
	@Option(name="-i", aliases = { "--input" }, usage="input from this file")
	private String inputFile = null;

	@Option(name="-o", aliases = { "--output" }, usage="output to this file")
	private String outputFile = "placingToRendering.xml";
	
	@Option(name="-l", aliases = { "--layout" }, usage="layout algorithm")
	private String layout = "pack";
	 
	@Option(name="-m", aliases = { "--map" }, usage="whether or not the map should be shown")
	private boolean showMap = false;

	public boolean showHelp() {
		return showHelp;
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

	public boolean showMap() {
		return showMap;
	}
	 
}
