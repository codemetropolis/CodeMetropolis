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
		logVerbose("Creating CodeMetropolis CdfTree.");

		Graph graph = createEmptyGraph();
		loadGraph(graph, graphPath);
		Node root = findRootNode(graph);
		CdfElement rootElement = createChildElements(root);

		logVerbose("Creating CodeMetropolis CdfTree done.");
		return new CdfTree(rootElement);
	}

	private Graph createEmptyGraph() {
		return new Graph();
	}

	private void loadGraph(Graph graph, String graphPath) {
		logVerbose("Loading in SourceMeter graph.");
		graph.loadBinary(graphPath);
		logVerbose("Loading in SourceMeter graph done.");
	}

	private Node findRootNode(Graph graph) {
		logVerbose("Finding root node of SourceMeter graph.");
		Node root = graph.findNode(ROOT_NODE_ID);
		logVerbose("Finding root node of SourceMeter graph done.");
		return root;
	}

	private CdfElement createChildElements(Node root) {
		logVerbose("Creating child elements recursively.");
		CdfElement rootElement = createElementsRecursively(root);
		logVerbose("Creating child elements recursively done.");
		return rootElement;
	}

	private void logVerbose(String message) {
		if (super.getVerboseMode()) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date());
			System.out.println("[" + timestamp + "]:" + message);
		}
	}
	
	private CdfElement createElementsRecursively(Node root) {
		String name = ((AttributeString)root.findAttributeByName("Name").next()).getValue();
		String type = root.getType().getType();
		logVerbose("Creating CodeMetropolis CdfElement: " + name + " of type: " + type);
		CdfElement element = new CdfElement(name, type);
		logVerbose("Creating CodeMetropolis CdfElement: " + name + " of type: " + type + " done.");
		logVerbose("Setting source id to: " + root.getUID());
		element.setSourceId(root.getUID());
        logVerbose("Setting source id to: " + root.getUID() + " done.");
		addProperties(root, element);
		for(Node child : getChildNodes(root)) {
			element.addChildElement(createElementsRecursively(child));
            logVerbose("Adding child element: " + child.getUID() + " to parent: " + root.getUID());
		}
		return element;
	}

	private Node[] getChildNodes(Node node) {
		List<Node> childList = new ArrayList<Node>();
		EdgeIterator it = node.findOutEdges(new EdgeType("LogicalTree", eDirectionType.edtDirectional));
        logVerbose("Getting child nodes for: " + node.getUID());
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
