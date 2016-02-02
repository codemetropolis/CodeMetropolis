package codemetropolis.toolchain.converter.control;

import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlWriterException;
import codemetropolis.toolchain.commons.converter.ElementList;
import codemetropolis.toolchain.commons.converter.IConverter;

public class ConverterController {
	
	private IConverter graphConverter;

	public ConverterController(IConverter graphConverter){
		this.graphConverter = graphConverter;
	}
	
	public void createElementsFromFile(){
		graphConverter.createElements();
	}
	
	public void writeToFile(String filePath) throws CmxmlWriterException{ 
		ElementList elementList = graphConverter.getElementList();
		elementList.writeToFile(filePath);

	}

	
}
