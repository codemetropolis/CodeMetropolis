package codemetropolis.toolchain.gui.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import codemetropolis.toolchain.gui.beans.BadConfigFileFomatException;

/**
 * Test class for testing the {@link BuildableSettings} class.
 *
 * @author Tamas Keri {@literal <KETWAAT.SZE>}
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class BuildableSettingsTest {
	
	BuildableSettings instance = new BuildableSettings();

	/**
     * @throws IOException if it is true.
     */
	@Test
	public void testNonValidateProperties() throws IOException {
		String buildableName = "FLOOR";
		String[] buildableProperties = {"sdg", "int"};
		boolean result = BuildableSettings.validateProperties(buildableName, buildableProperties);
		Assert.assertEquals(result, false);
	}

	/**
     * @throws IOException if it is false.
     */
	@Test
	public void testPassValidateProperties() throws IOException {
		String buildableName = "CELLAR";
		String[] buildableProperties = {"width"};
		boolean result = BuildableSettings.validateProperties(buildableName, buildableProperties);
		Assert.assertEquals(result, true);
	}

	@Test
	/**
	 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
	 */
	public void testDisplaySettings() throws FileNotFoundException{
		
		boolean exceptionThrown = false;
		
		try{
			instance.displaySettings();
		}
		catch(BadConfigFileFomatException e) {
			exceptionThrown = true;
		}
		
		//Assert.assertEquals(result, false);
		Assert.assertFalse(exceptionThrown);
	}
	
	@Test
	/**
	 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
	 */
	public void testReadSettings() throws FileNotFoundException{
		
		boolean exceptionThrown = false;
		
		try{
			instance.readSettings();
		}
		catch(BadConfigFileFomatException e) {
			exceptionThrown = true;
		}
		
		Assert.assertFalse(exceptionThrown);
	}

}