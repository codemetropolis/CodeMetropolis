package codemetropolis.toolchain.mapping;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;

public class Main {

    private static final String MAPPING_LOG_FILE = "mapping_log_file";
    private static final String MAPPING_HELP_MESSAGE_WHEN_NO_ARGS = "mapping_help_message_when_no_args";
    private static final String COMMAND_LINE_ERROR = "command_line_error";
    private static final String MAPPING_USAGE = "mapping_usage";
    private static final String MAPPING_INTRODUCTION = "mapping_introduction";
    private static final String MAPPING_PREFIX = "mapping_prefix";
    private static final String ERROR_PREFIX = "error_prefix";

    public static void main(String[] args) {
        FileLogger.load(Settings.get(MAPPING_LOG_FILE));

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

        executeMapping(options);
    }

    private static void printHelpMessage() {
        System.out.println(Resources.get(MAPPING_HELP_MESSAGE_WHEN_NO_ARGS));
    }

    private static void logAndPrintError(Exception e) {
        String message = Resources.get(COMMAND_LINE_ERROR);
        FileLogger.logError(message, e);
        System.err.println(message);
        System.err.println(Resources.get(MAPPING_USAGE));
    }

    private static void printUsage() {
        System.out.println(Resources.get(MAPPING_INTRODUCTION));
        System.out.println(Resources.get(MAPPING_USAGE));
    }

    private static void executeMapping(CommandLineOptions options) {
        MappingExecutor executor = new MappingExecutor();
        executor.setPrefix(Resources.get(MAPPING_PREFIX));
        executor.setErrorPrefix(Resources.get(ERROR_PREFIX));
        executor.execute(
                new MappingExecutorArgs(
                        options.getInputFile(),
                        options.getOutputFile(),
                        options.getMappingFile(),
                        options.getScale(),
                        options.isHierarchyValidationEnabled())
        );
    }
}