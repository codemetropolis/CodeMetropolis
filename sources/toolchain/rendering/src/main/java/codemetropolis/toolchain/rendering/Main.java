package codemetropolis.toolchain.rendering;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.rendering.events.ProgressEvent;
import codemetropolis.toolchain.rendering.events.ProgressEventListener;

public class Main {

	private static final String RENDERING_PREFIX = "rendering_prefix";
	private static final String ERROR_PREFIX = "error_prefix";
	private static final String RENDERING_INTRODUCTION = "rendering_introduction";
	private static final String RENDERING_USAGE = "rendering_usage";
	private static final String EMPTY_WORLD_PARAMETER = "empty_world_parameter";
	private static final String COMMAND_LINE_ERROR = "command_line_error";

	public static void main(String[] args) {
		FileLogger.load(Settings.get("rendering_log_file"));

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

		executeRendering(options);
	}

	private static void parseArguments(CmdLineParser parser, CommandLineOptions options, String[] args) throws CmdLineException {
		parser.parseArgument(args);
		if ((options.getInputFile() == null || options.getWorld() == null) && !options.showHelp()) {
			throw new IllegalArgumentException();
		}
	}

	private static void handleError(Exception e) {
		if (e.getMessage() != null && e.getMessage().contains("-w (-world, --world)")) {
			printErrorMessage(Resources.get(EMPTY_WORLD_PARAMETER));
		} else {
			String message = Resources.get(COMMAND_LINE_ERROR);
			FileLogger.logError(message, e);
			printErrorMessage(message);
			printErrorMessage(Resources.get(RENDERING_USAGE));
		}
	}

	private static void printHelp() {
		System.out.println(Resources.get(RENDERING_INTRODUCTION));
		System.out.println(Resources.get(RENDERING_USAGE));
	}

	private static void executeRendering(CommandLineOptions options) {
		RenderingExecutor executor = new RenderingExecutor();
		executor.setPrefix(Resources.get(RENDERING_PREFIX));
		executor.setErrorPrefix(Resources.get(ERROR_PREFIX));
		executor.addEventListener(new ProgressEventListener() {
			@Override
			public void onNextState(ProgressEvent event) {
				if (event.COUNT > 0) {
					switch (event.PHASE) {
						case GENERATING_BLOCKS:
							printProgress(Resources.get("creating_blocks_progress"), event);
							break;
						case PLACING_BLOCKS:
							printProgress(Resources.get("placing_blocks_progress"), event);
							break;
						default:
							break;
					}
				}
			}

			private void printProgress(String message, ProgressEvent event) {
				System.out.printf(
						Resources.get(RENDERING_PREFIX) + message + "\r",
						event.getPercent(),
						event.getTimeLeft().getHours(),
						event.getTimeLeft().getMinutes());
				if (event.getPercent() >= 100) {
					System.out.print("                                                                           \r");
				}
			}
		});

		executor.execute(
				new RenderingExecutorArgs(
						options.getInputFile(),
						options.getWorld(),
						options.overwriteSilently())
		);
	}

	private static void printErrorMessage(String message) {
		System.err.println(message);
	}
}