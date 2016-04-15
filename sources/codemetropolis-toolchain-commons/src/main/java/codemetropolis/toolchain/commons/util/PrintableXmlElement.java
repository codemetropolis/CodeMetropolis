package codemetropolis.toolchain.commons.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class PrintableXmlElement {
	
	protected String lightWeightContent = "";
	
	protected abstract Element toXmlElement(Document doc);
	
	protected List<PrintableXmlElement> childElements = new ArrayList<>();
	
	public Element toXmlElement() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			return toXmlElement(doc);
		} catch (ParserConfigurationException e) {
			return null;
		}
	}
	
	public void toLightWeightElement(boolean append) {
		StringBuilder contentBuilder = new StringBuilder(append ? lightWeightContent : "");
		for(PrintableXmlElement element : childElements) {
			contentBuilder.append(element.toXml());
		}
		childElements.clear();
		lightWeightContent = contentBuilder.toString();
	}
	
	protected abstract void toXml(XMLStreamWriter writer);
	
	public String toXml() {
		try {
			StringWriter stringWriter = new StringWriter();
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = outputFactory.createXMLStreamWriter(stringWriter);
			writer = XmlStreamPrettyPrinter.create(writer);
			toXml(writer);
			return stringWriter.toString();
		} catch (FactoryConfigurationError | XMLStreamException e) {
			return "";
		}
	}
	
}
