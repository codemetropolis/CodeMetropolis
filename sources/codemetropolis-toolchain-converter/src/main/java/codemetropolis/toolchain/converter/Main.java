package codemetropolis.toolchain.converter;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.Resources;

public class Main {

	
	public static void main(String[] args) {
		
		CommandLineOptions options = new CommandLineOptions();
	    CmdLineParser parser = new CmdLineParser(options);

	    try {
	        parser.parseArgument(args);
	        if(options.getInputFile() == null ){
	        	throw new IllegalArgumentException();
	        }
	    } catch (CmdLineException | IllegalArgumentException e) {
	    	System.err.println(Resources.get("command_line_error"));
	    	System.err.println(Resources.get("converter_usage"));
	    	return;
	    }
	    
	    if(options.showHelp()) {
	    	System.out.println(Resources.get("converter_introduction"));
	    	System.out.println(Resources.get("converter_usage"));
	    	return;
	    }
			
	    ConverterExecutor executor = new ConverterExecutor();
	    executor.setPrefix(Resources.get("converter_prefix"));
	    executor.setErrorPrefix(Resources.get("error_prefix"));
	    executor.execute(
	    		new ConverterExecutorArgs(
		    		options.getInputFile(),
		    		options.getOutputFile()
	    		));	
		
	}
}
