package codemetropolis.toolchain.converter.browsinghistory.test;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.browsinghistory.BrowsingHistoryConverter;
import junit.framework.TestCase;

public class BrowsingHistoryConverterTest extends TestCase {
	
	String nodeValue = "43";
	BrowsingHistoryConverter conv = new BrowsingHistoryConverter(null);
	
	@Test
	public void testTypeFromNodeValueWithCorrectValue() {

		CdfProperty.Type result = conv.typeFromNodeValue(nodeValue);
		CdfProperty.Type expectedType = CdfProperty.Type.INT;
		assertEquals(result, expectedType);
		
	}
	
	@Test
	public void testTypeFromNodeValueWithIncorrectValue() {

		CdfProperty.Type result = conv.typeFromNodeValue(nodeValue);
		CdfProperty.Type expectedType = CdfProperty.Type.STRING;
		assertNotSame(result, expectedType);
		
	}

}
