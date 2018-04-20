package codemetropolis.toolchain.converter;

import java.util.HashMap;

import codemetropolis.toolchain.converter.control.ConverterType;
import junit.framework.TestCase;

public class ConverterExecutorArgsTest extends TestCase {
	ConverterExecutorArgs result;
	
	public void setup () {
		result = null;
	}
	
	public void testConverterExecutorArgsConstructorNoParams() {
		result = new ConverterExecutorArgs(ConverterType.SOURCEMETER, "testSource", "testOutput");
		assertEquals(result.getOutputFile(), "testOutput");
		assertEquals(result.getSource(), "testSource");
		assertEquals(result.getType(), ConverterType.SOURCEMETER);
	}
	
	public void testConverterExecutorArgsConstructor() {
		result = new ConverterExecutorArgs(ConverterType.SONARQUBE, "testSource", "testOutput", new HashMap<String, String>());
		assertEquals(result.getOutputFile(), "testOutput");
		assertEquals(result.getSource(), "testSource");
		assertEquals(result.getType(), ConverterType.SONARQUBE);
		assertNotNull(result.getParams());
		assertTrue(result.getParams().isEmpty());
	}
}
