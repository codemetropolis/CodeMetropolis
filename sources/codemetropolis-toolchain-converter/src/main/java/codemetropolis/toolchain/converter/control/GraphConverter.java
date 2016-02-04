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
	
	String fileName;
	
	public GraphConverter(String filename){
		this.fileName = filename;
	}
	
	@Override
	public CdfTree createElements(){
		Graph graph = new Graph();
		graph.loadBinary(fileName);
		List<CdfElement> elements = new ArrayList<>();
		Node root = graph.findNode("L100");
		List<Node> nodes = getDescendantNodes(root);
		nodes.add(0, root);
		
		for(Node node : nodes) {
			CdfElement element = createElement(node);
			elements.add(element);
		}
		
		CdfElement rootElement = null;
		
		for(Node node : nodes) {
			CdfElement cdfElement = null;
			List<CdfElement> children = new ArrayList<CdfElement>();
			Node[] childNodes = getChildNodes(node);
			
			for(CdfElement e : elements) {	
				if(rootElement == null && getSourceIdProp(e).equals(root.getUID())){
					rootElement = e;
				}
				if(getSourceIdProp(e).equals(node.getUID())) {
					e.getProperties().addAll(getProperties(node));
					cdfElement = e;
				} else {
					for(Node n : childNodes) {
						if(getSourceIdProp(e).equals(n.getUID())) {
							children.add(e);
							break;
						}
					}
				}
				if(cdfElement != null && children.size() == childNodes.length) break;
			}
			cdfElement.getChildrenElements().addAll(children);
		}
		
		return new CdfTree(rootElement);
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
	
	private String getSourceIdProp(CdfElement element){
		for(CdfProperty prop : element.getProperties()){
			if("sourceid".equals(prop.getName())){
				return prop.getValue();
			}
		}
		return null;
	}
	
	private List<CdfProperty> getProperties(Node node){
		List<CdfProperty> propertiesList = new ArrayList<>();
		
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
		    CdfProperty prop = new CdfProperty(a.getName(), String.valueOf(value), type);
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
	
	private CdfElement createElement(Node node){
		CdfElement element =  new CdfElement();
		String name = ((AttributeString)node.findAttributeByName("Name").next()).getValue();
		String type = node.getType().getType();
		element.setName(name);
		element.setType(type);
		element.getProperties().add(new CdfProperty("sourceid", node.getUID(), "string"));
		return element;
		
	}

}
