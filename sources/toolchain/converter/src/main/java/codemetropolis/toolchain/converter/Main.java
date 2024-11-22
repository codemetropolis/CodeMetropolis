package codemetropolis.toolchain.converter;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.converter.control.ConverterType;

import java.util.HashMap;
import java.util.Map;

public class Main {

	private static final String CONVERTER_LOG_FILE = "converter_log_file";
	private static final String CONVERTER_HELP_MESSAGE_WHEN_NO_ARGS = "converter_usage_and_help";
	private static final String COMMAND_LINE_ERROR = "command_line_error";
	private static final String CONVERTER_USAGE = "converter_usage_and_help";
	private static final String CONVERTER_INTRODUCTION = "converter_introduction";
	private static final String CONVERTER_PREFIX = "converter_prefix";
	private static final String ERROR_PREFIX = "error_prefix";

	public static void main(String[] args) {
		FileLogger.load(Settings.get(CONVERTER_LOG_FILE));

		CommandLineOptions options = new CommandLineOptions();
		CmdLineParser parser = new CmdLineParser(options);

		try {
			parser.parseArgument(args);

			if (args.length == 0) {
				printHelpMessage();
				return;
			}
		} catch (CmdLineException | IllegalArgumentException e) {
			logAndPrintError(e);
			return;
		}

		if (options.showHelp()) {
			printUsage();
			return;
		}

		try {
			executeConversion(options);
		} catch (IllegalArgumentException e) {
			logAndPrintError(e);
		}
	}

	private static void printHelpMessage() {
		System.out.println(Resources.get(CONVERTER_HELP_MESSAGE_WHEN_NO_ARGS));
	}

	private static void logAndPrintError(Exception e) {
		String message = Resources.get(COMMAND_LINE_ERROR);
		FileLogger.logError(message, e);
		System.err.println(message);
		System.err.println(Resources.get(CONVERTER_USAGE));
	}

	private static void printUsage() {
		System.out.println(Resources.get(CONVERTER_INTRODUCTION));
		System.out.println(Resources.get(CONVERTER_USAGE));
	}

	private static void executeConversion(CommandLineOptions options) {
		validateOptions(options);

		ConverterType converterType;
		try {
			converterType = ConverterType.valueOf(options.getType().toUpperCase());
		} catch (IllegalArgumentException e) {
			String message = String.format("%s%s", Resources.get("error_prefix"), Resources.get("invalid_converter_type"));
			System.err.println(message);
			FileLogger.logError(message, e);
			return;
		}

		Map<String, String> params = new HashMap<>();
		if (options.getParams() != null) {
			try {
				String[] paramsArray = options.getParams();
				for (String str : paramsArray) {
					String[] strParts = str.split("=");
					params.put(strParts[0], strParts[1]);
				}
			} catch (Exception e) {
				String message = Resources.get("invalid_params");
				System.err.println(message);
				FileLogger.logError(message, e);
			}
		}

		ConverterExecutor executor = new ConverterExecutor();
		executor.setPrefix(Resources.get(CONVERTER_PREFIX));
		executor.setErrorPrefix(Resources.get(ERROR_PREFIX));
		executor.execute(
				new ConverterExecutorArgs(
						converterType,
						options.getSource(),
						options.getOutputFile(),
						params,
						options.getVerboseMode()
				));
	}

	private static void validateOptions(CommandLineOptions options) {
		if (options.getType() == null || options.getType().isEmpty()) {
			throw new IllegalArgumentException();
		}

		if (options.getSource() == null || options.getSource().isEmpty()) {
			throw new IllegalArgumentException();
		}
	}
}