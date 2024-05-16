package codemetropolis.toolchain.placing;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;

public class Main {

	private static final String PLACING_PREFIX = "placing_prefix";
	private static final String ERROR_PREFIX = "error_prefix";
	private static final String PLACING_INTRODUCTION = "placing_introduction";
	private static final String PLACING_USAGE = "placing_usage";
	private static final String LAYOUT_EXCEPTION = "layout_exception";
	private static final String MISSING_LAYOUT_ERROR = "missing_layout_error";
	private static final String COMMAND_LINE_ERROR = "command_line_error";

	public static void main(String[] args) {
		FileLogger.load(Settings.get("placing_log_file"));

		CommandLineOptions options = new CommandLineOptions();
		CmdLineParser parser = new CmdLineParser(options);

		try {
			parseArguments(parser, options, args);
		} catch (CmdLineException | IllegalArgumentException e) {
			handleError(e);
			return;
		}

		if (options.showHelp()) {
			printHelp();
			return;
		}

		executePlacing(options);
	}

	private static void parseArguments(CmdLineParser parser, CommandLineOptions options, String[] args) throws CmdLineException {
		parser.parseArgument(args);
		if (options.getInputFile() == null && !options.showHelp()) {
			throw new IllegalArgumentException();
		}
	}

	private static void handleError(Exception e) {
		if (e.getMessage().contains(Resources.get(LAYOUT_EXCEPTION))) {
			printErrorMessage(Resources.get(MISSING_LAYOUT_ERROR));
			printErrorMessage(Resources.get(PLACING_USAGE));
		} else {
			String message = Resources.get(COMMAND_LINE_ERROR);
			FileLogger.logError(message, e);
			printErrorMessage(message);
			printErrorMessage(Resources.get(PLACING_USAGE));
		}
	}

	private static void printHelp() {
		System.out.println(Resources.get(PLACING_INTRODUCTION));
		System.out.println(Resources.get(PLACING_USAGE));
	}

	private static void executePlacing(CommandLineOptions options) {
		PlacingExecutor executor = new PlacingExecutor();
		executor.setPrefix(Resources.get(PLACING_PREFIX));
		executor.setErrorPrefix(Resources.get(ERROR_PREFIX));
		executor.execute(
				new PlacingExecutorArgs(
						options.getInputFile(),
						options.getOutputFile(),
						options.getLayout(),
						options.showMap())
		);
	}

	private static void printErrorMessage(String message) {
		System.err.println(message);
	}
}