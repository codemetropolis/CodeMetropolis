package codemetropolis.toolchain.converter.gitstat;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;

public class Test_CdfTreeBuilder_createTreeFromMap {

	public Map<CdfElement, List<CdfProperty>> map;
	public List<CdfProperty> list;
	
	public void initValid() {
		list = new ArrayList<>();
		list.add(new CdfProperty("teszt1", "teszt2", CdfProperty.Type.INT));
		map = new HashMap<>();
		map.put(new CdfElement(), list);
	}
	
	public void initEmpty() {
		list = new ArrayList<>();
		map = new HashMap<>();
		map.put(new CdfElement(), list);
	}
	
	@Test
	public void createTreeFromMapValid() {
		initValid();
		CdfTree tree = CdfTreeBuilder.createTreeFromMap(map);
		assertNotNull(tree);
		assertNotNull(tree.getRoot());
		assertEquals("teszt2", tree.getRoot().getProperty("teszt1").getValue());
		assertEquals(CdfProperty.Type.INT, tree.getRoot().getProperty("teszt1").getType());
	}
	
	@Test
	public void createTreeFromMapEmpty() {
		initEmpty();
		CdfTree tree = CdfTreeBuilder.createTreeFromMap(map);
		assertNotNull(tree);
		assertNull(tree.getRoot());
	}
	
	@Test
	public void createTreeFromMapEmpty2() {
		CdfTree tree = CdfTreeBuilder.createTreeFromMap(new HashMap<CdfElement,List<CdfProperty>>());
		assertNotNull(tree);
		assertNull(tree.getRoot());
	}
	
	@Test
	public void createTreeFromMapNukk() {
		CdfTree tree = CdfTreeBuilder.createTreeFromMap(null);
		assertNotNull(tree);
	}
}