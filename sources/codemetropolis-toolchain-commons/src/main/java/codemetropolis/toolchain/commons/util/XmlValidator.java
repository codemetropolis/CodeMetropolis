package codemetropolis.toolchain.commons.util;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import codemetropolis.toolchain.commons.exceptions.InvalidSchemeException;
import codemetropolis.toolchain.commons.exceptions.SchemeNotSetException;

public class XmlValidator {
	
	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
	private Validator validator;
	
	public void setScheme(String schemePath) throws InvalidSchemeException {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			StreamSource schemaSource = new StreamSource(classLoader.getResourceAsStream(schemePath));
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(schemaSource);
			validator = schema.newValidator();
		} catch (SAXException e) {
			throw new InvalidSchemeException(e);
		}
	}
	
	public boolean validate(String xmlPath) throws IOException, SchemeNotSetException {
		if(validator == null) {
			throw new SchemeNotSetException();
		}
		try {
			File xmlFile = new File(xmlPath);
			DocumentBuilder documentBuilder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
			Document doc = documentBuilder.parse(xmlFile);
			validator.validate(new DOMSource(doc));
		} catch (ParserConfigurationException | SAXException e1) {
			return false;
		}
	    return true;
	}

}
