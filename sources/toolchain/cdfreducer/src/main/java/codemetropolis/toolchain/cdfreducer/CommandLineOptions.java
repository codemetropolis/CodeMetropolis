package codemetropolis.toolchain.cdfreducer;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

public class CommandLineOptions {
	
	@Option(name="-h", aliases = { "--help" })
	private boolean showHelp = false;
	
	@Option(name="-s", aliases = { "--source", "-i", "--input" })
	private String source = null;
	
	@Option(name="-o", aliases = {"--output"})
	private String outputFile = "converterToMappingReduced.xml";
	
	@Option(name="-p", handler = StringArrayOptionHandler.class, aliases = {"--params"})
	private String[] params = null;
	
	public String getOutputFile(){
		return outputFile;
	}

	public String getSource() {
		return source;
	}

	public boolean showHelp() {
		return showHelp;
	}

	public String[] getParams() {
		return params;
	}
	
}
