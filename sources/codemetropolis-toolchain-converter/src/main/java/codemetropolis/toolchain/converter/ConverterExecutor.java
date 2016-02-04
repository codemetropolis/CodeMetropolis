package codemetropolis.toolchain.converter;

import codemetropolis.toolchain.commons.cdf.CdfConverter;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.exceptions.CdfWriterException;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.converter.control.GraphConverter;

public class ConverterExecutor extends AbstractExecutor {

	@Override
	public void execute(ExecutorArgs args) {
		ConverterExecutorArgs converterArgs = (ConverterExecutorArgs) args;
		CdfConverter graphConverter = new GraphConverter(converterArgs.getInputFile());
		try {
			CdfTree cdfTree = graphConverter.createElements();
			cdfTree.writeToFile(converterArgs.getOutputFile());
		} catch (CdfWriterException e) {
			e.printStackTrace();
		}
	}

}
