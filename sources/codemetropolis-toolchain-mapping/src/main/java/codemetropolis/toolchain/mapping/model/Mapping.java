package codemetropolis.toolchain.mapping.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.mapping.conversions.Conversion;
import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;
import codemetropolis.toolchain.mapping.exceptions.NotSupportedLinkingException;

public class Mapping {
	private Map<String, String> constants;
	private List<Linking> linkings;
	public static Map<String, Type> correctLinking = new HashMap<>();
	
	private Mapping() {
		constants  = new HashMap<String, String>();
		linkings = new ArrayList<Linking>();
	}
	
	public Map<String, String> getConstants() {
		return new HashMap<String, String>(constants);
	}

	public List<Linking> getLinkings() {
		return new ArrayList<Linking>(linkings);
	}

	public static Mapping readFromXML(String filename) throws MappingReaderException, NotSupportedLinkingException {
		
		Mapping result = new Mapping();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();;
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(filename));
			document.getDocumentElement().normalize();
			result.constants = parseResources(document);
			result.linkings = parseLinkings(document);
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new MappingReaderException(e);
		}
		
		return result;
	}
	
	private static Map<String, String> parseResources(Document document) {
		Map<String, String> result = new HashMap<String, String>();
		NodeList constantList = document.getElementsByTagName("constant");
		
		for(int i = 0; i < constantList.getLength(); i++) {
			Node constant = constantList.item(i);
			String constantName = constant.getAttributes().getNamedItem("name").getNodeValue();
			String constantValue = constant.getAttributes().getNamedItem("value").getNodeValue();
			result.put(constantName, constantValue);
		}
		return result;
	}
	
	private static List<Linking> parseLinkings(Document document) throws NotSupportedLinkingException {
		List<Linking> result = new ArrayList<Linking>();
		
		NodeList linkingList = document.getElementsByTagName("linking");
		NodeList nodeList;
		NodeList conversionList;
		NodeList parameterList;
		Node node;
		
		String sourceName = null;
		String sourceFrom = null;
		String targetName = null;
		String targetTo = null;
		
		for(int i = 0; i < linkingList.getLength(); i++) {
			Linking linking = new Linking();
			nodeList = linkingList.item(i).getChildNodes();
			
			for(int j = 0; j < nodeList.getLength(); j++) {
				node = nodeList.item(j);
					
				if(node.getNodeName().equals("source")) {
					sourceName = node.getAttributes().getNamedItem("name").getNodeValue();
					sourceFrom = node.getAttributes().getNamedItem("from").getNodeValue();
				}
				
				if(node.getNodeName().equals("target")) {
					targetName = node.getAttributes().getNamedItem("name").getNodeValue();
					targetTo = node.getAttributes().getNamedItem("to").getNodeValue();
				}
				
				if(node.getNodeName().equals("conversions")) {
					Element e = (Element)node;
					conversionList = e.getElementsByTagName("conversion");
					
					for(int k = 0; k < conversionList.getLength(); k++) {
						Element conversionElement = (Element)conversionList.item(k);
						Conversion conversion = Conversion.createFromName(conversionElement.getAttribute("type"));
						parameterList = conversionElement.getElementsByTagName("parameter");
						
						for(int l = 0; l < parameterList.getLength(); l++) {
							Element parameter = (Element)parameterList.item(l);
							conversion.addParameter(
									new Parameter(
											parameter.getAttribute("name"),
											parameter.getAttribute("value")
											)
									);
						}
						linking.addConversion(conversion);
					}
				}
			}
			
			linking.setSourceName(sourceName);
			linking.setSourceFrom(sourceFrom);
			linking.setTargetName(targetName);
			linking.setTargetTo(targetTo);
			
			if(!linking.isSupported()) {
				throw new NotSupportedLinkingException(String.format(Resources.get("invalid_linking_error"), sourceName, sourceFrom, targetName, targetTo));
			} else if(!isSameSourceAndTarget(sourceName, targetName)){
				//TODO: 
				System.out.println("Warning: Multiple element mapped to the same object!");
			} else if(checkVisualPropAndProperties(linking, result)){
				//TODO: 
				throw new NotSupportedLinkingException(String.format(Resources.get("invalid_linking_error"), sourceName, sourceFrom, targetName, targetTo));
			}
			result.add(linking);				
		}
		return result;
	}
	
	private static boolean isSameSourceAndTarget(String sourceName, String targetName){
		Type type = getTypeFromTargetName(targetName.toLowerCase());

		if(type == null){
			return false;
		}
		if(correctLinking.get(sourceName.toLowerCase()) == null){
			correctLinking.put(sourceName.toLowerCase(), type);
			return true;
		} else if(!type.equals(correctLinking.get(sourceName.toLowerCase()))){
			return false;
		} else {
			return true;
		}
	}
	
	private static Type getTypeFromTargetName(String targetName){
			switch(targetName) {
			case "ground":
				return Type.GROUND;
			case "garden":
				return Type.GARDEN;
			case "floor":
				return Type.FLOOR;
			case "cellar":
				return Type.CELLAR;
			default: return null;
		}
	}

	public Type getType(String sourceName){
		return correctLinking.get(sourceName.toLowerCase());
	}
	
	private static boolean checkVisualPropAndProperties(Linking linking, List<Linking> linkings){
		for(Linking l : linkings){
			if(l.getSourceName().equals(linking.getSourceName()) && 
			   l.getTargetName().equals(linking.getTargetName()) &&
			   l.getTargetTo().equals(linking.getTargetTo())){
				return true;
			}
		}
		return false;
	}
}
