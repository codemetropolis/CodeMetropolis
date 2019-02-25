package codemetropolis.toolchain.metrics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import codemetropolis.toolchain.commons.executor.AbstractExecutor;
import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.metrics.util.Descriptions;

/**
 * Class for creating a csv file with metrics for in-game display.
 * @author Dora Borsos {@literal <BODWACT.SZE>}
 */

public class MetricsExecutor extends AbstractExecutor {
	
	/**
	 * Get the path of the xml file with the coordinates, the xml file with the metrics and the metrics threshold file holding the threshold values.
	 * If all of the files are available, store the metrics and coords in a csv file, in the current MC world's folder (by calling {@link MetricsExecutor#createMetricsCsv(String, String, String, Map)}).
	 * Only SourceMeter metrics are accepted.
	 * @param args contains the paths and the project name set by the user.
	 * @return If csv creation is successful, return true; if any of the files is missing, or the csv generation failed, return false.
	 */
	
	@Override
	public boolean execute(ExecutorArgs args) {
		String message = String.format("%s%s", Resources.get("metrics_generating_done"), Resources.get("metrics_generating_began"));
		FileLogger.logInfo(message);
		
		MetricsExecutorArgs metricsArgs = (MetricsExecutorArgs) args;
		if (metricsArgs.getType().toString() != "SOURCEMETER") {
			message = String.format("%s%s", Resources.get("error_prefix"), Resources.get("sourcemeter_only_error"));
	    	System.err.println(message);
	    	FileLogger.logInfo(message);
	    	return false;
		}
		
		String converterPath = metricsArgs.getCmRoot() + File.separator + "converter-results.xml";		String placingPath = metricsArgs.getCmRoot() + File.separator + "placing-results.xml";
		String outputPath = metricsArgs.getMinecraftRoot() + File.separator + "saves" + File.separator + metricsArgs.getProjectName() + File.separator + "metrics.csv";
		
		if (!checkIfXmlExists(placingPath) || !checkIfXmlExists(converterPath)) {
			return false;
		}
		
		Map<String, String> thresholds = new HashMap<String, String>();
		if (!getThresholds(thresholds, metricsArgs.getThresholdPath())){
			return false;
		}
		
		return createMetricsCsv(converterPath, placingPath, outputPath, thresholds);
	}
	
	
	/**
	 * Check if the XML file exists.
	 * @param xmlPath the path of the XML file.
	 * @return true if the XML file exists, is readable and is a file; else return false.
	 */
	
	public boolean checkIfXmlExists(String xmlPath) {
		File xml = new File(xmlPath);
		
		if (xml.exists() && xml.isFile() && xml.canRead()) {
			return true;
		}
		else {
			String message = String.format("%s%s%s", Resources.get("error_prefix"), Resources.get("xml_not_found"), xmlPath);
	    	System.err.println(message);
	    	FileLogger.logInfo(message);
			return false;
		}
	}
	
	/**
	 * Geting the metric thresholds and their default values.
	 * @param thresholds Map storing the values.
	 * @param thresholdPath Path of the thresholds file.
	 * @return False on any error occurring while processing data, else true.
	 */
	 
	public boolean getThresholds(Map<String, String> thresholds, String thresholdPath){
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    Document document = docBuilder.parse(new File(thresholdPath));
		    
			NodeList nodeList = document.getElementsByTagName("threshold");
		        for (int i = 0; i < nodeList.getLength(); i++) {
		            Node node = nodeList.item(i);
		            if (node.getNodeType() == Node.ELEMENT_NODE) {
		            	Element elem = (Element) node;
		            	if (elem.getAttribute("Entity").equals("Method")){
		            		thresholds.put(elem.getAttribute("Mid"), elem.getAttribute("Value"));
		            	}
		            }
		        }
		} catch (SAXException | ParserConfigurationException | IOException e) {
			e.printStackTrace();
			return false;
		}
       
    	return true;
    }
	
	/**
	 * Generate the metrics.csv file in the gameworld's folder, which is the data source of the in-game metric display.
	 * Each row contains the six coordinates of the graphical representation of the code entity, followed by the metrics (display order: meaning, abbreviation, average value from SourceMeter and the calculated value).
	 * @param converterPath Path of the converter data, containing the metrics.
	 * @param placingPath Path of the placing data, containing the coordinates.
	 * @param outputPath Output path, which equals to %APPDATA%/.minecraft/saves/project-name/metrics.csv
	 * @param thresholds Map containing the metric thresholds.
	 * @return true if the data processing is successful (which doesn't equal to the creation of the file, since it is possible to have zero displayable entities, e.g. an empty package as the source). If the processing fails, the function returns false.
	 */
	
	public boolean createMetricsCsv(String converterPath,String placingPath, String outputPath, Map<String, String> thresholds) {
       	Map<Character, Integer> characterWidths = new HashMap<Character, Integer>();
       	setCharacterWidths(characterWidths);
       	int displayWidthBPoint = 230;
       	
        try {
        	new File(outputPath).delete();
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        	NodeList converterElements = docBuilder.parse(new File(converterPath)).getElementsByTagName("element");
        	NodeList placingElements = docBuilder.parse(new File(placingPath)).getElementsByTagName("buildable");
        	int placingElementsLength = placingElements.getLength();
        	
        	for (int i = 0; i < placingElementsLength; i++) {
        		Element placingElem = (Element) placingElements.item(i);
        		if (!placingElem.getAttribute("type").equals("decoration_floor")) {
        			int [] coords = new int[6];
        			Node placingChildNode = placingElem.getFirstChild();
        			if (placingElem.getAttribute("name").equals("")) continue;
        			
        			while (placingChildNode.getNextSibling() != null){
        				placingChildNode = placingChildNode.getNextSibling();
            	        if (placingChildNode.getNodeType() == Node.ELEMENT_NODE) {
            	            Element placingChildElement = (Element) placingChildNode;
            	            if (placingChildElement.getNodeName().equals("position")){
        	            		coords[0] = Integer.parseInt(placingChildElement.getAttribute("x"));
        	            		coords[1] = Integer.parseInt(placingChildElement.getAttribute("y"));
        	            		coords[2] = Integer.parseInt(placingChildElement.getAttribute("z"));
            	            }
            	            else if (placingChildElement.getNodeName().equals("size")){
            	            	coords[3] = Integer.parseInt(placingChildElement.getAttribute("x"));
            	            	coords[5] = Integer.parseInt(placingChildElement.getAttribute("z"));
            	            	if (placingElem.getAttribute("type").equals("cellar") || placingElem.getAttribute("type").equals("floor")) {
            	            		coords[4] = Integer.parseInt(placingChildElement.getAttribute("y"));
            	            	}
            	            	else {
            	            		coords[4] = 0;
            	            	}
            	            }
            	        }
            	    }
        			coords[3] = coords[0] + coords[3];
    	            coords[4] = coords[1] + coords[4];
    	            coords[5] = coords[2] + coords[5];
    	            
    	            // Initializing DOM traverse (XPath is slower and doesn't support escaped characters in name attributes)
    	            ArrayList<String> elementPath = new ArrayList<String>();
    	            while (placingChildNode.getParentNode() != null){
    	            	placingChildNode = placingChildNode.getParentNode();
    	            	if (placingChildNode.getNodeType() == Node.ELEMENT_NODE){
    	            		if (placingChildNode.getNodeName().equals("buildable") && !((Element) placingChildNode).getAttribute("name").equals("")){
    	            			elementPath.add(((Element) placingChildNode).getAttribute("name"));
    	            		}
    	            	}
    	            }
    	            ArrayList<String> metrics = new ArrayList<String>();
    	            Element converterElem = (Element) converterElements.item(0);
    	            int elementPathSize = elementPath.size();
    	            boolean elementFound = false;
    	            
    	            for (int j = elementPathSize - 1; j >= 0 ; j--){
    	            	elementFound = false;
    	            	NodeList elemNodes = converterElem.getElementsByTagName("element");
    	            	int elemNodesSize = elemNodes.getLength();
    	            	for (int k = 0; k < elemNodesSize; k++){
    	            		if (((Element) elemNodes.item(k)).getAttribute("name").equals(elementPath.get(j))){
    	            			converterElem = (Element) elemNodes.item(k);
    	            			elementFound = true;
    	            			continue;
    	            		}
    	            	}
    	            	if (!elementFound) continue;
    	            }
    		        if (elementFound){
		        		Node childNode = converterElem.getFirstChild();
		        	    while (childNode.getNextSibling() != null){
		        	        childNode = childNode.getNextSibling();
		        	        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
		        	            Element childElement = (Element) childNode;
		        	            if (childElement.getNodeName().equals("properties")){
		        	            	NodeList properties = childElement.getElementsByTagName("*");
		        	        		for (int k = 0; k < properties.getLength(); k++){
		        	        			Element property = (Element) properties.item(k);
		        	        			if (!property.getAttribute("name").equals("source_id") &&
		        	        				!property.getAttribute("name").equals("Name") &&
		        	        				!property.getAttribute("name").equals("LongName"))
		        	        			{
		        	        				String currentDescription = (Descriptions.get(property.getAttribute("name")) == null) ? "" : "(" + Descriptions.get(property.getAttribute("name")) + ")";
		        	        				String currentThreshold = (thresholds.get(property.getAttribute("name")) == null) ? "" : "(" + thresholds.get(property.getAttribute("name")) + ")";
		        	        				metrics.add(property.getAttribute("name") + currentDescription + currentThreshold + ": " + property.getAttribute("value"));
			        	        		}
		        	        		}
		        	            }
		        	        }
		        	    }
		        	    
		        	    if (metrics.size() > 0){
		        	    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, true))) {
		        	    		
		        	    		String outputLine = Integer.toString(coords[0]) + ',' +
		        	    				Integer.toString(coords[1]) + ',' +
		        	    				Integer.toString(coords[2]) + ',' +
		        	    				Integer.toString(coords[3]) + ',' +
		        	    				Integer.toString(coords[4]) + ',' +
		        	    				Integer.toString(coords[5]) + ',' +
		        	    				placingElem.getAttribute("name") + ',';
		        	    		
		        	    		String displayLine = "";
		        	    		
		        	    		for (int j = 0; j < metrics.size(); j++){                    		
		        	    			if (displayLine.isEmpty()){
		        	    				displayLine = metrics.get(j).toString();
		        	    			}
		        	    			else {
		        	    				int stringWidth = getStringWidth(displayLine, characterWidths);
		        	    				if (stringWidth > displayWidthBPoint){
		        	    					outputLine += displayLine + ',';
		        	    					j--;
		        	    				}
		        	    				else {
		        	    					outputLine += padStringByFontWidth(displayLine, stringWidth, displayWidthBPoint) + metrics.get(j) + ',';
		        	    				}
		        	    				
		        	    				displayLine = "";
		        	    			}
		        	    		}
		        	    		
		        	    		bw.write(outputLine + System.lineSeparator());
		        	    	}
		        	    }
    	            }
        		}
            }

        } catch (Exception e){
        	e.printStackTrace();
        	return false;
        }
        
        String message = String.format("%s%s", Resources.get("metrics_prefix"), Resources.get("metrics_generating_done"));
		FileLogger.logInfo(message);
		return true;
	}
    
    /**
     * Get the given string's input.
     * @param string The string to calculate the width for.
     * @param characterWidths Map containing the characters with their width values.
     * @return Display width of the string.
     */
    private int getStringWidth(String string, Map<Character, Integer> characterWidths){
    	int stringLength = string.length();
    	int width = 0;
    	for (int i = 0; i < stringLength; i++){
    		width += characterWidths.get(string.charAt(i));
    	}
    	return width;
    }
    
    /**
     * Padding the input string by the given breakpoint.
     * @param string String to convert.
     * @param originalWidth The original width of the given string.
     * @param widthBPoint The breakpoint.
     * @return The padded string.
     */
    private String padStringByFontWidth(String string, int originalWidth, int widthBPoint){
    	int width = originalWidth;
    	while (width < widthBPoint){
    		string += ' ';
    		width += 4;
    	}
    	return string;
    }
    
    /**
     * Private method for setting the font widths, used for calculating the metrics breakpoint in the UI.
     * @param characterWidths Widths mapped to characters
     */
    private void setCharacterWidths(Map<Character, Integer> characterWidths){
    	characterWidths.clear();
    	characterWidths.put(' ', 4);
    	characterWidths.put('!', 2);
    	characterWidths.put('"', 5);
    	characterWidths.put('#', 6);
    	characterWidths.put('$', 6);
    	characterWidths.put('%', 6);
    	characterWidths.put('&', 6);
    	characterWidths.put('\'', 3);
    	characterWidths.put('(', 5);
    	characterWidths.put(')', 5);
    	characterWidths.put('*', 5);
    	characterWidths.put('+', 6);
    	characterWidths.put(',', 2);
    	characterWidths.put('-', 6);
    	characterWidths.put('.', 2);
    	characterWidths.put('/', 6);
    	characterWidths.put('0', 6);
    	characterWidths.put('1', 6);
    	characterWidths.put('2', 6);
    	characterWidths.put('3', 6);
    	characterWidths.put('4', 6);
    	characterWidths.put('5', 6);
    	characterWidths.put('6', 6);
    	characterWidths.put('7', 6);
    	characterWidths.put('8', 6);
    	characterWidths.put('9', 6);
    	characterWidths.put(':', 2);
    	characterWidths.put(';', 2);
    	characterWidths.put('<', 5);
    	characterWidths.put('=', 6);
    	characterWidths.put('>', 5);
    	characterWidths.put('?', 6);
    	characterWidths.put('@', 7);
    	characterWidths.put('A', 6);
    	characterWidths.put('B', 6);
    	characterWidths.put('C', 6);
    	characterWidths.put('D', 6);
    	characterWidths.put('E', 6);
    	characterWidths.put('F', 6);
    	characterWidths.put('G', 6);
    	characterWidths.put('H', 6);
    	characterWidths.put('I', 4);
    	characterWidths.put('J', 6);
    	characterWidths.put('K', 6);
    	characterWidths.put('L', 6);
    	characterWidths.put('M', 6);
    	characterWidths.put('N', 6);
    	characterWidths.put('O', 6);
    	characterWidths.put('P', 6);
    	characterWidths.put('Q', 6);
    	characterWidths.put('R', 6);
    	characterWidths.put('S', 6);
    	characterWidths.put('T', 6);
    	characterWidths.put('U', 6);
    	characterWidths.put('V', 6);
    	characterWidths.put('W', 6);
    	characterWidths.put('X', 6);
    	characterWidths.put('Y', 6);
    	characterWidths.put('Z', 6);
    	characterWidths.put('[', 4);
    	characterWidths.put('\\', 6);
    	characterWidths.put(']', 4);
    	characterWidths.put('^', 6);
    	characterWidths.put('_', 6);
    	characterWidths.put('`', 0);
    	characterWidths.put('a', 6);
    	characterWidths.put('b', 6);
    	characterWidths.put('c', 6);
    	characterWidths.put('d', 6);
    	characterWidths.put('e', 6);
    	characterWidths.put('f', 5);
    	characterWidths.put('g', 6);
    	characterWidths.put('h', 6);
    	characterWidths.put('i', 2);
    	characterWidths.put('j', 6);
    	characterWidths.put('k', 5);
    	characterWidths.put('l', 3);
    	characterWidths.put('m', 6);
    	characterWidths.put('n', 6);
    	characterWidths.put('o', 6);
    	characterWidths.put('p', 6);
    	characterWidths.put('q', 6);
    	characterWidths.put('r', 6);
    	characterWidths.put('s', 6);
    	characterWidths.put('t', 4);
    	characterWidths.put('u', 6);
    	characterWidths.put('v', 6);
    	characterWidths.put('w', 6);
    	characterWidths.put('x', 6);
    	characterWidths.put('y', 6);
    	characterWidths.put('z', 6);
    	characterWidths.put('{', 5);
    	characterWidths.put('|', 2);
    	characterWidths.put('}', 5);
    	characterWidths.put('~', 7);
    }
        
}
