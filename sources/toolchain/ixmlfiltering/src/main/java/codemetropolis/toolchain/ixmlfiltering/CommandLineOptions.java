package codemetropolis.toolchain.ixmlfiltering;

import org.kohsuke.args4j.Option;

/**
 * The CommandLineOptions for the IXMLFiltering
 * This class manage the command line arguments of the IXMLFilteringExecutor
 * @author Adam Polnik LIYMCC h984893
 */

public class CommandLineOptions {
	
	@Option(name="-h", aliases = { "--help" })
	private boolean showHelp = false;
	
	@Option(name="-i", aliases = { "--input" })
	private String inputFile = null;

	@Option(name="-o", aliases = { "--output" })
	private String outputFile = ".\reducedXmlfile.xml";
	 
	@Option(name="-pn", aliases = { "--pnr" })
	private String propertyNameRegex = null;
	
	@Option(name="-pv", aliases = { "--pvr" })
	private String propertyValueRegex = null;
	
	public boolean showHelp() {
		return showHelp;
	}

	public String getInputFile() {
		return inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public String getPropertyNameRegex() {
		return propertyNameRegex;
	}
	public String getPropertyValueRegex() {
		return propertyValueRegex;
	}
	
}
