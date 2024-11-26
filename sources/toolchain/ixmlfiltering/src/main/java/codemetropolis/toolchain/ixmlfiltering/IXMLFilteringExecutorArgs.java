package codemetropolis.toolchain.ixmlfiltering;

import java.util.Map;
import java.util.regex.Pattern;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;
/**
 * The IXMLFilteringExecutorArgs for the IXMLFiltering
 * This class implements the arguments of the IXMLFilteringExecutor
 * @author Adam Polnik LIYMCC h984893
 */


public class IXMLFilteringExecutorArgs extends ExecutorArgs {
	
	  private String inputFile;
	  private String outputFile;
	  private Pattern propertyNameRegex;
	  private Pattern propertyValueRegex;
	    
    public IXMLFilteringExecutorArgs(String inputFile, String outputFile, String propertyNameRegex, String propertyValueRegex) {
        super();
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.propertyNameRegex = Pattern.compile(propertyNameRegex);
        this.propertyValueRegex = Pattern.compile(propertyValueRegex);
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

	public Pattern getPropertyNameRegex() {
		return propertyNameRegex;
	}
	public Pattern getPropertyValueRegex() {
		return propertyValueRegex;
	}



	
}
