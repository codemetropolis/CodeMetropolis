package codemetropolis.toolchain.gui.utils;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for testing the {@link BuildableSettings}.
 *
 * @author Tamas Keri {@literal <KETWAAT.SZE>}
 */
public class BuildableSettingsTest {

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
}
