package codemetropolis.toolchain.mapping.conversions;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for the {@link Conversion} class.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 *
 */
public class ConversionTest {
	
	@Test
	public void testCreateFromName() {
		String conversionType = "multiply";
		Conversion obj = Conversion.createFromName(conversionType);
		boolean result = obj instanceof MultiplyConversion;
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void testCreateFromNameNull() {
		String conversionType = "sGfijkL";
		Conversion result = Conversion.createFromName(conversionType);		
		
		Assert.assertNull(result);
	}
}
