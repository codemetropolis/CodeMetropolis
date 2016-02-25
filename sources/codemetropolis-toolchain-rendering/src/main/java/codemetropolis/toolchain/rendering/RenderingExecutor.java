package codemetropolis.toolchain.rendering;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import codemetropolis.toolchain.commons.cmxml.Validator;
import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlValidationFailedException;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.FileUtils;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.rendering.control.WorldBuilder;
import codemetropolis.toolchain.rendering.events.ProgressEvent;
import codemetropolis.toolchain.rendering.events.ProgressEventListener;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;
import codemetropolis.toolchain.rendering.exceptions.TooLongRenderDurationException;

public class RenderingExecutor extends AbstractExecutor {
	
	@Override
	public void execute(ExecutorArgs args) {
		RenderingExecutorArgs renderingArgs = (RenderingExecutorArgs)args;
		
		File worldDir = new File(renderingArgs.getWorldPath());
		File tempDir = new File(worldDir, "TEMP");
		tempDir.deleteOnExit();
		boolean overwrite = renderingArgs.isSilentOverwriteEnabled();
		
		if(worldDir.exists() && worldDir.isDirectory()) {
			if(!overwrite) {
				print(false, Resources.get("world_already_exists"), tempDir.getAbsolutePath());
				Scanner in = new Scanner(System.in);
				String input = "";
				while(!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
					input = in.next();
				}
				in.close();
				if(input.equalsIgnoreCase("Y")) {
					overwrite = true;
				} else {
					print(Resources.get("render_interrupted"));
					return;
				}
			}
			if(overwrite) {
				FileUtils.deleteDirectory(worldDir);
			}
		}
			
		try {
			boolean isValid = Validator.validate(renderingArgs.getInputFile());
			if(!isValid) {
				throw new CmxmlValidationFailedException();
			}
		} catch (IOException e) {
			printError(e, Resources.get("missing_input_xml_error"));
			return;
		} catch (CmxmlValidationFailedException e) {
			printError(e, Resources.get("invalid_input_xml_error"));
			return;
		}
		
		WorldBuilder worldBuilder = new WorldBuilder(renderingArgs.getWorldPath());
		worldBuilder.addEventListener(new ProgressEventListener() {
			@Override
			public void onNextState(ProgressEvent event) {
				if(event.COUNT > 0) {
					switch(event.PHASE){
						case GENERATING_BLOCKS:
							print(
									false,
									Resources.get("creating_blocks_progress"),
									event.getPercent(),
									event.getTimeLeft().getHours(),
									event.getTimeLeft().getMinutes());
							break;
						case PLACING_BLOCKS:
							print(
									false,
									Resources.get("placing_blocks_progress"),
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
		
		print(Resources.get("rendering_reading_input"));
		try {
			worldBuilder.createBuildings(renderingArgs.getInputFile());
		} catch (BuildingTypeMismatchException e) {
			printError(e, Resources.get("building_creation_failed_error"));
			return;
		}
		print(Resources.get("rendering_reading_input_done"));
		print(Resources.get("buildables_found"), worldBuilder.getNumberOfBuildings());
		
		print(Resources.get("creating_blocks"));
		try {
			worldBuilder.createBlocks(tempDir, renderingArgs.getMaxTime());
		} catch (TooLongRenderDurationException e) {
			printError(e, Resources.get("too_long_render_duration_error"), e.getMaxTime());
			return;
		}
		long elapsed = worldBuilder.getTimeElapsedDuringLastPhase();
		int hours = (int) (elapsed / (1000 * 60 * 60));
        int minutes = (int) (elapsed % (1000 * 60 * 60) / (1000 * 60));
        print(Resources.get("creating_blocks_done"), worldBuilder.getNumberOfBlocks(), hours, minutes);
		
        print(Resources.get("placing_blocks"));
		try {
			worldBuilder.build(tempDir);
		} catch (RenderingException e) {
			printError(e, Resources.get("placing_blocks_failed_error"));
			return;
		}
		elapsed = worldBuilder.getTimeElapsedDuringLastPhase();
		hours = (int) (elapsed / (1000 * 60 * 60));
        minutes = (int) (elapsed % (1000 * 60 * 60) / (1000 * 60));
        print(Resources.get("placing_blocks_done"), hours, minutes);
	}
	
}
