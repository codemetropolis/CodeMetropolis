package codemetropolis.toolchain.gui.utils;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is responsible for providing information what properties/metrics belong to the individual source code element types.
 *  @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class PropertyCollector {
	/**
	 * Those source code element types, whose properties wanted to be collected.
	 */
	public static final Set<String>  acceptedTypes = new HashSet<String>(Arrays.asList(
		     new String[] {"package","class","attribute","method"}
	));
	
	/**
	 * The String key contains the name of the source code element type. The value assigned to the key is a list with the properties (<name, type> pairs).
	 */
	private Map<String, List<Property>> propertyMap;
	
	/**
	 * Class initialization.
	 */
	public PropertyCollector() {
		initializePropertyMap();
	}
	
	/**
	 * Initialize the property map.
	 */
	private void initializePropertyMap() {
		Map<String, List<Property>> tmpPropertyMap = new HashMap<String, List<Property>>();
		for(String type : acceptedTypes) {
			tmpPropertyMap.put(type, null);
		}
		propertyMap = tmpPropertyMap;
	}
	
	/**
	 * Gets the {@link Map} which contains the individual source code element types as keys and their metrics/properties as values.
	 * @param cdfFilePath The path from the cdf file from which the information will be read.
	 * @return The {@link Map} which contains the individual source code element types as keys and their metrics/properties as values.
	 */
	public Map<String, List<Property>> getFromCdf(String cdfFilePath) throws FileNotFoundException {
		try {
			
			initializePropertyMap();

			File file = new File(cdfFilePath);

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			Element rootElement = doc.getDocumentElement();
			rootElement.normalize();
				
			//This NodeList 'elementTags' below doesn't contains the root element.
			//The root element is also an 'element' tag (a package), that's why we need to try collecting its properties as well.
			if(isValidElement(rootElement)) {
				tryCollectProperties(rootElement);
			}
							
			NodeList elementTags = rootElement.getElementsByTagName("element");
			
			//Iteration through all of the rest 'element' tags (they represent source code element types). Trying to collect their properties.
			for(int i = 0; i < elementTags.getLength(); i++) {
				Element currentTag = (Element) elementTags.item(i);
				if(isValidElement(currentTag)) {
					tryCollectProperties(currentTag);
				}
			};

		} catch (ParserConfigurationException | SAXException | IOException e) {	
			e.printStackTrace();
		}
			
		return propertyMap;
	}
		
	/**
	 * Checks if the tag is a valid 'element' tag or not.
	 * @param element The {@link Element} which will be examined.
	 * @return The examined element is a valid 'element' tag or not.
	 */
	public boolean isValidElement(Element element) {
		return element.getTagName().equals("element") &&
			element.hasAttribute("type") &&
			acceptedTypes.contains(element.getAttribute("type"));
	}	
	
	/**
	 * Checks if the properties/metrics of the element have been ever gathered or not. If not, calls the {@code getPropertyList} method to collect them.
	 * @param element The 'element' tag which will be examined.
	 */
	private void tryCollectProperties(Element element) {
		List<Property> propertyList = null;
		//We will collect the properties of the source code element types only in that way, when we have never collected them before.
		//The element tag must have child nodes. (E.g. the 'properties' tag also a child element.)
		if(propertyList == null && element.hasChildNodes()) {
			NodeList children = element.getChildNodes();
			for(int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				//When we found the 'properties' tag, we collect the list of properties contained by it.
				if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("properties")) {
					propertyList = getPropertyList((Element)child);
					propertyMap.put(element.getAttribute("type"), propertyList);
					break;
				}
			}
		}
	}
		
	/**
	 * Collects the properties contained by the 'properties' tag.
	 * @param element The 'properties' tag of the 'element' tag.
	 * @return The list of the gathered properties/metrics.
	 */
	public List<Property> getPropertyList(Element element) {
		List<Property> result = new ArrayList<Property>();
		
		NodeList properties = element.getElementsByTagName("property");
		
		for(int i = 0; i < properties.getLength(); i++) {
			Element property = (Element) properties.item(i);
			if(property.hasAttribute("name") && property.hasAttribute("type")) {
				Property p = new Property();
				p.name = property.getAttribute("name");
				p.type = property.getAttribute("type");
				result.add(p);
			}
		}
		return result;
	}
	
	/**
	 * Displays the various properties (<name, type> pairs) of the source code elements.
	 */
	public void displayProperties() {
		for(String srcCodeElement : acceptedTypes) {
			System.out.println("Properties of source code element '" + srcCodeElement + "':");
			for(Property p : propertyMap.get(srcCodeElement)) {
				System.out.println(p.name + ": " + p.type);
			}
		}
	}
}