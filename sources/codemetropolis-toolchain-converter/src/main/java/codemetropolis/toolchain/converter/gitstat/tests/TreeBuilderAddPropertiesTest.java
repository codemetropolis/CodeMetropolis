package codemetropolis.toolchain.converter.gitstat.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.converter.gitstat.CdfTreeBuilder;

public class TreeBuilderAddPropertiesTest {
	
	@Test
	public void testNormal() {
		CdfProperty property = new CdfProperty("Property1", "Value1", CdfProperty.Type.INT);
		CdfElement element = new CdfElement();
		CdfTree tree = new CdfTree(null);
		CdfTreeBuilder.addPropertyToElement(element, property, tree);
		assertEquals(element, tree.getRoot());
		assertTrue(tree.getRoot().getProperty("Property1").getValue().equals("Value1"));
	}
	
	@Test 
	public void testAbnormal() {
		Exception ex = null;
		try {
			CdfTreeBuilder.addPropertyToElement(null, null, null);
		} 
		catch (Exception error) 
		{
			ex = error;
		}
		// no exception 
		assertNull(ex);
	}

}
