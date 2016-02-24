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
import codemetropolis.toolchain.mapping.exceptions.NotSupportedLinkingException;
import codemetropolis.toolchain.mapping.model.Mapping;

public class MappingExecutor extends AbstractExecutor {

	@Override
	public void execute(ExecutorArgs args) {
		MappingExecutorArgs mappingArgs = (MappingExecutorArgs)args;
		
		print(Resources.get("reading_mapping"));
		Mapping mapping;
		try {
			mapping = Mapping.readFromXML(mappingArgs.getMappingFile());
		} catch (MappingReaderException e) {
			if(e.getCause() instanceof FileNotFoundException) {
				printError(Resources.get("mapping_not_found_error"));
			} else {
				printError(e.getMessage());
			}
			return;
		} catch (NotSupportedLinkingException e) {
			printError(e.getMessage());
			return;
		}
		print(Resources.get("reading_mapping_done"));
		
		print(Resources.get("reading_graph"));
		MappingController mappingController = new MappingController(mappingArgs.getScale(), mappingArgs.isShowNested());
		try {
			mappingController.createBuildablesFromCdf(mappingArgs.getCdfFile());
		} catch (CdfReaderException e) {
			if(e.getCause() instanceof FileNotFoundException) {
				printError(Resources.get("cdf_not_found_error"));
			} else {
				printError(Resources.get("cdf_error"));
			}
			return;
		}
		print(Resources.get("reading_graph_done"));
		
		print(Resources.get("linking_metrics"));
		BuildableTree buildables = mappingController.linkBuildablesToMetrics(mapping);
		print(Resources.get("linking_metrics_done"));
		
		print(Resources.get("mapping_printing_output"));
		try {
			buildables.writeToFile(mappingArgs.getOutputFile(), "mapping", "placing", "1.0");
		} catch (CmxmlWriterException e) {
			printError(Resources.get("cmxml_writer_error"));
			return;
		}
		print(Resources.get("mapping_printing_output_done"));
	}
	
}
