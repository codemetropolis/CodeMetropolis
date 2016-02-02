package codemetropolis.toolchain.converter;

import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlWriterException;
import codemetropolis.toolchain.commons.converter.IConverter;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.converter.control.ConverterController;
import codemetropolis.toolchain.converter.control.GraphConverter;

public class ConverterExecutor extends AbstractExecutor{

	@Override
	public void execute(ExecutorArgs args) {
		ConverterExecutorArgs converterArgs = (ConverterExecutorArgs) args;
		IConverter gConverter = new GraphConverter(converterArgs.getInputFile());
		ConverterController converterController = new ConverterController(gConverter);
		converterController.createElementsFromFile();
		try {
			converterController.writeToFile(converterArgs.getOutputFile());
		} catch (CmxmlWriterException e) {
			e.printStackTrace();
		}
		
	}

}
