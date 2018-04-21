package codemetropolis.toolchain.gui;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for the {@link CodeMetropolisGUI} class.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 *
 */
public class CodeMetropolisGUITest {	

	@Test
	public void testCheckInputCdfFileExists() {
		URL urlToDictionary  = this.getClass().getResource("/" + "output_test.xml");
		String filePath = urlToDictionary.getPath();
		boolean result = CodeMetropolisGUI.checkInputCdfFile(filePath);
		
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void testCheckInputCdfFileNotExists() {
		String path = "";
		boolean result = CodeMetropolisGUI.checkInputCdfFile(path);
		
		Assert.assertEquals(result, false);
	}

}
