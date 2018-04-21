package codemetropolis.toolchain.gui.utils;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Test cases for the {@link PropertyCollector} class.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 *
 */
public class PropertyCollectorTest {
	
	PropertyCollector instance = new PropertyCollector();
	URL urlToDictionary  = this.getClass().getResource("/" + "output_test.xml");
	InputStream stream = null;
	DocumentBuilder builder = null;
	Document doc = null;

	@Test
	public void testGetFromCdf() throws FileNotFoundException{
		//URL urlToDictionary  = this.getClass().getResource("/" + "output_test.xml");
		String filePath = urlToDictionary.getPath();
		Map<String, List<Property>> map = null;
		
		map = instance.getFromCdf(filePath);
		
		List<Property> list = map.get("package");
		Assert.assertNotNull(list);
	}
	

	@Test
	public void testIsValidElement() throws Exception{
		
		stream = urlToDictionary.openStream();
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(stream);
			
		Element rootElement = doc.getDocumentElement();
		rootElement.normalize();
		boolean result = instance.isValidElement(rootElement);
		
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void testIsNotValidElement() throws Exception{
		
		stream = urlToDictionary.openStream();
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(stream);
		
		Element rootElement = doc.getDocumentElement();
		rootElement.normalize();
		rootElement.setAttribute("type", "AdeGw");
		boolean result = instance.isValidElement(rootElement);
		
		Assert.assertEquals(result, false);
	}

	@Test
	public void testGetPropertyList() throws Exception{
		
		stream = urlToDictionary.openStream();
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(stream);
		
		Element rootElement = doc.getDocumentElement();
		rootElement.normalize();
		NodeList children = rootElement.getChildNodes();
		
		List<Property> propertyList = new ArrayList<Property>();
		
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			//When we found the 'properties' tag, we collect the list of properties contained by it.
			if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("properties")) {
				propertyList = instance.getPropertyList((Element)child);
				break;
			}
		}
		
		Assert.assertNotEquals(0, propertyList.size());
	}
	
	
}
