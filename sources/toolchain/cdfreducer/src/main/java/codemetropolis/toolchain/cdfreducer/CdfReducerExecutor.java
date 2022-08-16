package codemetropolis.toolchain.cdfreducer;

import codemetropolis.toolchain.cdfreducer.exceptions.CdfReducerWriterException;
import codemetropolis.toolchain.cdfreducer.model.CdfReducerElement;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class CdfReducerExecutor extends AbstractExecutor {

	/**
	 * First it unmarshalls the source .xml document,
	 * then calls the writeToFile function to create the new .xml document
	 * @param args contains the input file, output file and params from the -p switch
	 * @return
	 * @author h759770 Fülep Martin Patrik
	 */
	@Override
	public boolean execute(ExecutorArgs args){
		CdfReducerExecutorArgs cdfArgs = (CdfReducerExecutorArgs) args;
		File inputFile = new File(cdfArgs.getInputFile());
		boolean fileExists = inputFile.exists();
		if (!fileExists) {
			System.out.println("Input File doesn't exist!");
			return false;
		}

		FileLogger.load(Settings.get("cdfreducer_log_file"));

		try {
			JAXBContext context = JAXBContext.newInstance(CdfReducerElement.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			CdfReducerElement cdfReducerElement = (CdfReducerElement) unmarshaller.unmarshal(inputFile);
			cdfReducerElement.writeToFile(cdfArgs.getOutputFile(), cdfArgs.getParams());
		} catch (JAXBException e) {
			FileLogger.logError("Couldn't unmarshall input file, because it is not valid!\n", e);
			System.out.println("Couldn't unmarshall input file, because it is not valid!");
			return false;
		}

		System.out.println("CdF reducing was a success!");

		return true;
	}
	
}
