package codemetropolis.toolchain.converter.browsinghistory.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.browsinghistory.BrowsingHistoryConverter;

import org.w3c.dom.Document;

public class BrowsingHistoryConverterTests {
	
	public static final String NODE_VALUE1 = "43";
	public static final String NODE_VALUE2 = "Text43";
	public static final String ITEM_NODE = "item";
	public static final String TEST_INPUT_FILE = "./src/main/java/codemetropolis/toolchain/converter/browsinghistory/test/testFile2.xml";
	
	BrowsingHistoryConverter converter = new BrowsingHistoryConverter(null);
	
	
	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = null;
    Document document = null;
	
	@Test
	public void testTypeFromNodeValueWithCorrectValue() {

		CdfProperty.Type result = converter.typeFromNodeValue(NODE_VALUE1);
		CdfProperty.Type expectedType = CdfProperty.Type.INT;
		assertEquals(result, expectedType);
		
	}
	
	@Test
	public void testTypeFromNodeValueWithCorrectValue2() {

		CdfProperty.Type result = converter.typeFromNodeValue(NODE_VALUE2);
		CdfProperty.Type expectedType = CdfProperty.Type.STRING;
		assertEquals(result, expectedType);
		
	}
	
	@Test
	public void testTypeFromNodeValueWithIncorrectValue() {

		CdfProperty.Type result = converter.typeFromNodeValue(NODE_VALUE1);
		CdfProperty.Type expectedType = CdfProperty.Type.STRING;
		assertNotEquals(result, expectedType);
		
	}
	
	@Test
	public void testTypeFromNodeValueWithIncorrectValue2() {

		CdfProperty.Type result = converter.typeFromNodeValue(NODE_VALUE2);
		CdfProperty.Type expectedType = CdfProperty.Type.INT;
		assertNotEquals(result, expectedType);
		
	}
	
	@Test
	public void testAddPropertiesWithCorrectResult() throws Exception {
		
		List<CdfProperty> expectedProperties = new ArrayList<CdfProperty>();
		CdfElement resultElement = new CdfElement(ITEM_NODE, ITEM_NODE);

	    documentBuilder = documentBuilderFactory.newDocumentBuilder();
		document = documentBuilder.parse(TEST_INPUT_FILE);
		
		converter.addProperties(resultElement, document);
		
		System.out.println(resultElement.getProperty(ITEM_NODE));
		
		assertEquals(resultElement.getProperties(), expectedProperties);
	}
	
}
