package codemetropolis.toolchain.rendering;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;

public class Main {
	
	public static void main(String[] args) {
		
		CommandLineOptions options = new CommandLineOptions();
	    CmdLineParser parser = new CmdLineParser(options);

	    try {
	        parser.parseArgument(args);
	        if((options.getInputFile()== null || options.getWorld() == null) && !options.showHelp())
	        	throw new IllegalArgumentException();
	    } catch (CmdLineException | IllegalArgumentException e) {
	    	String message = Resources.get("command_line_error");
	    	FileLogger.logError(message, e);
	    	System.err.println(message);
	    	System.err.println(Resources.get("rendering_usage"));
	    	return;
	    }
	    
	    if(options.showHelp()) {
	    	System.out.println(Resources.get("rendering_introduction"));
	    	System.out.println(Resources.get("rendering_usage"));
	    	return;
	    }
	    
	    RenderingExecutor executor = new RenderingExecutor();
	    executor.setPrefix(Resources.get("rendering_prefix"));
	    executor.setErrorPrefix(Resources.get("error_prefix"));
	    executor.execute(
	    		new RenderingExecutorArgs(
	    				options.getInputFile(),
	    				options.getWorld(),
	    				options.overwriteSilently())
	    		);
	    
	}

}
