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
	public void execute(ExecutorArgs args) {
		ConverterExecutorArgs converterArgs = (ConverterExecutorArgs) args;
		CdfConverter converter = new GraphConverter(converterArgs.getInputFile());
		
		printStream.println(Resources.get("converting_to_cdf"));
		CdfTree cdfTree = converter.createElements();
		printStream.println(Resources.get("converting_to_cdf_done"));
		
		printStream.println(Resources.get("printing_cdf"));
		try {
			cdfTree.writeToFile(converterArgs.getOutputFile());
		} catch (CdfWriterException e) {
			e.printStackTrace(errorStream);
			return;
		}
		printStream.println(Resources.get("printing_cdf_done"));
	}

}
