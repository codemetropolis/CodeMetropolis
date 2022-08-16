package codemetropolis.toolchain.converter;

import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.converter.control.ConverterType;

public class Main {

	/**
	 * If there is no parameter given, the system will print out the 'without_param' string from the resources,
	 * which tells the user that no parameter was given,
	 * and also print the 'converter_usage' string.
	 */
	public static boolean checkWithoutParam(CommandLineOptions options) {
		if(options.getType() == null || options.getSource() == null ) {

			System.err.println(Resources.get("without_param"));
			System.err.println(Resources.get("converter_usage"));

			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		
		FileLogger.load(Settings.get("converter_log_file"));
		
		CommandLineOptions options = new CommandLineOptions();
	    CmdLineParser parser = new CmdLineParser(options);

	    try {
	        parser.parseArgument(args);

			if(checkWithoutParam(options)) {
				return;
			}

	        if(options.getType() == null || options.getSource() == null ){
				throw new IllegalArgumentException();
			}
	    } catch (CmdLineException | IllegalArgumentException e) {
	    	String message = Resources.get("command_line_error");
	    	FileLogger.logError(message, e);
	    	System.err.println(message);
	    	System.err.println(Resources.get("converter_usage"));
	    	return;
	    }
	    
	    ConverterType converterType = null;
	    try {
	    	converterType = ConverterType.valueOf(options.getType().toUpperCase());
	    } catch(IllegalArgumentException e) {
	    	String message = String.format("%s%s", Resources.get("error_prefix"), Resources.get("invalid_converter_type"));
	    	System.err.println(message);
	    	FileLogger.logError(message, e);
	    	return;
	    }
	    
	    Map<String, String> params = new HashMap<>();
	    if(options.getParams() != null) {
	    	try {
	 		    String[] paramsArray = options.getParams();
	 		    for(String str : paramsArray) {
	 		    	String[] strParts = str.split("=");
	 		    	params.put(strParts[0], strParts[1]);
	 		    }
	 	    } catch(Exception e) {
	 	    	String message = Resources.get("invalid_params");
	 	    	System.err.println(message);
	 	    	FileLogger.logError(message, e);
	 	    }
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
	    			converterType,
		    		options.getSource(),
		    		options.getOutputFile(),
		    		params
	    		));	
		
	}
	
}
