package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class ResetMetricsTest {

    @Test
    public void testResetMetrics() {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        conv.resetMetrics("kiscica");
        CdfElement testAuthor = new CdfElement("kiscica", "author");
        CdfElement testMetric = new CdfElement("kiscica", "floor-metrics");
        assertTrue(TestHelper.equals(testAuthor, conv.getAuthorMetrics()));
        assertTrue(TestHelper.equals(testMetric, conv.getFloorMetrics()));
        conv.resetMetrics("kiskutya");
        assertFalse(TestHelper.equals(testAuthor, conv.getAuthorMetrics()));
        assertFalse(TestHelper.equals(testMetric, conv.getFloorMetrics()));
    }
}
