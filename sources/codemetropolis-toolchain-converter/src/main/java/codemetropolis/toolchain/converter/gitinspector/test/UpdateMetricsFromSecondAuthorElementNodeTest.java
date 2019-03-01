package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

/**
*
* @author Zakor Gyula {@literal <ZAGPAAG.SZE>}
*
*/

public class UpdateMetricsFromSecondAuthorElementNodeTest {

    @Test
    public void testUpdateMetricsFromSecondAuthorElementNode() throws Exception {
        CdfElement authorMetrics = new CdfElement("Gipsz Jakab", "author");
        CdfElement floorMetrics = new CdfElement("Gipsz Jakab", "floor-metrics");
        authorMetrics.addChildElement(floorMetrics);

        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        Document doc = TestHelper.newDocument();
        Element element = doc.createElement("test-elemnt");

        Element nameAttr = doc.createElement("name");
        nameAttr.appendChild(doc.createTextNode("Gipsz Jakab"));
        element.appendChild(nameAttr);

        Element commitsAttr = doc.createElement("rows");
        commitsAttr.appendChild(doc.createTextNode("1600"));
        element.appendChild(commitsAttr);

        Element insertionsAttr = doc.createElement("stability");
        insertionsAttr.appendChild(doc.createTextNode("89.4"));
        element.appendChild(insertionsAttr);

        Element deletionsAttr = doc.createElement("age");
        deletionsAttr.appendChild(doc.createTextNode("0.1"));
        element.appendChild(deletionsAttr);

        Element pocAttr = doc.createElement("percentage-in-comments");
        pocAttr.appendChild(doc.createTextNode("0.51"));
        element.appendChild(pocAttr);

        conv.updateMetricsFromSecondAuthorElementNode(element, authorMetrics);

        authorMetrics = new CdfElement("Gipsz Jakab", "author");
        floorMetrics = new CdfElement("Gipsz Jakab", "floor-metrics");

        authorMetrics.addProperty("rows", "40", CdfProperty.Type.INT);
        floorMetrics.addProperty("rows", "40", CdfProperty.Type.INT);

        authorMetrics.addProperty("stability", "89.4", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("stability", "89.4", CdfProperty.Type.FLOAT);

        authorMetrics.addProperty("age", "0.1", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("age", "0.1", CdfProperty.Type.FLOAT);

        authorMetrics.addProperty("PIC", "0.51", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("PIC", "0.51", CdfProperty.Type.FLOAT);

        authorMetrics.addChildElement(floorMetrics);

        assertTrue(TestHelper.equals(authorMetrics, conv.getAuthorMetrics()));
    }
}
