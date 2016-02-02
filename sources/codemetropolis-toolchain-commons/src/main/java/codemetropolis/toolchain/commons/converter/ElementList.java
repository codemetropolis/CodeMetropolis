package codemetropolis.toolchain.commons.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import codemetropolis.toolchain.commons.cmxml.exceptions.CmxmlWriterException;

public class ElementList {
	
	public class Iterator {

		private List<ElementCDF> temp = new ArrayList<ElementCDF>();
		private int index = 0;
		
		Iterator() {
			temp.add(root);
		}
		
		public int getIndex() {
			return index;
		}
		
		public boolean hasNext() {
			return !temp.isEmpty();
		}

		public ElementCDF next() {
			ElementCDF next = temp.remove(0);
			temp.addAll(0, next.getChildrenElements());
			++index;
			return next;
		}
		
	}
	
	private ElementCDF root;
	
	public ElementList(ElementCDF root) {
		this.root = root;
	}
	
	public ElementList() {
	}
	
	public ElementCDF getRoot() {
		return root;
	}
	
	public void setRoot(ElementCDF root) {
		this.root = root;
	}

	public Iterator iterator() {
		return new Iterator();
	}
	
	public List<ElementCDF> getBuildables() {
		List<ElementCDF> buildables = new ArrayList<>();
		buildables.add(root);
		buildables.addAll(root.getDescendants());
		return buildables;
	}

	public void writeToFile(String filename) throws CmxmlWriterException{
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			doc.appendChild(root.toXmlElement(doc));			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);
		} catch ( Exception e) {
			throw new CmxmlWriterException(e);
		}
		
	}
}
