package codemetropolis.toolchain.commons.cmxml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Validator {
	
	private static final InputStream XSD_STREAM = Validator.class.getClassLoader().getResourceAsStream("cmxml_scheme.xsd");
	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
	
	public static boolean validate(String xmlPath) throws IOException {
		File xmlFile = new File(xmlPath);
		try {
			DocumentBuilder documentBuilder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
			Document doc = documentBuilder.parse(xmlFile);
		    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		    Source schemaFile = new StreamSource(XSD_STREAM);
		    Schema schema = factory.newSchema(schemaFile);;
	    	javax.xml.validation.Validator validator = schema.newValidator();
	        validator.validate(new DOMSource(doc));
	    } catch (SAXException | ParserConfigurationException e) {
	    	return false;
	    }
	    return true;
	}

}
