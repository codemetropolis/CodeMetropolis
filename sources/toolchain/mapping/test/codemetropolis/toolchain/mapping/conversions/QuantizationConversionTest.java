package codemetropolis.toolchain.mapping.conversions;

import static org.junit.Assert.assertEquals;

import codemetropolis.toolchain.mapping.model.Limit;
import codemetropolis.toolchain.mapping.model.Parameter;

import org.junit.Test;
import org.junit.Before;

public class QuantizationConversionTest {

	public Conversion conversion;

	@Before
	public void setUp() throws Exception {
		conversion = new QuantizationConversion();
		conversion.addParameter(new Parameter("level0", "1"));
		conversion.addParameter(new Parameter("level1", "2"));
		conversion.addParameter(new Parameter("level2", "4"));
	}
	
	@Test
	public void testApply() {
		Limit limit = new Limit();
		limit.add(50);
		limit.add(0);
		limit.add(100);
		double valueToConvert = 54;
		String actual = (String) conversion.apply(valueToConvert, limit);
		assertEquals("2", actual);
	}
	
}
