package codemetropolis.toolchain.converter;

import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.commons.cdf.exceptions.CdfWriterException;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.converter.control.ConverterLoader;

public class ConverterExecutor extends AbstractExecutor {
	
	@Override
	public boolean execute(ExecutorArgs args) {
		ConverterExecutorArgs converterArgs = (ConverterExecutorArgs) args;
		CdfConverter converter = createConverterBasedOnTerminalArgs(converterArgs);

		print(Resources.get("converting_to_cdf"));
		CdfTree cdfTree = createElements(converter, converterArgs);
		if (cdfTree == null) {
			return false;
		}
		print(Resources.get("converting_to_cdf_done"));

		print(Resources.get("printing_cdf"));
		if (!writeCdfToFile(cdfTree, converterArgs)) {
			return false;
		}
		print(Resources.get("printing_cdf_done"));

		return true;
	}

	private CdfConverter createConverterBasedOnTerminalArgs(ConverterExecutorArgs converterArgs) {
		CdfConverter converter = ConverterLoader.load(converterArgs.getType(), converterArgs.getParams(),
				converterArgs.getVerboseMode());
		converter.addConverterEventListener(event -> print(event.getMessage()));
		return converter;
	}

	private CdfTree createElements(CdfConverter converter, ConverterExecutorArgs converterArgs) {
		try {
			return converter.createElements(converterArgs.getSource());
		} catch (CodeMetropolisException e) {
			printError(e, e.getMessage());
			return null;
		} catch (Exception e) {
			printError(e, Resources.get("converter_error"));
			return null;
		}
	}

	private boolean writeCdfToFile(CdfTree cdfTree, ConverterExecutorArgs converterArgs) {
		try {
			cdfTree.writeToFile(converterArgs.getOutputFile());
			return true;
		} catch (CdfWriterException e) {
			printError(e, Resources.get("cdf_writer_error"));
			return false;
		}
	}
}
