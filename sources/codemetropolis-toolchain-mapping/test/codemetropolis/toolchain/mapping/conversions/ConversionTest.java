package codemetropolis.toolchain.mapping.conversions;

import static org.junit.Assert.assertEquals;

import codemetropolis.toolchain.mapping.model.Parameter;
import org.junit.Test;
import org.junit.BeforeClass;

public class ConversionTest {

	public static Conversion conversion;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		conversion = new NormalizeConversion();
		conversion.addParameters(
				new Parameter("p0name", "456456"),
				new Parameter("p1name", "p1value"),
				new Parameter("p2name", "3.1459"));
	}
	
	@Test
	public void testClearParameters() {
		conversion.clearParameters();
		assertEquals(0, conversion.getParameters().size());
	}

	@Test
	public void testAddParameter() {
		Parameter newParam = new Parameter("p3name", "p3value");
		int previousSize = conversion.getParameters().size();
		conversion.addParameter(newParam);
		assertEquals(previousSize + 1, conversion.getParameters().size());
	}

	@Test
	public void testToDouble() {
		Parameter param = new Parameter("p6name", "2.2");
		assertEquals(Conversion.toDouble(param.getValue()), 2.2, 0);
	}

	@Test
	public void testToInt() {
		Parameter param = new Parameter("p6name", "2");
		assertEquals(Conversion.toInt(param.getValue()), 2);
	}

}
