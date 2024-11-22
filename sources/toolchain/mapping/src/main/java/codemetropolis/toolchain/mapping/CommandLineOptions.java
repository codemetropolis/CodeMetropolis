package codemetropolis.toolchain.mapping;

import org.kohsuke.args4j.Option;

public class CommandLineOptions {

	private static final String SHORT_HELP_OPT = "-h";
	private static final String LONG_HELP_OPT = "--help";

	private static final String SHORT_INPUT_OPT = "-i";
	private static final String LONG_INPUT_OPT = "--input";

	private static final String SHORT_OUTPUT_OPT = "-o";
	private static final String LONG_OUTPUT_OPT = "--output";

	private static final String SHORT_MAPPING_OPT = "-m";
	private static final String LONG_MAPPING_OPT = "--mapping";

	private static final String SHORT_SCALE_OPT = "-s";
	private static final String LONG_SCALE_OPT = "--scale";

	private static final String SHORT_VALIDATE_OPT = "-v";
	private static final String LONG_VALIDATE_OPT = "--validate";

	@Option(name=SHORT_HELP_OPT, aliases = {LONG_HELP_OPT})
	private boolean showHelp = false;

	@Option(name=SHORT_INPUT_OPT, aliases = {LONG_INPUT_OPT})
	private String inputFile = null;

	@Option(name=SHORT_OUTPUT_OPT, aliases = {LONG_OUTPUT_OPT})
	private String outputFile = "mappingToPlacing.xml";

	@Option(name=SHORT_MAPPING_OPT, aliases = {LONG_MAPPING_OPT})
	private String mappingFile = null;

	@Option(name=SHORT_SCALE_OPT, aliases = {LONG_SCALE_OPT})
	private double scale = 1.0;

	@Option(name=SHORT_VALIDATE_OPT, aliases = {LONG_VALIDATE_OPT})
	private boolean hierarchyValidation = false;

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

	public boolean isHierarchyValidationEnabled() {
		return hierarchyValidation;
	}
}
