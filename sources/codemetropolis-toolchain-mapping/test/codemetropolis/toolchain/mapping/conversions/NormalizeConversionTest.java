package codemetropolis.toolchain.mapping.conversions;

import static org.junit.Assert.assertEquals;

import codemetropolis.toolchain.mapping.model.Limit;
import org.junit.Test;
import org.junit.Before;

public class NormalizeConversionTest {

	public Conversion conversion;

	@Before
	public void setUp() throws Exception {
		conversion = new NormalizeConversion();
	}
	
	@Test
	public void testApply() {
		Limit limit = new Limit();
		limit.add(0);
		limit.add(10);
		double valueToConvert = 100.;
		double normalized = (Double)conversion.apply(valueToConvert, limit);
		double expected = 10.;
		assertEquals(expected, normalized, 0);
	}
	
}
