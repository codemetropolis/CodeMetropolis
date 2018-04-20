package codemetropolis.toolchain.gui.utils;

import java.io.IOException;

import javax.swing.JTable;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for testing the {@link TransferHelper}.
 *
 * @author Tamas Keri {@literal <KETWAAT.SZE>}
 */
public class TransferHelperTest {
	TransferHelper helper = new TransferHelper();

	/**
      * @throws IOException if it is true.
      */
	@Test
	public void testNonTypeChecker() throws IOException {
		int row = 0;
		int col = 0;
		Object obj = new JTable();
		JTable table = new JTable();
		boolean result = helper.typeChecker(obj, table, row, col);

		Assert.assertEquals(result, false);
	}
}
