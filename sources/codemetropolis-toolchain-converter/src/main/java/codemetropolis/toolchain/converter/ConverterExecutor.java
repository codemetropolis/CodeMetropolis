package codemetropolis.toolchain.converter;

import codemetropolis.toolchain.commons.cdf.CdfConverter;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.exceptions.CdfWriterException;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.converter.control.GraphConverter;

public class ConverterExecutor extends AbstractExecutor {

	@Override
	public boolean execute(ExecutorArgs args) {
		ConverterExecutorArgs converterArgs = (ConverterExecutorArgs) args;
		CdfConverter converter = new GraphConverter(converterArgs.getInputFile());
		
		print(Resources.get("converting_to_cdf"));
		CdfTree cdfTree = null;
		try {
			cdfTree = converter.createElements();
		} catch (Exception e) {
			printError(e, Resources.get("converter_error"));
			return false;
		}
		print(Resources.get("converting_to_cdf_done"));
		
		print(Resources.get("printing_cdf"));
		try {
			cdfTree.writeToFile(converterArgs.getOutputFile());
		} catch (CdfWriterException e) {
			printError(e, Resources.get("cdf_writer_error"));
			return false;
		}
		print(Resources.get("printing_cdf_done"));
		
		return true;
	}

}
