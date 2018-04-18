package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class CreateRootelemenTest {
    static Document document;
    static GitInspectorConverter converter;

    @BeforeClass
    public static void setUp() throws Exception {
        document = TestHelper.newDocument();
        converter = TestHelper.newGitInspectorConverter();
    }

    @Test
    public void testCreateRootelement() {
        CdfElement root = converter.createRootelement(document);
        CdfElement expected = new CdfElement("gfx 2018/04/16", "root");
        assertTrue(TestHelper.equals(root, expected));
    }
}
