package codemetropolis.toolchain.converter;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

public class CommandLineOptions {
	
	@Option(name="-h", aliases = { "--help" })
	private boolean showHelp = false;
	
	@Option(name="-t", aliases = {"--type"})
	private String type = null;
	
	@Option(name="-s", aliases = { "--source", "-i", "--input" })
	private String source = null;
	
	@Option(name="-o", aliases = {"--output"})
	private String outputFile = "converterToMapping.xml";
	
	@Option(name="-p", handler = StringArrayOptionHandler.class, aliases = {"--params"})
	private String[] params = null;
	
	@Option(name="-v", aliases = {"--verbose"})
	private boolean verboseMode = false;
	
	public String getOutputFile(){
		return outputFile;
	}

	public String getType() {
		return type;
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

	public boolean getVerboseMode() {
		return verboseMode;
	}

	public boolean checkIfHelpModeIsEnabled(String[] commandLineArgs) {
		if (commandLineArgs != null) {
			for (String param : commandLineArgs) {
				if (param.equals("-h") || param.equals("--help")) {
					return true;
				}
			}
		}
		return false;
	}
}
