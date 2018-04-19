package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class CreateElementsTest {

    private static String XML_SRC_PATH = "src\\main\\java\\codemetropolis\\toolchain\\converter\\gitinspector\\test\\GitInspectorOutput.xml";
    private static String INVALID_FORMAT_PATH = "src\\main\\java\\codemetropolis\\toolchain\\converter\\gitinspector\\test\\";

    @Test
    public void invalidPath() {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        try {
            conv.createElements("");
            fail("This test should fail because the resource path is invalid.");
        } catch (CodeMetropolisException e) {
        } catch (Exception e) {
            fail("the wrong type of exception returned.");
        }
    }

    @Test
    public void invalidResource() {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        try {
            conv.createElements(INVALID_FORMAT_PATH);
            fail("This test should fail because the target resource is a library.");
        } catch (CodeMetropolisException e) {
        } catch (Exception e) {
            fail("the wrong type of exception returned.");
        }
    }

    @Test
    public void validPath() {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        try {
            conv.createElements(XML_SRC_PATH);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateElementsTest() {
        CdfTree tree = new CdfTree();
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        try {
            tree = conv.createElements(XML_SRC_PATH);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        CdfElement authorMetrics = new CdfElement("Teszt Bela", "author");
        CdfElement floorMetrics = new CdfElement("Teszt Bela", "floor-metrics");
        CdfElement root = new CdfElement("gfx 2018/04/16", "root");

        authorMetrics.addProperty("email", "becike@beciakiraly.eu", CdfProperty.Type.STRING);
        floorMetrics.addProperty("email", "becike@beciakiraly.eu", CdfProperty.Type.STRING);

        authorMetrics.addProperty("commits", "56", CdfProperty.Type.INT);
        floorMetrics.addProperty("commits", "56", CdfProperty.Type.INT);

        authorMetrics.addProperty("insertions", "67", CdfProperty.Type.INT);
        floorMetrics.addProperty("insertions", "67", CdfProperty.Type.INT);

        authorMetrics.addProperty("deletions", "54", CdfProperty.Type.INT);
        floorMetrics.addProperty("deletions", "54", CdfProperty.Type.INT);

        authorMetrics.addProperty("POC", "57.55", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("POC", "57.55", CdfProperty.Type.FLOAT);

        authorMetrics.addProperty("rows", "61", CdfProperty.Type.INT);
        floorMetrics.addProperty("rows", "61", CdfProperty.Type.INT);

        authorMetrics.addProperty("stability", "83.1", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("stability", "83.1", CdfProperty.Type.FLOAT);

        authorMetrics.addProperty("age", "0.8", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("age", "0.8", CdfProperty.Type.FLOAT);

        authorMetrics.addProperty("PIC", "4.32", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("PIC", "4.32", CdfProperty.Type.FLOAT);

        authorMetrics.addChildElement(floorMetrics);
        root.addChildElement(authorMetrics);

        assertTrue(TestHelper.equals(root, tree.getRoot()));
    }
}
