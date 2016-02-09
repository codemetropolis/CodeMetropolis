package codemetropolis.toolchain.mapping;

import java.io.IOException;

import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.Validator;
import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlWriterException;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.mapping.control.MappingController;
import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;
import codemetropolis.toolchain.mapping.exceptions.NotSupportedLinkingException;
import codemetropolis.toolchain.mapping.model.Mapping;

public class MappingExecutor extends AbstractExecutor {

	@Override
	public void execute(ExecutorArgs args) {
		MappingExecutorArgs mappingArgs = (MappingExecutorArgs)args;
		
		try {
			boolean isValid = Validator.validateCdf(mappingArgs.getGraphFile());
			if(!isValid) {
				errorStream.println(Resources.get("invalid_cdf_xml_error"));
				return;
			}
		} catch (IOException e) {
			errorStream.println(Resources.get("missing_input_xml_error"));
			return;
		}
		
		printStream.println(Resources.get("reading_mapping"));
		Mapping mapping;
		try {
			mapping = Mapping.readFromXML(mappingArgs.getMappingFile());
		} catch (MappingReaderException e) {
			e.printStackTrace(errorStream);
			return;
		} catch (NotSupportedLinkingException e) {
			errorStream.println(e.getMessage());
			return;
		}
		printStream.println(Resources.get("reading_mapping_done"));
		
		printStream.println(Resources.get("reading_graph"));
		MappingController mappingController = new MappingController(mappingArgs.getScale(), mappingArgs.isShowNested());
		mappingController.createBuildablesFromCdf(mappingArgs.getGraphFile());
		printStream.println(Resources.get("reading_graph_done"));
		
		printStream.println(Resources.get("linking_metrics"));
		BuildableTree buildables = mappingController.linkBuildablesToMetrics(mapping);
		printStream.println(Resources.get("linking_metrics_done"));
		
		printStream.println(Resources.get("mapping_printing_output"));
		try {
			buildables.writeToFile(mappingArgs.getOutputFile(), "mapping", "placing", "1.0");
		} catch (CmxmlWriterException e) {
			e.printStackTrace(errorStream);
			return;
		}
		printStream.println(Resources.get("mapping_printing_output_done"));
	}
	
}
