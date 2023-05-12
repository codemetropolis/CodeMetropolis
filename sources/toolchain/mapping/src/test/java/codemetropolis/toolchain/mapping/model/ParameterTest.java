package codemetropolis.toolchain.mapping.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Parameter}.
 * 
 * @author Gecs Bernat {@literal <GEBWABT.SZE>}
 */

public class ParameterTest {

	String parameter1 = "param1";
	String parameter2 = "param2";

	@Test
	public final void testGetName() {
		Parameter parameter = new Parameter(parameter1,parameter2);
		Assert.assertEquals(parameter.getName(), parameter1);
	}

	@Test
	public final void testSetName() {
		Parameter parameter = new Parameter(parameter1,parameter2);
		Assert.assertEquals(parameter.getName(), parameter1);
	}

	@Test
	public final void testGetValue() {
		Parameter parameter = new Parameter(parameter1,parameter2);
		Assert.assertEquals(parameter.getValue(), parameter2);
	}

	@Test
	public final void testSetValue() {
		Parameter parameter = new Parameter(parameter1,parameter2);
		Assert.assertEquals(parameter.getValue(), parameter2);
	}

}