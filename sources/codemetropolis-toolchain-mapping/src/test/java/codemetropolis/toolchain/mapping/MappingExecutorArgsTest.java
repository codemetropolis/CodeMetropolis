package codemetropolis.toolchain.mapping;

import junit.framework.TestCase;

public class MappingExecutorArgsTest extends TestCase {

	MappingExecutorArgs result;
	
	public void setup() {
		result = null;
	}
	
	public void testMappingExecutorArgs() {
		result = new MappingExecutorArgs("testCDF", "testOutput", "testMapping", 1.0, true);
		assertEquals(result.getCdfFile(), "testCDF");
		assertEquals(result.getOutputFile(), "testOutput");
		assertEquals(result.getMappingFile(), "testMapping");
		assertEquals(result.getScale(), 1.0);
		assertEquals(result.isHierarchyValidationEnabled(), true);
	}
	
}
