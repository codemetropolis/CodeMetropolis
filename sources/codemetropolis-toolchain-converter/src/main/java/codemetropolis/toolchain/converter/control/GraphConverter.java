package codemetropolis.toolchain.converter.control;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.CdfConverter;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import graphlib.Attribute;
import graphlib.Attribute.AttributeIterator;
import graphlib.AttributeFloat;
import graphlib.AttributeInt;
import graphlib.AttributeString;
import graphlib.Edge.EdgeIterator;
import graphlib.Edge.EdgeType;
import graphlib.Edge.eDirectionType;
import graphlib.Graph;
import graphlib.Node;

public class GraphConverter implements CdfConverter {
	
	private String fileName;
	
	public GraphConverter(String filename){
		this.fileName = filename;
	}

	@Override
	public CdfTree createElements(){
		Graph graph = new Graph();
		graph.loadBinary(fileName);
		Node root = graph.findNode("L100");
		CdfElement rootElement = createElementsRecursively(root);
		return new CdfTree(rootElement);
	}
	
	private CdfElement createElementsRecursively(Node root) {
		String name = ((AttributeString)root.findAttributeByName("Name").next()).getValue();
		String type = root.getType().getType();
		CdfElement element =  new CdfElement(name, type);
		element.addProperty("sourceid", root.getUID(), CdfProperty.Type.STRING);
		addProperties(root, element);
		for(Node child : getChildNodes(root)) {
			element.addChildElement(createElementsRecursively(child));
		}
		return element;
	}

	private Node[] getChildNodes(Node node) {
		List<Node> childList = new ArrayList<Node>();
		EdgeIterator it = node.findOutEdges(new EdgeType("LogicalTree", eDirectionType.edtDirectional));
		while(it.hasNext()) {
			Node childNode = it.next().getToNode();
			if(!node.getUID().equals(childNode.getUID())) 
				childList.add(childNode);
		}
		return childList.toArray(new Node[childList.size()]);
	}
	
	private void addProperties(Node node, CdfElement element) {
		AttributeIterator attributeIterator = node.getAttributes();
		while(attributeIterator.hasNext()) {
			Object value;
			CdfProperty.Type type;
			Attribute a = attributeIterator.next();
			switch(a.getType()) {
				case atString:
					value = ((AttributeString)a).getValue();
					type = CdfProperty.Type.STRING;
					break;
				case atInt:
					value = ((AttributeInt)a).getValue();
					type = CdfProperty.Type.INT;
					break;
				case atFloat:
					value = ((AttributeFloat)a).getValue();
					type = CdfProperty.Type.FLOAT;
					break;
				default:
					continue;
			}
		    element.addProperty(a.getName(), String.valueOf(value), type);
		}
	}

}
