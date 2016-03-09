package codemetropolis.toolchain.mapping;

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
	        if((options.getInputFile() == null || options.getMappingFile() == null) && !options.showHelp())
	        	throw new IllegalArgumentException();
	    } catch (CmdLineException | IllegalArgumentException e) {
	    	String message = Resources.get("command_line_error");
	    	FileLogger.logError(message, e);
	    	System.err.println(message);
	    	System.err.println(Resources.get("mapping_usage"));
	    	return;
	    }
	    
	    if(options.showHelp()) {
	    	System.out.println(Resources.get("mapping_introduction"));
	    	System.out.println(Resources.get("mapping_usage"));
	    	return;
	    }
			
	    MappingExecutor executor = new MappingExecutor();
	    executor.setPrefix(Resources.get("mapping_prefix"));
	    executor.setErrorPrefix(Resources.get("error_prefix"));
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
