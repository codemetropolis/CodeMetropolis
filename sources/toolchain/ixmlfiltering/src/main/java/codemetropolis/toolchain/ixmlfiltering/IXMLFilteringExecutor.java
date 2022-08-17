package codemetropolis.toolchain.ixmlfiltering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import codemetropolis.toolchain.commons.cmxml.Attribute;
import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;

import org.w3c.dom.*;

/**
 * The Executor for the IXMLFiltering
 * This class processes an XML file and excludes certain items
 * @author Narad Márton N4P26J h879805
 */

public class IXMLFilteringExecutor extends AbstractExecutor {

    /**
     * First it unmarshalls the source .xml document,
     * then calls the writeToFile function to create the new .xml document
     * @param args contains the input file, output file and params from the -p switch
     * @return true if sucessful, false is an error occurred
     */
    @Override
    public boolean execute(ExecutorArgs args){
    	System.out.println("Execute started");
    	IXMLFilteringExecutorArgs IXMLArgs = (IXMLFilteringExecutorArgs) args;
    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();    	
      File inputFile = new File(IXMLArgs.getInputFile());
      boolean fileExists = inputFile.exists();
        if (!fileExists) try {
            throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            System.out.println("Input File doesn't exist!");
            return false;
        }

        try {
        	
        	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        	Document document = docBuilder.parse(inputFile);
        	Pattern propertyNameRegex=IXMLArgs.getPropertyNameRegex();
        	Pattern propertyValueRegex=IXMLArgs.getPropertyValueRegex();       	
        	Node parent= document.getFirstChild();
        	System.out.println(parent);
        	Queue<Node> queue=new LinkedList<Node>();
        	queue.add(parent);
        	while(!queue.isEmpty()) {
        		Node current=queue.poll();
        		if(current.getNodeName().equals("buildable") && isRemoveable(current,propertyNameRegex,propertyValueRegex)){
        			current.getParentNode().removeChild(current);
        			continue;
        		}
        		NodeList children=current.getChildNodes();
        			for (int i = 0; i < children.getLength(); i++) {
        				queue.add(children.item(i));
        		}        	
        	}
        	TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(IXMLArgs.getOutputFile());
            transformer.transform(domSource, streamResult);            
            System.out.println("Done creating XML File");
        	
    	} catch (Exception e) {
        e.printStackTrace();
        return false;
        }     
        return true;
    }
    /**
     * The isRemoveable method for the IXMLFiltering
     * This class processes an XML file and excludes certain items
     * @author Polnik Ádám LYIMCC h984893
     */

    	private boolean isRemoveable(Node node,Pattern propertyNameRegex, Pattern propertyValueRegex) {
    		NamedNodeMap attributes =  node.getAttributes();
    		
	   		for (int i = 0; i<attributes.getLength();i++) {
		      Node att = attributes.item(i);
		      Matcher nameMatcher = propertyNameRegex.matcher(att.getNodeName());
		      Matcher valueMatcher = propertyValueRegex.matcher(att.getNodeValue());
		      if(nameMatcher.find() && valueMatcher.find()) {
		    	  return true;
		      }
	   		}
	   		return false;
	    }

}

