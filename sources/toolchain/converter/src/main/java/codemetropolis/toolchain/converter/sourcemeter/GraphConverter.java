package codemetropolis.toolchain.converter.sourcemeter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
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

public class GraphConverter extends CdfConverter {
	
	public GraphConverter(Map<String, String> params, boolean verboseMode) {
		super(params, verboseMode);
	}

	private static final String ROOT_NODE_ID = "L100";
	
	@Override
	public CdfTree createElements(String graphPath) {
		boolean verboseMode = super.getVerboseMode();
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Creating elements.\r\n" : "");
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Creating empty graph.\r\n" : "");
		Graph graph = new Graph();
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Creating empty graph done.\r\n" : "");
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Loading path into graph.\r\n" : "");
		graph.loadBinary(graphPath);
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Loading path into graph done.\r\n" : "");
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Finding root node.\r\n" : "");
		Node root = graph.findNode(ROOT_NODE_ID);
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Finding root node done.\r\n" : "");
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Creating child elements recursively.\r\n" : "");
		CdfElement rootElement = createElementsRecursively(root);
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Creating child elements recursively done.\r\n" : "");
		System.out.print(verboseMode ? "[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new java.util.Date()) + "]:Creating elements done.\r\n" : "");
		return new CdfTree(rootElement);
	}
	
	private CdfElement createElementsRecursively(Node root) {
		String name = ((AttributeString)root.findAttributeByName("Name").next()).getValue();
		String type = root.getType().getType();
		CdfElement element = new CdfElement(name, type);
		element.setSourceId(root.getUID());
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
