package codemetropolis.toolchain.rendering;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.rendering.events.ProgressEvent;
import codemetropolis.toolchain.rendering.events.ProgressEventListener;

public class Main {
	
	public static void main(String[] args) {
		int javaVersion = parseJavaVersion(System.getProperty("java.version"));

		if(javaVersion != 8) {	// Works with only Java 8 version.
			throw new RuntimeException("The required Java version is 8, you have Java " + javaVersion);	// Warns the user if the Java version is not 8. 
		}

		FileLogger.load(Settings.get("rendering_log_file"));
		
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
	    executor.addEventListener(new ProgressEventListener() {
			
	    	@Override
			public void onNextState(ProgressEvent event) {
				if(event.COUNT > 0) {
					switch(event.PHASE){
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
						Resources.get("rendering_prefix") + message + "\r",
						event.getPercent(),
						event.getTimeLeft().getHours(),
						event.getTimeLeft().getMinutes());
				if(event.getPercent() >= 100) {
					System.out.print("                                                                           \r");
				}
			}
			
		});
	    
	    executor.setPrefix(Resources.get("rendering_prefix"));
	    executor.setErrorPrefix(Resources.get("error_prefix"));
	    executor.execute(
	    		new RenderingExecutorArgs(
	    				options.getInputFile(),
	    				options.getWorld(),
	    				options.overwriteSilently())
	    		);
	    
	}

	/**
	* java.version is a system property that exists in every JVM.
	* There are two possible formats for it, because the version format changed after Java 8:
	* Java 8 or lower: 1.6.0_12, 1.7.0, 1.7.0_22, 1.8.0_201
	* Java 9 or higher: 9.0.1, 10.1.4, 12, 12.0.1
	* @param version string of version numbers formatted in 'x.y.z' or '1.x.y_z'
	* @return parsed version number in integer
	*/
	private static int parseJavaVersion(String version) {
		if(version.startsWith("1.")) { // Under Java 8 and Java 8 version. Parse 1.x.y_z format string.
			version = version.substring(2, 3);

		} else {	// After Java 8 version. Parse x.y.z format string.
			int dot = version.indexOf(".");
			if(dot != -1) {
				version = version.substring(0, dot);
			}
		} return Integer.parseInt(version);
	}
}
