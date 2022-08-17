package codemetropolis.toolchain.mapping;

import java.io.FileNotFoundException;

import codemetropolis.toolchain.commons.cdf.exceptions.CdfReaderException;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlWriterException;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.mapping.control.MappingController;
import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;
import codemetropolis.toolchain.mapping.exceptions.MissingResourceException;
import codemetropolis.toolchain.mapping.exceptions.NotSupportedLinkingException;
import codemetropolis.toolchain.mapping.exceptions.NotValidBuildableStructure;
import codemetropolis.toolchain.mapping.model.Mapping;

public class MappingExecutor extends AbstractExecutor {

	public static final double MIN_SCALE = 0.01;
	public static final double MAX_SCALE = 100;

	@Override
	public boolean execute(ExecutorArgs args) {
		MappingExecutorArgs mappingArgs = (MappingExecutorArgs)args;
		
		if(mappingArgs.getScale() < MIN_SCALE || mappingArgs.getScale() > MAX_SCALE) {
			printError(null, Resources.get("invalid_scale_error"), MIN_SCALE, MAX_SCALE);
			return false;
		}

		 /**
		 * try-catch blocks to handle invalid XML inputs.
		 * @param mapping XML file content
		 * @param MappingReaderException Invalid XML content
		 */
		print(Resources.get("reading_mapping"));
		Mapping mapping;
		try {
			mapping = Mapping.readFromXML(mappingArgs.getMappingFile());
		} catch (FileNotFoundException e) {
			printError(e, Resources.get("mapping_not_found_error"));
			return false;
		} catch (MappingReaderException e) { //we print a warning, instead of a long error message.
			System.out.println("The XML file's content is invalid.");
			return false;
		}
		print(Resources.get("reading_mapping_done"));
		
		print(Resources.get("validating_mapping"));
		try {
			mapping.validate();
		} catch (NotSupportedLinkingException e) {
			printError(e, e.getMessage());
			return false;
		} catch (MissingResourceException e) {
			printError(e, e.getMessage());
			return false;
		}
		print(Resources.get("validating_mapping_done"));
		
		print(Resources.get("reading_graph"));
		MappingController mappingController = new MappingController(mapping, mappingArgs.getScale(), !mappingArgs.isHierarchyValidationEnabled());
		try {
			mappingController.createBuildablesFromCdf(mappingArgs.getCdfFile());
		} catch (CdfReaderException e) {
			if(e.getCause() instanceof FileNotFoundException) {
				printError(e, Resources.get("cdf_not_found_error"));
			} else {
				printError(e, Resources.get("cdf_error"));
			}
			return false;
		}
		print(Resources.get("reading_graph_done"));
		
		print(Resources.get("linking_metrics"));
		BuildableTree buildables = mappingController.linkBuildablesToMetrics();
		print(Resources.get("linking_metrics_done"));
		try {
			mappingController.validateBuildableStructure(buildables);
		} catch (NotValidBuildableStructure e) {
			printError(e, Resources.get("invalid_hierarchy_error"));
			return false;
		}
		print(Resources.get("mapping_printing_output"));
		try {
			buildables.writeToFile(mappingArgs.getOutputFile(), "mapping", "placing", "1.0");
		} catch (CmxmlWriterException e) {
			printError(e, Resources.get("cmxml_writer_error"));
			return false;
		}
		print(Resources.get("mapping_printing_output_done"));

		return true;
	}
}
