package codemetropolis.toolchain.converter.control;

import codemetropolis.toolchain.commons.cdf.CdfElementTree;
import codemetropolis.toolchain.commons.cdf.CdfConverter;
import codemetropolis.toolchain.commons.cdf.exceptions.CdfWriterException;

public class ConverterController {
	
	private CdfConverter graphConverter;

	public ConverterController(CdfConverter graphConverter){
		this.graphConverter = graphConverter;
	}
	
	public void createElementsFromFile(){
		graphConverter.createElements();
	}
	
	public void writeToFile(String filePath) throws CdfWriterException{ 
		CdfElementTree elementTree = graphConverter.getElementList();
		elementTree.writeToFile(filePath);
	}

	
}
