package codemetropolis.toolchain.converter.gitstat;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

/**
 * 
 * @author Radics Ott�
 *
 */
public class UnitGitsStatFileHelperLines {
	
	@Test
	public void invalidParam() {
		List<String> list = null;
		try {
			list = GitStatFileHelper.getLinesFromFile("abcd1234");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertNull(list);
		
	}
	
	@Test
	public void nullParam() {
		List<String> list = null;
		try {
			list = GitStatFileHelper.getLinesFromFile(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertNull(list);
	}
	
	@Test
	public void correctParam() {
		List<String> list = null;
		try {
			list = GitStatFileHelper.getLinesFromFile("C:/maven/readme.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertNotNull(list);
	}

}