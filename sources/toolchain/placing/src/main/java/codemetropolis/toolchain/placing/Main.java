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
	        parser.parseArgument(args);
	        if(options.getInputFile() == null && !options.showHelp())
	        	throw new IllegalArgumentException();


	    } catch (CmdLineException | IllegalArgumentException e) {

			/**
			 * Warns the user that the layout file parameter value is missing and exits normally without creating the layout file
			 * and shows usage help message
			 */
			String exceptionMessage = e.getMessage();
			if (exceptionMessage.contains(Resources.get("layout_exception"))) {
				System.err.println(Resources.get("missing_layout_error"));
				System.err.println(Resources.get("placing_usage"));
				return;
			}
	    	String message = Resources.get("command_line_error");
	    	FileLogger.logError(message, e);
	    	System.err.println(message);
	    	System.err.println(Resources.get("placing_usage"));
	    	return;
	    }
	    
	    if(options.showHelp()) {
	    	System.out.println(Resources.get("placing_introduction"));
	    	System.out.println(Resources.get("placing_usage"));
	    	return;
	    }
		
		PlacingExecutor executor = new PlacingExecutor();
	    executor.setPrefix(Resources.get("placing_prefix"));
	    executor.setErrorPrefix(Resources.get("error_prefix"));
		executor.execute(
				new PlacingExecutorArgs(
						options.getInputFile(),
						options.getOutputFile(),
						options.getLayout(),
						options.showMap())
				);
		
	}

}
