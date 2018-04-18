package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class GitInspectorConverterTest {

	@Test
	public void test() {
		GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
		CdfElement authorMetrics = new CdfElement("", "author");
		CdfElement floorMetrics = new CdfElement("", "floor-metrics");
		assertTrue(TestHelper.equals(authorMetrics, conv.getAuthorMetrics()));
		assertTrue(TestHelper.equals(floorMetrics, conv.getFloorMetrics()));
	}
}
