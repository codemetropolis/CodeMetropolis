package codemetropolis.toolchain.converter.browsinghistory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class BrowsingHistoryConverter extends CdfConverter {
	
	public static final String ROOT_NODE = "chrome_visited_sites";
	public static final String ITEM_NODE = "item";
	public static final String URL_NODE = "url";
	public static final String TITLE_NODE = "title";
	public static final String VISIT_ID_NODE = "visit_id";
	public static final String VISIT_COUNT_NODE = "visit_count";
	public static final String TYPED_COUNT_NODE = "typed_count";
	public static final String PROFILE_NODE = "profile";
	public static final String URL_LENGTH_NODE = "url_length";
	public static final String TRANSITION_TYPE_NODE = "transition_type";
	public static final String TRANSITION_QUALIFIERS_NODE = "transition_qualifiers";
	
	public BrowsingHistoryConverter(Map<String, String> params) {
		super(params);
	}

	@Override
	public CdfTree createElements(String source) throws CodeMetropolisException {
		
		File inputXml = null;
		try {
			inputXml = readFile(source);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Document document = null;
		try {
			document = createDocumentFromXmlFile(inputXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CdfElement rootElement = createElementsRecursively(document);

		return new CdfTree(rootElement);
	}
	
	public CdfElement createElementsRecursively(Node root) {
		
		CdfElement cdfElement = addNameAndTypeOfElement(root);
		
		if (cdfElement != null) {
				NodeList children = root.getChildNodes();
				
				if(children != null) {
					for (int c = 0; c < children.getLength(); c++) {
						
						if(cdfElement.getName() == ROOT_NODE || cdfElement.getName() == "#document") {
							
							cdfElement.addChildElement(createElementsRecursively(children.item(c)));
							
						}else if(cdfElement.getName() == ITEM_NODE) {
							
							String value = "";
							
							for(int k = 0; k < children.getLength(); k++) {
								
								if(children.item(k).getFirstChild() != null) {
									value = children.item(k).getFirstChild().getNodeValue();
								}
								
								if(children.item(k).getNodeName() != "#text") {
									cdfElement.addProperty(children.item(k).getNodeName(), value, 
											typeFromNodeValue(value));
								}

							}
						}
					}
				}
		}
		
		return cdfElement;
	}
	
	public CdfElement addNameAndTypeOfElement(Node root) {
		
		String nodeName = root.getNodeName();

		CdfElement cdfElement = new CdfElement(nodeName, nodeName);
			
		return cdfElement;
	}
	
	public CdfElement addProperties(CdfElement cdfElement, Node node) {
		String nodeName = node.getNodeName();
		
		if (nodeName == URL_NODE || nodeName == TITLE_NODE || nodeName == VISIT_ID_NODE || nodeName == VISIT_COUNT_NODE ||
			nodeName == TYPED_COUNT_NODE|| nodeName == PROFILE_NODE || nodeName == URL_LENGTH_NODE || 
			nodeName == TRANSITION_TYPE_NODE || nodeName == TRANSITION_QUALIFIERS_NODE) {
			cdfElement.addProperty(node.getNodeName(), node.getFirstChild().getNodeValue(), typeFromNodeValue(node.getFirstChild().getNodeValue()));
		}
		return cdfElement;
	}
	
	public CdfProperty.Type typeFromNodeValue(String nodeValue){
		if(Pattern.matches("\\-?\\d+", (CharSequence) nodeValue)) {
			return CdfProperty.Type.INT;
		}else {
			return CdfProperty.Type.STRING;
		}
	}
	
	public File readFile(String source) throws Exception {
		File inputXml = new File(source);
		
		if(!inputXml.getName().toLowerCase().endsWith(".xml")) {
			throw new Exception("The file isn't an xml file.");
		}
		
		return inputXml;
	}
	
	public Document createDocumentFromXmlFile(File inputXml){
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        try {
			document = documentBuilder.parse(inputXml);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
        
		return document;
	}

}
