package codemetropolis.toolchain.rendering;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import codemetropolis.toolchain.commons.cmxml.Validator;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.FileUtils;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.rendering.control.WorldBuilder;
import codemetropolis.toolchain.rendering.events.ProgressEvent;
import codemetropolis.toolchain.rendering.events.ProgressEventListener;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;

public class RenderingExecutor extends AbstractExecutor {
	
	@Override
	public void execute(ExecutorArgs executorArgs) {
		RenderingExecutorArgs args = (RenderingExecutorArgs)executorArgs;
		
		File worldDir = new File(args.getWorldPath());
		File tempDir = new File(worldDir, "TEMP");
		tempDir.deleteOnExit();
		boolean overwrite = args.isSilentOverwriteEnabled();
		
		if(worldDir.exists() && worldDir.isDirectory()) {
			if(!overwrite) {
				printStream.printf(Resources.get("world_already_exists"), tempDir.getAbsolutePath());
				Scanner in = new Scanner(System.in);
				String input = "";
				while(!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
					input = in.next();
				}
				in.close();
				if(input.equalsIgnoreCase("Y")) {
					overwrite = true;
				} else {
					printStream.println(Resources.get("render_interrupted"));
					return;
				}
			}
			if(overwrite) {
				FileUtils.deleteDirectory(worldDir);
			}
		}
			
		try {
			boolean isValid = Validator.validate(args.getInputFile());
			if(!isValid) {
				errorStream.println(Resources.get("invalid_input_xml_error"));
				return;
			}
		} catch (IOException e) {
			errorStream.println(Resources.get("missing_input_xml_error"));
			return;
		}
		
		WorldBuilder worldBuilder = new WorldBuilder(args.getWorldPath());
		worldBuilder.addEventListener(new ProgressEventListener() {
			@Override
			public void onNextState(ProgressEvent event) {
				if(event.COUNT > 0) {
					switch(event.PHASE){
						case GENERATING_BLOCKS:
							printStream.printf(
									Resources.get("creating_blocks_progress") + "\r",
									event.getPercent(),
									event.getTimeLeft().getHours(),
									event.getTimeLeft().getMinutes());
							break;
						case PLACING_BLOCKS:
							printStream.printf(
									Resources.get("placing_blocks_progress") + "\r",
									event.getPercent(),
									event.getTimeLeft().getHours(),
									event.getTimeLeft().getMinutes());
							break;
						default:
							break;
					}
				}
			}
		});
		
		printStream.println(Resources.get("rendering_reading_input"));
		try {
			worldBuilder.createBuildings(args.getInputFile());
		} catch (BuildingTypeMismatchException e) {
			e.printStackTrace(errorStream);
		}
		printStream.println(Resources.get("rendering_reading_input_done"));
		printStream.printf(Resources.get("buildables_found") + "\n", worldBuilder.getNumberOfBuildings());
		
		printStream.println(Resources.get("creating_blocks"));
		try {
			worldBuilder.createBlocks(tempDir, args.getMaxTime());
		} catch (RenderingException e) {
			e.printStackTrace(errorStream);
			return;
		}
		long elapsed = worldBuilder.getTimeElapsedDuringLastPhase();
		int hours = (int) (elapsed / (1000 * 60 * 60));
        int minutes = (int) (elapsed % (1000 * 60 * 60) / (1000 * 60));
		printStream.printf(Resources.get("creating_blocks_done") + "\n", worldBuilder.getNumberOfBlocks(), hours, minutes);
		
		printStream.println(Resources.get("placing_blocks"));
		try {
			worldBuilder.build(tempDir);
		} catch (RenderingException e) {
			e.printStackTrace(errorStream);
		}
		elapsed = worldBuilder.getTimeElapsedDuringLastPhase();
		hours = (int) (elapsed / (1000 * 60 * 60));
        minutes = (int) (elapsed % (1000 * 60 * 60) / (1000 * 60));
		printStream.printf(Resources.get("placing_blocks_done") + "                                           \n", hours, minutes);
	}
	
}
