package codemetropolis.toolchain.mapping.model;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;

/**
 * Test cases for the {@link Mapping} class.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 *
 */
public class MappingTest {
	
	public static String TEST_FILE_LOCATION = "./test/sourcemeter_mapping_example_2_0.xml";

	@Test
	public void testReadFromXMLShouldNotThrowException() {
		
		boolean exceptionThrown = false;
		
		try {
			Mapping.readFromXML(TEST_FILE_LOCATION);
		}catch (MappingReaderException e) {
			exceptionThrown = true;
		} catch (FileNotFoundException e) {
			exceptionThrown = true;
		}
		
		Assert.assertFalse(exceptionThrown);
	}
}
