package codemetropolis.toolchain.mapping.model;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;
import codemetropolis.toolchain.mapping.exceptions.MissingResourceException;
import codemetropolis.toolchain.mapping.exceptions.NotSupportedLinkingException;

/**
 * Test cases for the {@link Linking} class.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 *
 */
public class LinkingTest {
	
	public static String TEST_FILE_LOCATION = "./test/sourcemeter_mapping_example_2_0.xml";

	@Test
	public void testValidateShouldNotThrowException() throws MappingReaderException, FileNotFoundException{
		
		boolean exceptionThrown = false;
		
		Mapping mapping = Mapping.readFromXML(TEST_FILE_LOCATION);
		
		Linking linking = mapping.getLinkings().get(0);
		List<Constant> resources = mapping.getResources();
		
		try {
			linking.validate(resources);
		} catch (NotSupportedLinkingException e) {
			exceptionThrown = true;
		} catch (MissingResourceException e) {
			exceptionThrown = true;
		}
		
		Assert.assertFalse(exceptionThrown);
	}
	
	@Test
	public void testValidateShouldThrowException() throws MappingReaderException, FileNotFoundException{
		
		boolean exceptionThrown = false;
		
		String badTarget = "skyscraper";
		
		Mapping mapping = Mapping.readFromXML(TEST_FILE_LOCATION);
		
		Linking linking = mapping.getLinkings().get(0);
		linking.setTarget(badTarget);
		List<Constant> resources = mapping.getResources();
		
		try {
			linking.validate(resources);
		} catch (NotSupportedLinkingException e) {
			exceptionThrown = true;
		} catch (MissingResourceException e) {
			exceptionThrown = true;
		}
		
		Assert.assertTrue(exceptionThrown);
	}
	
}
