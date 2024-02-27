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
		logVerbose("Creating empty graph in which SourceMeter graph will load into.");
		Graph graph = new Graph();
		logVerbose("Empty graph creation done.");
		return graph;
	}

	private void loadGraph(Graph graph, String graphPath) {
		logVerbose("Loading in SourceMeter graph.");
		try {
			graph.loadBinary(graphPath);
			logVerbose("Loading in SourceMeter graph done.");
		} catch (Exception e) {
			logVerbose("Error loading SourceMeter graph: " + e.getMessage());
		}
	}

	private Node findRootNode(Graph graph) {
		logVerbose("Finding root node of SourceMeter graph.");
		try {
			Node root = graph.findNode(ROOT_NODE_ID);
			logVerbose("Finding root node of SourceMeter graph done.");
			return root;
		} catch (Exception e) {
			logVerbose("Error loading SourceMeter graph: " + e.getMessage());
			return null;
		}
	}

	private CdfElement createChildElements(Node root) {
		logVerbose("Creating child elements recursively.");
		try {
			CdfElement rootElement = createElementsRecursively(root);
			logVerbose("Creating child elements recursively done.");
			return rootElement;
		} catch (Exception e) {
			logVerbose("Error creating child elements recursively: " + e.getMessage());
			return null;
		}
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
		}
		return element;
	}

	private Node[] getChildNodes(Node node) {
		logVerbose("Getting child nodes of: " + node.getUID());
		List<Node> childList = new ArrayList<Node>();
		EdgeIterator it = node.findOutEdges(new EdgeType("LogicalTree", eDirectionType.edtDirectional));
		while(it.hasNext()) {
			Node childNode = it.next().getToNode();
			if(!node.getUID().equals(childNode.getUID())) 
				childList.add(childNode);
		}
		logVerbose("Getting child nodes of: " + node.getUID() + " done.");
		return childList.toArray(new Node[childList.size()]);
	}
	
	private void addProperties(Node node, CdfElement element) {
		logVerbose("Adding properties to CodeMetropolis CdfElement: " + element.getName());
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
							"to CodeMetropolis CdfElement: " + element.getName() + " done.");
					break;
				case atInt:
					value = ((AttributeInt)a).getValue();
					type = CdfProperty.Type.INT;
					logVerbose("Adding property: " + a.getName() + " with value: " + value + " and type: " + type + " " +
							"to CodeMetropolis CdfElement: " + element.getName() + " done.");
					break;
				case atFloat:
					value = ((AttributeFloat)a).getValue();
					type = CdfProperty.Type.FLOAT;
					logVerbose("Adding property: " + a.getName() + " with value: " + value + " and type: " + type + " " +
							"to CodeMetropolis CdfElement: " + element.getName() + " done.");
					break;
				default:
					continue;
			}
		    element.addProperty(a.getName(), String.valueOf(value), type);
			logVerbose("Adding properties to CodeMetropolis CdfElement: " + element.getName() + " done.");
		}
	}
}
