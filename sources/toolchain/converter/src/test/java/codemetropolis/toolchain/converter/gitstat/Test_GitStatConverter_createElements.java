package codemetropolis.toolchain.converter.gitstat;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class Test_GitStatConverter_createElements {
	
	CdfConverter converter = new GitStatConverter(new HashMap<String,String>());
	
	@Test
	public void testCreateElementsEmpty() throws CodeMetropolisException {
		CdfTree tree = converter.createElements("");
		assertNotEquals(null, tree);
		assertEquals("root", tree.getRoot().getName());
		assertEquals("root", tree.getRoot().getType());
	}
	
	@Test
	public void testCreateElementsValid() throws CodeMetropolisException {
		CdfTree tree = converter.createElements(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource");
		assertNotEquals(null, tree);
		assertEquals("root", tree.getRoot().getName());
		assertEquals("root", tree.getRoot().getType());
	}
	
	@Test
	public void testCreateElementsInvalid() throws CodeMetropolisException {
		CdfTree tree = converter.createElements("/%=)(=)!(=+�)(=1111213__+)('(+1213414'");
		assertNotEquals(null, tree);
		assertEquals("root", tree.getRoot().getName());
		assertEquals("root", tree.getRoot().getType());
	}
	
	@Test
	public void testCreateElementsNull() throws CodeMetropolisException {
		CdfTree tree = converter.createElements(null);
		assertNotEquals(null, tree);
		assertEquals("root", tree.getRoot().getName());
		assertEquals("root", tree.getRoot().getType());
	}
	
	
}