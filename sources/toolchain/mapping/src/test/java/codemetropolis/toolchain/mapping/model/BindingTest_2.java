package codemetropolis.toolchain.mapping.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for {@link Binding}.
 * 
 * @author Gecs Bernat {@literal <GEBWABT.SZE>}
 */

public class BindingTest_2 {

	String from = "valami.xml";
	String to = "csv";

	@Test
	public final void testGetVariableId() {
		Binding binding = new Binding(from,to);
		assertNull(binding.getVariableId());
	}

}