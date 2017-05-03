package codemetropolis.toolchain.placing;

import java.awt.EventQueue;
import java.io.IOException;

import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.CmxmlValidator;
import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlReaderException;
import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlValidationFailedException;
import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlWriterException;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.exceptions.NonExistentLayoutException;
import codemetropolis.toolchain.placing.layout.Layout;

public class PlacingExecutor extends AbstractExecutor {

	@Override
	public boolean execute(ExecutorArgs args) {
		PlacingExecutorArgs placingArgs = (PlacingExecutorArgs)args;
			
		try {
			boolean isValid = CmxmlValidator.validate(placingArgs.getInputFile());
			if(!isValid) {
				throw new CmxmlValidationFailedException();
			}
		} catch (IOException e) {
			printError(e, Resources.get("missing_input_xml_error"));
			return false;
		} catch (CmxmlValidationFailedException e) {
			printError(e, Resources.get("invalid_input_xml_error"));
			return false;
		}
		
		print(Resources.get("placing_reading_input"));
		BuildableTree buildables = new BuildableTree();
		try {
			buildables.loadFromFile(placingArgs.getInputFile());
		} catch (CmxmlReaderException e) {
			printError(e, Resources.get("cmxml_reader_error"));
			return false;
		}
		print(Resources.get("placing_reading_input_done"));
		
		print(Resources.get("calculating_size_and_pos"));
		try {
			String layoutString = placingArgs.getLayout();
			if(layoutString.equals("BASIC") && layoutString.equals("MINIMALIST")) {
				placingArgs.setLayout("PACK");
			}
			Layout layout = Layout.parse(layoutString);
			layout.apply(buildables);
		} catch (NonExistentLayoutException e) {
			printError(e, Resources.get("missing_layout_error"));
			return false;
		} catch (LayoutException e) {
			printError(e, Resources.get("layout_error"));
			return false;
		}
		print(Resources.get("calculating_size_and_pos_done"));

		print(Resources.get("placing_printing_output"));
		try {
			buildables.writeToFile(placingArgs.getOutputFile(), "placing", "rendering", "1.0");
		} catch (CmxmlWriterException e) {
			printError(e, Resources.get("cmxml_writer_error"));
			return false;
		}
		print(Resources.get("placing_printing_output_done"));
		
		if(placingArgs.showMap()) {
			final BuildableTree b = buildables;
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					CityMapGUI map = new CityMapGUI(b);
					map.setVisible(true);
				}
			});
		}
		
		return true;
	}
	
}
