package codemetropolis.toolchain.gui.utils;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import codemetropolis.toolchain.gui.beans.QuantizationInformation;

/**
 * Test class for testing the {@link TransferHelper}.
 *
 * @author Tamas Keri {@literal <KETWAAT.SZE>}
 */
public class QuantizationInformationTest {
	QuantizationInformation quant = new QuantizationInformation();

	@Test
	public void testPassSetIndex() throws IOException {
		quant.setIndex(4);
		assertTrue(quant.getIndex() == 4);
	}

	@Test
	public void testPassSetMetric() throws IOException {
		quant.setMetric("NUMPAR");
		assertTrue(quant.getMetric() == "NUMPAR");
	}

	@Test
	public void testPassSetBuildableAttribute() throws IOException {
		quant.setBuildableAttribute("viktor");
		assertTrue(quant.getBuildableAttribute() == "viktor");
	}

	@Test
	public void testFailSetIndex() throws IOException {
		quant.setIndex(4);
		assertTrue(quant.getIndex() == 1);
	}

	@Test
	public void testFailSetMetric() throws IOException {
		quant.setMetric("NUMPAR");
		assertTrue(quant.getMetric() == "");
	}

	@Test
	public void testFailSetBuildableAttribute() throws IOException {
		quant.setBuildableAttribute("viktor");
		assertTrue(quant.getBuildableAttribute() != "");
	}
}
