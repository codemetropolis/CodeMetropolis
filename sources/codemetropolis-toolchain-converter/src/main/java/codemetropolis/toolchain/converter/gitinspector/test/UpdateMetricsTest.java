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

public class UpdateMetricsTest {

    @Test
    public void testUpdateMetrics() throws Exception {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        Document doc = TestHelper.newDocument();
        Element element = doc.createElement("test-element");

        Element strAttr = doc.createElement("string-attribute");
        strAttr.appendChild(doc.createTextNode("value"));
        element.appendChild(strAttr);

        Element intAttr = doc.createElement("int-attribute");
        intAttr.appendChild(doc.createTextNode("16"));
        element.appendChild(intAttr);

        Element floatAttr = doc.createElement("float-attribute");
        floatAttr.appendChild(doc.createTextNode("0.78"));
        element.appendChild(floatAttr);

        conv.updateMetrics(element, "string-attribute", "string", CdfProperty.Type.STRING, false);
        conv.updateMetrics(element, "int-attribute", "int1", CdfProperty.Type.INT, false);
        conv.updateMetrics(element, "int-attribute", "int2", CdfProperty.Type.INT, true);
        conv.updateMetrics(element, "float-attribute", "float", CdfProperty.Type.FLOAT, false);

        CdfElement authorMetrics = new CdfElement("", "author");
        CdfElement floorMetrics = new CdfElement("", "floor-metrics");

        authorMetrics.addProperty("string", "value", CdfProperty.Type.STRING);
        floorMetrics.addProperty("string", "value", CdfProperty.Type.STRING);

        authorMetrics.addProperty("int1", "16", CdfProperty.Type.INT);
        floorMetrics.addProperty("int1", "16", CdfProperty.Type.INT);

        authorMetrics.addProperty("int2", "4", CdfProperty.Type.INT);
        floorMetrics.addProperty("int2", "4", CdfProperty.Type.INT);

        authorMetrics.addProperty("float", "0.78", CdfProperty.Type.FLOAT);
        floorMetrics.addProperty("float", "0.78", CdfProperty.Type.FLOAT);

        assertTrue(TestHelper.equals(authorMetrics, conv.getAuthorMetrics()));
        assertTrue(TestHelper.equals(floorMetrics, conv.getFloorMetrics()));
    }
}
