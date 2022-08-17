package codemetropolis.toolchain.ixmlfiltering;

import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;

/**
 * The Main for the IXMLFiltering
 * This class runs the IXMLFiltering toolchain
 * @author Adam Polnik LIYMCC h984893
 */

public class Main {
	public static void main(String[] args) {
		
	CommandLineOptions options = new CommandLineOptions();
    CmdLineParser parser = new CmdLineParser(options);

    try {
        parser.parseArgument(args);
        if (options.showHelp()) {
            System.out.println(Resources.get("IXML_introduction"));
            System.out.println(Resources.get("IXMLreducer_usage") + '\n' + Resources.get("IXMLreducer_usage_aliases"));
            return;
        }
        if (options.getInputFile() == null || options.getOutputFile() == null) {
            throw new IllegalArgumentException();
        }
    } catch (CmdLineException | IllegalArgumentException e) {
        String message = Resources.get("command_line_error");
        FileLogger.logError(message, e);
        System.err.println(message);
        return;
    }
    if(options.showHelp()) {
    	System.out.println(Resources.get("IXMLReducer_introduction"));
    	System.out.println(Resources.get("IXMLReducing_usage"));
    	return;
    }
   
    IXMLFilteringExecutor executor = new IXMLFilteringExecutor();
    executor.setPrefix(Resources.get("mapping_prefix"));
    executor.setErrorPrefix(Resources.get("error_prefix"));
    executor.execute(
    		new IXMLFilteringExecutorArgs(
	    		options.getInputFile(),
	    		options.getOutputFile(),
	    		options.getPropertyNameRegex(),
	    		options.getPropertyValueRegex()

    		));	
    
    }

}
