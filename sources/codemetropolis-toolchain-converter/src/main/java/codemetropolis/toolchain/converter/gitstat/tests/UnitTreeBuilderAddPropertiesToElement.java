package codemetropolis.toolchain.converter.gitstat.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.converter.gitstat.CdfTreeBuilder;

public class UnitTreeBuilderAddPropertiesToElement {
	
	
	@Test
	public void invalidParam() {
		List<CdfProperty> list = new ArrayList<CdfProperty>();
		CdfTree tree =  new CdfTree(new CdfElement());
		CdfTreeBuilder.addPropertiesToElement(new CdfElement(), list, tree);
		assertEquals(1, tree.getElements().size());
		assertNotNull(tree.getRoot());
	}
	
	@Test
	public void correctParam() {
		List<CdfProperty> list = new ArrayList<CdfProperty>();
		list.add(new CdfProperty("name", "value", CdfProperty.Type.INT));
		CdfTree tree =  new CdfTree(null);
		CdfTreeBuilder.addPropertiesToElement(new CdfElement(), list, tree);
		assertEquals(CdfProperty.Type.INT, tree.getRoot().getProperty("name").getType());
		assertNotNull(tree.getRoot());
	}

}
