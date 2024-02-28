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
		logVerbose("Creating CodeMetropolis CdfTree.",false);

		Graph graph = createEmptyGraph();
		loadGraph(graph, graphPath);
		Node root = findRootNode(graph);
		CdfElement rootElement = createChildElements(root);

		logVerbose("Creating CodeMetropolis CdfTree done.", false);
		return new CdfTree(rootElement);
	}

	private Graph createEmptyGraph() {
		logVerbose("Creating empty graph in which SourceMeter graph will load into.", false);
		Graph graph = new Graph();
		logVerbose("Empty graph creation done.", false);
		return graph;
	}

	private void loadGraph(Graph graph, String graphPath) {
		logVerbose("Loading in SourceMeter graph.", false);
		try {
			graph.loadBinary(graphPath);
			logVerbose("Loading in SourceMeter graph done.", false);
		} catch (Exception e) {
			logVerbose("Error loading SourceMeter graph: " + e.getMessage(), true);
		}
	}

	private Node findRootNode(Graph graph) {
		logVerbose("Finding root node of SourceMeter graph.", false);
		try {
			Node root = graph.findNode(ROOT_NODE_ID);
			logVerbose("Finding root node of SourceMeter graph done.",false);
			return root;
		} catch (NullPointerException e) {
			logVerbose("Error loading SourceMeter graph: " + e.getMessage(), true);
			return null;
		}
	}

	private CdfElement createChildElements(Node root) {
		logVerbose("Creating child elements recursively.", false);
		try {
			CdfElement rootElement = createElementsRecursively(root);
			logVerbose("Creating child elements recursively done.", false);
			return rootElement;
		} catch (NullPointerException e) {
			logVerbose("Error creating child elements recursively: " + e.getMessage(), true);
			return null;
		}
	}

	private void logVerbose(String message, boolean isError) {
		if (super.getVerboseMode()) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date());
			if (isError) {
				System.err.println("[" + timestamp + "]:" + message);
			} else {
				System.out.println("[" + timestamp + "]:" + message);
			}
		}
	}
	
	private CdfElement createElementsRecursively(Node root) {
		String name = ((AttributeString)root.findAttributeByName("Name").next()).getValue();
		String type = root.getType().getType();
		logVerbose("Creating CodeMetropolis CdfElement: " + name + " of type: " + type, false);
		CdfElement element = new CdfElement(name, type);
		logVerbose("Creating CodeMetropolis CdfElement: " + name + " of type: " + type + " done.", false);
		logVerbose("Setting source id to: " + root.getUID(), false);
		element.setSourceId(root.getUID());
        logVerbose("Setting source id to: " + root.getUID() + " done.", false);
		addProperties(root, element);
		for(Node child : getChildNodes(root)) {
			element.addChildElement(createElementsRecursively(child));
		}
		return element;
	}

	private Node[] getChildNodes(Node node) {
		logVerbose("Getting child nodes of: " + node.getUID(), false);
		List<Node> childList = new ArrayList<Node>();
		EdgeIterator it = node.findOutEdges(new EdgeType("LogicalTree", eDirectionType.edtDirectional));
		while(it.hasNext()) {
			Node childNode = it.next().getToNode();
			if(!node.getUID().equals(childNode.getUID())) 
				childList.add(childNode);
		}
		logVerbose("Getting child nodes of: " + node.getUID() + " done.", false);
		return childList.toArray(new Node[childList.size()]);
	}
	
	private void addProperties(Node node, CdfElement element) {
		logVerbose("Adding properties to CodeMetropolis CdfElement: " + element.getName(), false);
		AttributeIterator attributeIterator = node.getAttributes();
		while(attributeIterator.hasNext()) {
			Object value;
			CdfProperty.Type type;
			Attribute a = attributeIterator.next();
			switch(a.getType()) {
				case atString:
					value = ((AttributeString)a).getValue();
					type = CdfProperty.Type.STRING;
					logVerbose("Adding property: " + a.getName() + " with value: " + value + " and type: " + type + " " +
							"to CodeMetropolis CdfElement: " + element.getName() + " done.", false);
					break;
				case atInt:
					value = ((AttributeInt)a).getValue();
					type = CdfProperty.Type.INT;
					logVerbose("Adding property: " + a.getName() + " with value: " + value + " and type: " + type + " " +
							"to CodeMetropolis CdfElement: " + element.getName() + " done.", false);
					break;
				case atFloat:
					value = ((AttributeFloat)a).getValue();
					type = CdfProperty.Type.FLOAT;
					logVerbose("Adding property: " + a.getName() + " with value: " + value + " and type: " + type + " " +
							"to CodeMetropolis CdfElement: " + element.getName() + " done.", false);
					break;
				default:
					continue;
			}
		    element.addProperty(a.getName(), String.valueOf(value), type);
			logVerbose("Adding properties to CodeMetropolis CdfElement: " + element.getName() + " done.", false);
		}
	}
}
