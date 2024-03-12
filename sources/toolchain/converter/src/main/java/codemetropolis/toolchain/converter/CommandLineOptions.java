package codemetropolis.toolchain.converter;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

public class CommandLineOptions {

	private static final String SHORT_HELP_OPT = "-h";
    private static final String LONG_HELP_OPT = "--help";

    private static final String SHORT_TYPE_OPT = "-t";
    private static final String LONG_TYPE_OPT = "--type";
    private static final String SHORT_SOURCE_OPT = "-s";
    private static final String LONG_SOURCE_OPT = "--source";
    private static final String ALTERNATE_SOURCE_OPT1 = "-i";
    private static final String ALTERNATE_SOURCE_OPT2 = "--input";

    private static final String SHORT_OUTPUT_OPT = "-o";
    private static final String LONG_OUTPUT_OPT = "--output";

    private static final String SHORT_PARAMS_OPT = "-p";
    private static final String LONG_PARAMS_OPT = "--params";

    private static final String SHORT_VERBOSE_OPT = "-v";
    private static final String LONG_VERBOSE_OPT = "--verbose";

    @Option(name=SHORT_HELP_OPT, aliases = {LONG_HELP_OPT})
    private boolean showHelp = false;

    @Option(name=SHORT_TYPE_OPT, aliases = {LONG_TYPE_OPT})
    private String type = null;

    @Option(name=SHORT_SOURCE_OPT, aliases = {LONG_SOURCE_OPT, ALTERNATE_SOURCE_OPT1, ALTERNATE_SOURCE_OPT2})
    private String source = null;

    @Option(name=SHORT_OUTPUT_OPT, aliases = {LONG_OUTPUT_OPT})
    private String outputFile = "converterToMapping.xml";

    @Option(name=SHORT_PARAMS_OPT, handler = StringArrayOptionHandler.class, aliases = {LONG_PARAMS_OPT})
    private String[] params = null;

    @Option(name=SHORT_VERBOSE_OPT, aliases = {LONG_VERBOSE_OPT})
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
}
