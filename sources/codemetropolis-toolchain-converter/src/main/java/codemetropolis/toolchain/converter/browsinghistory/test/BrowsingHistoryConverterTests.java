package codemetropolis.toolchain.converter.browsinghistory.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;
import codemetropolis.toolchain.converter.browsinghistory.BrowsingHistoryConverter;

import org.w3c.dom.Document;

public class BrowsingHistoryConverterTests {
	
	public static final String NODE_VALUE1 = "43";
	public static final String NODE_VALUE2 = "Text43";
	public static final String ITEM_NODE = "item";
	public static final String TEST_INPUT_FILE = "./src/main/java/codemetropolis/toolchain/converter/browsinghistory/test/testFile2.xml";
	public static final String TEST_INPUT_FILE2 = "./src/main/java/codemetropolis/toolchain/converter/browsinghistory/test/testFile3.txt";

	BrowsingHistoryConverter converter = new BrowsingHistoryConverter(null);
	
	
	DocumentBuilderFactory documentBuilderFactory = null;
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

		documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    documentBuilder = documentBuilderFactory.newDocumentBuilder();
		document = documentBuilder.parse(TEST_INPUT_FILE);
		
		converter.addProperties(resultElement, document);
		
		assertEquals(resultElement.getProperties(), expectedProperties);
	}
	
	@Test
	public void testCreateElementsRecursivelyWithCheckChildElements() throws Exception {

		documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    documentBuilder = documentBuilderFactory.newDocumentBuilder();
		document = documentBuilder.parse(TEST_INPUT_FILE);
		
		CdfElement resultElement = converter.createElementsRecursively(document);
		
		assertEquals(resultElement.getNumberOfChildren(), 1);
	}
	
	@Test
	public void testCreateElementsRecursivelyWithCheckChildElements2() throws Exception {

		documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    documentBuilder = documentBuilderFactory.newDocumentBuilder();
		document = documentBuilder.parse(TEST_INPUT_FILE);
		
		CdfElement resultElement = converter.createElementsRecursively(document);
		
		assertEquals(resultElement.getChildElements().get(0).getChildElements().size(), 0);
	}
	
	@Test
	public void testReadFileWithCorrectFile() throws Exception  {
		File expectedFile = new File(TEST_INPUT_FILE);
		File resultFile = converter.readFile(TEST_INPUT_FILE);
		
		assertEquals(resultFile, expectedFile);
	}
	
	@Test
	public void testReadFileWithIncorrectFile() throws Exception  {
		
		boolean isThrown = false;
		
		try {
			File resultFile = converter.readFile(TEST_INPUT_FILE2);
		}catch(Exception e) {
			isThrown = true;
		}
		
		assertTrue(isThrown);
	}
	
	
	@Test
	public void testCreateDocumentFromXmlFileWithCorrectFile() {

		boolean isThrown = false;
		File file = new File(TEST_INPUT_FILE);
		
		try {
			converter.createDocumentFromXmlFile(file);
		}catch(Exception e) {
			isThrown = true;
		}
		
		assertFalse(isThrown);
	}
	
	@Test
	public void testCreateDocumentFromXmlFileWithCorrectFile2() throws Exception {

		File file = converter.readFile(TEST_INPUT_FILE);

		documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    documentBuilder = documentBuilderFactory.newDocumentBuilder();
	    document = documentBuilder.parse(TEST_INPUT_FILE);
	    
		Document resultDocument = converter.createDocumentFromXmlFile(file);
		
		assertNotEquals(document, resultDocument);
	}
	
	@Test
	public void testCreateElementsShouldNotThrowException() {
		
		boolean isThrown = true;
		
		try {
			CdfTree resultTree = converter.createElements(TEST_INPUT_FILE);
			isThrown = false;
		}catch(CodeMetropolisException e) {
			e.printStackTrace();
		}
		
		assertFalse(isThrown);
	}
}
