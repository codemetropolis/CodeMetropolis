package codemetropolis.toolchain.converter.gitstat.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.converter.gitstat.CdfTreeBuilder;

public class TreeBuilderCreateTreeTest {
	
	@Test
	public void normalTest() {
		CdfTree tree = null;
		CdfElement element = new CdfElement();
		List<CdfProperty> properties = new ArrayList<CdfProperty>();
		properties.add(new CdfProperty("Property1", "Value1", CdfProperty.Type.INT));
		tree = CdfTreeBuilder.createTree(element, properties);
		assertNotNull(tree);
		assertTrue(tree.getElements().size() > 0);
		assertEquals("root", tree.getRoot().getName());
		assertEquals("root", tree.getRoot().getType());	
	}

	@Test
	public void abnormalTest() {
		CdfTree tree = null;
		CdfElement element = null;
		List<CdfProperty> properties = new ArrayList<CdfProperty>();
		tree = CdfTreeBuilder.createTree(element, properties);
		assertNotNull(tree);
		assertTrue(tree.getElements().size() > 0);
		assertEquals("root", tree.getRoot().getName());
		assertEquals("root", tree.getRoot().getType());	
	}
}

