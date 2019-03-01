package codemetropolis.toolchain.mapping.model;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.exceptions.CdfReaderException;
import codemetropolis.toolchain.mapping.control.MappingController;
import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;

/**
 * Test cases for the {@link MappingController} class.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 *
 */
public class MappingControllerTest {
	
	public static String MAPPING_FILE_LOCATION = "./test/sourcemeter_mapping_example_2_0.xml";
	public static String TEST_FILE_LOCATION = "./test/output_test.xml";
	

	@Test
	public void testCreateBuildablesFromCdfShouldNotThrowException() throws FileNotFoundException, MappingReaderException{
		Mapping mapping = Mapping.readFromXML(MAPPING_FILE_LOCATION);
		
		MappingController mController = new MappingController(mapping);
		boolean exceptionThrown = false;
		
		try {
			mController.createBuildablesFromCdf(TEST_FILE_LOCATION);
		} catch (CdfReaderException e) {
			exceptionThrown = true;
		}
		
		Assert.assertFalse(exceptionThrown);
	}

}
