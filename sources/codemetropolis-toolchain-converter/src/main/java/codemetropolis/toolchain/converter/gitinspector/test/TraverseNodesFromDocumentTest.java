package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.w3c.dom.Document;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class TraverseNodesFromDocumentTest {

    @Test
    public void testTraverseNodesFromDocument() throws Exception {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        Document doc = TestHelper.newDocument();
        conv.traverseNodesFromDocument(doc);
        HashMap<String, CdfElement> elements = conv.getCdfElements();

        CdfElement authorMetrics = new CdfElement("Teszt Bela", "author");
        CdfElement floorMetrics = new CdfElement("Teszt Bela", "floor-metrics");

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

        assertTrue(TestHelper.equals(authorMetrics, elements.get("Teszt Bela")));
    }
}
