package cdfreducer;

import codemetropolis.toolchain.cdfreducer.model.CdfReducerElement;
import codemetropolis.toolchain.cdfreducer.model.CdfReducerProperty;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CdfReducerElementTest {

    @Test
    public void testToXmlElement_no_regexes() throws ParserConfigurationException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Map<String, String> regexParams = new HashMap<>();

        cdfReducerElement.addProperty("123", "456", CdfReducerProperty.Type.FLOAT);

        /* WHEN */
        Element element = cdfReducerElement.toXmlElement(doc, regexParams);

        /* THEN */
        assertNotNull(element);
        assertEquals(2, element.getChildNodes().getLength());
        assertEquals("properties", element.getLastChild().getNodeName());
        assertEquals(1, element.getLastChild().getChildNodes().getLength());
        assertEquals("123", element.getLastChild().getChildNodes().item(0).getAttributes().getNamedItem("name").getNodeValue());
        assertEquals("456", element.getLastChild().getChildNodes().item(0).getAttributes().getNamedItem("value").getNodeValue());
        System.out.println("testToXmlElement_no_regexes Test passed!");
    }


    @Test
    public void testToXmlElement_value_regexes_only_matches() throws ParserConfigurationException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();


        Map<String, String> regexParams = new HashMap<String, String>()
        {{
            put("property-value-regex","[A-Z][0-9]{3}");
        }};

        cdfReducerElement.addProperty("123", "A123", CdfReducerProperty.Type.FLOAT);

        /* WHEN */
        Element element = cdfReducerElement.toXmlElement(doc, regexParams);

        /* THEN */
        assertNotNull(element);
        assertEquals(2, element.getChildNodes().getLength());
        assertEquals("properties", element.getLastChild().getNodeName());
        assertEquals(0, element.getLastChild().getChildNodes().getLength());
        System.out.println("testToXmlElement_value_regexes_only_matches Test passed!");

    }


    @Test
    public void testToXmlElement_value_regexes_only_not_matches() throws ParserConfigurationException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();


        Map<String, String> regexParams = new HashMap<String, String>()
        {{
            put("property-value-regex","[A-Z][0-9]{3}");
        }};

        cdfReducerElement.addProperty("123", "AaA123", CdfReducerProperty.Type.FLOAT);

        /* WHEN */
        Element element = cdfReducerElement.toXmlElement(doc, regexParams);

        /* THEN */
        assertNotNull(element);
        assertEquals(2, element.getChildNodes().getLength());
        assertEquals("properties", element.getLastChild().getNodeName());
        assertEquals(1, element.getLastChild().getChildNodes().getLength());
        assertEquals("123", element.getLastChild().getChildNodes().item(0).getAttributes().getNamedItem("name").getNodeValue());
        assertEquals("AaA123", element.getLastChild().getChildNodes().item(0).getAttributes().getNamedItem("value").getNodeValue());
        System.out.println("testToXmlElement_value_regexes_only_not_matches Test passed!");

    }


    @Test
    public void testToXmlElement_name_regexes_only_matches() throws ParserConfigurationException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Map<String, String> regexParams = new HashMap<>();
        regexParams.put("property-name-regex","source_id");

        cdfReducerElement.addProperty("source_id", "456", CdfReducerProperty.Type.FLOAT);

        /* WHEN */
        Element element = cdfReducerElement.toXmlElement(doc, regexParams);

        /* THEN */
        assertNotNull(element);
        assertEquals(2, element.getChildNodes().getLength());
        assertEquals("properties", element.getLastChild().getNodeName());
        assertEquals(0, element.getLastChild().getChildNodes().getLength());
        System.out.println("testToXmlElement_name_regexes_only_matches Test passed!");
    }

    @Test
    public void testToXmlElement_name_regexes_only_not_matches() throws ParserConfigurationException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();


        Map<String, String> regexParams = new HashMap<String, String>()
        {{
            put("property-name-regex","source_id");
        }};

        cdfReducerElement.addProperty("123", "AaA123", CdfReducerProperty.Type.FLOAT);

        /* WHEN */
        Element element = cdfReducerElement.toXmlElement(doc, regexParams);

        /* THEN */
        assertNotNull(element);
        assertEquals(2, element.getChildNodes().getLength());
        assertEquals("properties", element.getLastChild().getNodeName());
        assertEquals(1, element.getLastChild().getChildNodes().getLength());
        assertEquals("123", element.getLastChild().getChildNodes().item(0).getAttributes().getNamedItem("name").getNodeValue());
        assertEquals("AaA123", element.getLastChild().getChildNodes().item(0).getAttributes().getNamedItem("value").getNodeValue());
        System.out.println("testToXmlElement_name_regexes_only_not_matches Test passed!");

    }

    @Test
    public void testToXmlElement_both_regexes_only_not_matches() throws ParserConfigurationException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();


        Map<String, String> regexParams = new HashMap<String, String>()
        {{
            put("property-name-regex","source_id");
            put("property-value-regex","[A-Z][0-9]{3}");
        }};

        cdfReducerElement.addProperty("123", "AaA123", CdfReducerProperty.Type.FLOAT);

        /* WHEN */
        Element element = cdfReducerElement.toXmlElement(doc, regexParams);

        /* THEN */
        assertNotNull(element);
        assertEquals(2, element.getChildNodes().getLength());
        assertEquals("properties", element.getLastChild().getNodeName());
        assertEquals(1, element.getLastChild().getChildNodes().getLength());
        assertEquals("123", element.getLastChild().getChildNodes().item(0).getAttributes().getNamedItem("name").getNodeValue());
        assertEquals("AaA123", element.getLastChild().getChildNodes().item(0).getAttributes().getNamedItem("value").getNodeValue());
        System.out.println("testToXmlElement_both_regexes_only_not_matches Test passed!");

    }

    @Test
    public void testToXmlElement_both_regexes_only_matches() throws ParserConfigurationException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();



        Map<String, String> regexParams = new HashMap<String, String>()
        {{
            put("property-name-regex","source_id");
            put("property-value-regex","[A-Z][0-9]{3}");
        }};

        cdfReducerElement.addProperty("source_id", "A123", CdfReducerProperty.Type.FLOAT);

        /* WHEN */
        Element element = cdfReducerElement.toXmlElement(doc, regexParams);

        /* THEN */
        assertNotNull(element);
        assertEquals(2, element.getChildNodes().getLength());
        assertEquals("properties", element.getLastChild().getNodeName());
        assertEquals(0, element.getLastChild().getChildNodes().getLength());
        System.out.println("testToXmlElement_both_regexes_only_matches Test passed!");

    }

    @Test
    public void testAddPropertyToXML() throws ParserConfigurationException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element element = doc.createElement("element");
        element.setAttribute("name", "treki");

        CdfReducerProperty cdfReducerProperty = new CdfReducerProperty("name", "0", CdfReducerProperty.Type.FLOAT);
        assertEquals(0, element.getChildNodes().getLength());
        /* WHEN */

        cdfReducerElement.addPropertyToXML(doc, element, cdfReducerProperty);

        /* THEN */

        assertEquals(1, element.getChildNodes().getLength());
        assertEquals("property", element.getChildNodes().item(0).getNodeName());
        assertEquals("treki", element.getAttribute("name"));
        System.out.println("addPropertyToXMLTeszt Passed!");

    }

    @Test
    public void testWriteToFile() throws IOException {
        /* GIVEN */
        CdfReducerElement cdfReducerElement = new CdfReducerElement("name", "type");
        Map<String, String> regexParams = new HashMap<>();

        List<Map<String, String>> paramList = new ArrayList<>();
        paramList.add(new HashMap<String, String>()
        {{
            put("property-name-regex", "source_id");
            put("property-value-regex","0");
        }});
        /* WHEN */
        cdfReducerElement.writeToFile("src/test/resources/results/1_output_teszt.xml", paramList.get(0));

        /* THEN */
        Path path = Paths.get("src/test/resources/1-xml_teszt.xml");
        String original_file = Files.readAllLines(path).get(0);

        Path path2 = Paths.get("src/test/resources/results/1_output_teszt.xml");
        String output_file = Files.readAllLines(path2).get(0);

        assertEquals(original_file, output_file);


    }
}
