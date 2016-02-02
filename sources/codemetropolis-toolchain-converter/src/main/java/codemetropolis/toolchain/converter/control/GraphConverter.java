package codemetropolis.toolchain.converter.control;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.commons.converter.ElementCDF;
import codemetropolis.toolchain.commons.converter.ElementList;
import codemetropolis.toolchain.commons.converter.IConverter;
import codemetropolis.toolchain.commons.converter.Property;
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

public class GraphConverter implements IConverter {
	
	private Node root;
	private List<ElementCDF> elements;
	private ElementCDF rootElement;
	String fileName;
	
	public GraphConverter(String filename){
		this.fileName = filename;
	}
	
	@Override
	public void createElements(){
		Graph graph = new Graph();
		graph.loadBinary(fileName);
	    root = graph.findNode("L100");
	    elements = new ArrayList<>();
		Node root = graph.findNode("L100");
		List<Node> nodes = getDescendantNodes(root);
		nodes.add(0, root);
		
		for(Node node : nodes) {
			ElementCDF element = createElement(node);
			elements.add(element);
			
		}
		
		for(Node node : nodes) {
			ElementCDF elementCDF = null;
			List<ElementCDF> children = new ArrayList<ElementCDF>();
			Node[] childNodes = getChildNodes(node);
			
			for(ElementCDF e : elements) {	
				if(rootElement == null && e.getSourceId().equals(root.getUID())){
					rootElement = e;
				}
				if(e.getSourceId().equals(node.getUID())) {
					e.getProperties().addAll(getProperties(node));
					elementCDF = e;
				} else {
					for(Node n : childNodes) {
						if(e.getSourceId().equals(n.getUID())) {
							children.add(e);
							break;
						}
					}
				}
				if(elementCDF != null && children.size() == childNodes.length) break;
			}
			elementCDF.getChildrenElements().addAll(children);
			
		}	
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
	
	private List<Property> getProperties(Node node){
		List<Property> propertiesList = new ArrayList<>();
		
		AttributeIterator attributeIterator = node.getAttributes();
		while(attributeIterator.hasNext()) {
			Object value;
			Double doubleValue = null;
			String type;
			Attribute a = attributeIterator.next();
			switch(a.getType()) {
			case atString:
				value = ((AttributeString)a).getValue();
				type = "string";
				break;
			case atInt:
				doubleValue = (double)((AttributeInt)a).getValue();
				value = doubleValue;
				type = "int";
				break;
			case atFloat:
				doubleValue = (double)((AttributeFloat)a).getValue(); 
				value = doubleValue;
				type = "float";
				break;
			default:
				value = "";
				type = "";
		}
		    Property prop = new Property(a.getName(), String.valueOf(value), type);
		    propertiesList.add(prop);
		}
		return propertiesList;	
	}
	private List<Node> getDescendantNodes(Node node) {
		List<Node> temp = new ArrayList<Node>();
		temp.add(node);
		List<Node> descendants = new ArrayList<Node>();

		while(!temp.isEmpty()) {
			Node expanded = temp.remove(0);
			for(Node child : getChildNodes(expanded)) {
				temp.add(child);
				descendants.add(child);
			}
		}
		return descendants;
	}
	
	private ElementCDF createElement(Node node){
		ElementCDF element =  new ElementCDF();
		String id = node.getUID();
		String name = ((AttributeString)node.findAttributeByName("Name").next()).getValue();
		String type = node.getType().getType();

		element.setSourceId(id);
		element.setName(name);
		element.setType(type);
		
		return element;
		
	}

	
	@Override
	public ElementList getElementList(){
		return new ElementList(rootElement);
	}
}
