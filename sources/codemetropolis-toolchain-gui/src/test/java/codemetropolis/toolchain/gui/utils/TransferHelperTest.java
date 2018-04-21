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
	public void testNullTypeChecker() throws IOException {
		int row = 0;
		int col = 0;
		Object obj = new JTable();
		JTable table = new JTable();
		boolean result = helper.typeChecker(obj, table, row, col);

		Assert.assertEquals(result, false);
	}

	/**
     * @throws IOException if it is true.
     *
     */
	@Test
	public void testNonTypeChecker() throws IOException {
		int row = 0;
		int col = 1;
		Object obj = new JTable();
		obj = "Name: string";

		Object rowData[][] = { { "width: int" }, {" "} };
		Object columnNames[] = { "Attribute", "Builtin property"};
		JTable table = new JTable(rowData, columnNames);

		boolean result = helper.typeChecker(obj, table, row, col);

		Assert.assertEquals(result, false);
	}

	/**
     * @throws IOException if it is false.
     *
     */
	@Test
	public void testPassTypeChecker() throws IOException {
		int row = 0;
		int col = 1;
		Object obj = new JTable();
		obj = "tipli: int";

		Object columnNames[] = { "Attribute", "Builtin property"};
		Object rowData[][] = { { "width: int" }, {""} };

		JTable table = new JTable(rowData, columnNames);

		boolean result = helper.typeChecker(obj, table, row, col);

		Assert.assertEquals(result, true);
	}
}
