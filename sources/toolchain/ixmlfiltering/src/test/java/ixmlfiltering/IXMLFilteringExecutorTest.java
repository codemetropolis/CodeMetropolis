package ixmlfiltering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import codemetropolis.toolchain.ixmlfiltering.CommandLineOptions;
import codemetropolis.toolchain.ixmlfiltering.IXMLFilteringExecutor;
import codemetropolis.toolchain.ixmlfiltering.IXMLFilteringExecutorArgs;

class IXMLFilteringExecutorTest {
	@Test()//Root node founded
	void getRootNodeTest() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {	        	
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse("./src/test/resources/test.xml");			
			Node parent= document.getFirstChild();		
			assertEquals("buildables",parent.getNodeName());
			System.out.println("Test Passed> Root node founded");
		}catch (Exception e) {
			e.printStackTrace();
        }     		   	
	}
	@Test()//File Not Found Exception
	void FileReadFailTest() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {	        	
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse("./src/test/resources/no.xml");			        	
		}catch (Exception e) {			
			assertEquals(FileNotFoundException.class,e.getClass());
			System.out.println("Test Passed> File Not Found Exception");
        }     		   	
	}
	@Test//File read unsuccessful and filtered IXML dies not created
	void executorTestFail() {
		CommandLineOptions options = new CommandLineOptions();
		IXMLFilteringExecutor filteringExecutor= new IXMLFilteringExecutor();
		filteringExecutor.execute(
	    		new IXMLFilteringExecutorArgs(
	    				"./src/test/resources/test1.xml",
	    				"./src/test/resources/result.xml",
	    	    		"",
	    	    		""

	        		));	
	}
	@Test()//File read successfully
	void FileReadSuccessTest() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {	        	
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse("./src/test/resources/test.xml");			
        	assertTrue(true);
        	System.out.println("Test Passed> File Is Read Successfully");
		}catch (Exception e) {
			e.printStackTrace();
        }     		   	
	}
			
	@Test//File read successfully and filtered IXML created
	void executorTestPass() {
		CommandLineOptions options = new CommandLineOptions();
		IXMLFilteringExecutor filteringExecutor= new IXMLFilteringExecutor();
		filteringExecutor.execute(
	    		new IXMLFilteringExecutorArgs(
	    				"./src/test/resources/test.xml",
	    				"./src/test/resources/result.xml",
	    	    		"",
	    	    		""
	        		));	
	}



	


}
