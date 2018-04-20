package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Parameter;
import junit.framework.TestCase;

public class ConversionTest extends TestCase {
	
	Conversion result = null;
	
	public void setup() {
		result = null;
	}
	
	public void testCreateFromName() {
		result = Conversion.createFromName("to_int");
		assert(result instanceof ToIntConversion);
		
		result = Conversion.createFromName("to_double");
		assert(result instanceof ToDoubleConversion);
		
		result = Conversion.createFromName("multiply");
		assert(result instanceof MultiplyConversion);
		
		result = Conversion.createFromName("quantization");
		assert(result instanceof QuantizationConversion);
		
		result = Conversion.createFromName("switch");
		assert(result instanceof SwitchConversion);
		
		result = Conversion.createFromName("abcd");
		assertNull(result);
	}
	
	public void testToDouble() {
		double testResult;
		
		testResult = Conversion.toDouble("42");
		assertEquals(42.0, testResult);
		
		testResult = Conversion.toDouble((double)42.0);
		assertEquals(42.0, testResult);
	}
	
	public void testAddParameter() {
		result = new MultiplyConversion();
		
		assertEquals(0, result.getParameters().length);
		
		result.addParameter(new Parameter("testKey", "testValue"));
		
		assertEquals(1, result.getParameters().length);
		assertEquals("testKey", result.getParameters()[0].getName());
		assertEquals("testValue", result.getParameters()[0].getValue());
	}
	
}
