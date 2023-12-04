package codemetropolis.toolchain.converter.gitstat;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.Test;

public class UnitGitStatFileHelperSearchFiles {

	
	@Test
	public void invalidParam() {
		List<String> list = null;
		list = GitStatFileHelper.searchForFile("abcd1234", null);
		assertEquals(0, list.size());
		assertNotNull(list);
	}
	
	@Test
	public void nullParam() {
		List<String> list = null;
		list = GitStatFileHelper.searchForFile(null, null);
		assertEquals(0, list.size());
		assertNotNull(list);
	}
	
	@Test
	public void correctParam() {
		List<String> list = null;
		list = GitStatFileHelper.searchForFile("C:/maven/", "txt");
		assertNotNull(list);
		assertEquals("c:\\maven\\readme.txt".toUpperCase(), list.get(0).toUpperCase());
	}

	
}