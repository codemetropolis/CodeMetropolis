package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class GetMetricsFromFirstAuthorElementNodeTest {

    @Test
    public void testGetMetricsFromFirstAuthorElementNode() throws Exception {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        Document doc = TestHelper.newDocument();
        Element element = doc.createElement("test-element");

        Element nameAttr = doc.createElement("name");
        nameAttr.appendChild(doc.createTextNode("Gipsz Jakab"));
        element.appendChild(nameAttr);

        Element emailAttr = doc.createElement("email");
        emailAttr.appendChild(doc.createTextNode("xyz@valami.hu"));
        element.appendChild(emailAttr);

        Element commitsAttr = doc.createElement("commits");
        commitsAttr.appendChild(doc.createTextNode("16"));
        element.appendChild(commitsAttr);

        Element insertionsAttr = doc.createElement("insertions");
        insertionsAttr.appendChild(doc.createTextNode("100"));
        element.appendChild(insertionsAttr);

        Element deletionsAttr = doc.createElement("deletions");
        deletionsAttr.appendChild(doc.createTextNode("121"));
        element.appendChild(deletionsAttr);

        Element pocAttr = doc.createElement("percentage-of-changes");
        pocAttr.appendChild(doc.createTextNode("0.51"));
        element.appendChild(pocAttr);

        conv.getMetricsFromFirstAuthorElementNode(element);

        CdfElement authorMetrics = new CdfElement("Gipsz Jakab", "author");
        CdfElement floorMetrics = new CdfElement("Gipsz Jakab", "floor-metrics");

        authorMetrics.addProperty("email", "xyz@valami.hu", CdfProperty.Type.STRING);
        floorMetrics.addProperty("email", "xyz@valami.hu", CdfProperty.Type.STRING);

        authorMetrics.addProperty("commits", "16", CdfProperty.Type.INT);
        floorMetrics.addProperty("commits", "16", CdfProperty.Type.INT);

        authorMetrics.addProperty("insertions", "10", CdfProperty.Type.INT);
        floorMetrics.addProperty("insertions", "10", CdfProperty.Type.INT);

        authorMetrics.addProperty("deletions", "11", CdfProperty.Type.INT);
        floorMetrics.addProperty("deletions", "11", CdfProperty.Type.INT);

        authorMetrics.addProperty("POC", "0.51", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("POC", "0.51", CdfProperty.Type.FLOAT);

        authorMetrics.addChildElement(floorMetrics);

        assertTrue(TestHelper.equals(authorMetrics, conv.getAuthorMetrics()));
    }
}
