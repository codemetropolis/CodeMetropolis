package codemetropolis.toolchain.rendering;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.rendering.events.ProgressEvent;
import codemetropolis.toolchain.rendering.events.ProgressEventListener;

public class Main {

	/**
	 * The Executor for the IXMLFiltering
	 * This class make the process of rendering and showing specific errors if the render command is wrong
	 * If world parameter is empty, show a specific error message and do not log error, only exits normaly
	 * @author Bicskei Dániel B0IAS9 h642581
	 * @param args  from command line, it's a String array
	 */

	public static void main(String[] args) {
		
		FileLogger.load(Settings.get("rendering_log_file"));
		
		CommandLineOptions options = new CommandLineOptions();
	    CmdLineParser parser = new CmdLineParser(options);

	    try {
	        parser.parseArgument(args);
	        if((options.getInputFile()== null || options.getWorld() == null) && !options.showHelp())
	        	throw new IllegalArgumentException();
	    } catch (CmdLineException | IllegalArgumentException e) {
			String message = Resources.get("command_line_error") + System.lineSeparator() + Resources.get("rendering_usage");
			//If isset e.getMessage() error message contain "-w (-world, --world)" string only if world param is empty
			if (e.getMessage() != null && e.getMessage().contains("-w (-world, --world)")) {
				message = Resources.get("empty_world_parameter");
			} else {
				FileLogger.logError(message, e);
			}
			
			System.err.println(message);
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

}
