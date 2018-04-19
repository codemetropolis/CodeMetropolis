package codemetropolis.toolchain.converter.sourcemeter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.relations.Relations;
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
	
	public GraphConverter(Map<String, String> params) {
		super(params);
	}

	private static final String ROOT_NODE_ID = "L100";
	private boolean isRelationsNeeded = false;
	private Relations relations = null;

	@Override
	public CdfTree createElements(String graphPath) {
		if (getParameter("relationFile") != null) {
			isRelationsNeeded = true;
			relations = new Relations(getParameter("relationFile"));
			relations.parseRelationFile();
			System.out.println(relations.toString());
		}

		Graph graph = new Graph();
		graph.loadBinary(graphPath);
		Node root = graph.findNode(ROOT_NODE_ID);
		CdfElement rootElement = createElementsRecursively(root);
		return new CdfTree(rootElement);
	}
	
	private CdfElement createElementsRecursively(Node root) {
		String name = ((AttributeString)root.findAttributeByName("Name").next()).getValue();
		String type = root.getType().getType();
		CdfElement element = new CdfElement(name, type);
		if ("Class".equals(type)) {
			// if the element is a class, check for subclasses from relationFile
			System.out.println("I find a \"class\" id: " + root.getUID());

			if (relations.getRelationsMap().get(root.getUID()) != null) {
				String classId = relations.getRelationsMap().get(root.getUID()).toString();
				element.addProperty("ChildClasses", classId, CdfProperty.Type.STRING);
				System.out.println(element.getProperty("ChildClasses"));
			} else  {
				element.addProperty("ChildClasses", "", CdfProperty.Type.STRING);
			}
			
			// check for attributes
			if (relations.getAttributesMap().get(root.getUID()) != null) {
				System.out.println("hello " + relations.getAttributesMap());
				String classId = relations.getAttributesMap().get(root.getUID()).toString();
				element.addProperty("AttributeClasses", classId, CdfProperty.Type.STRING);
				System.out.println(element.getProperty("AttributeClasses"));

			} else  {
				element.addProperty("AttributeClasses", "", CdfProperty.Type.STRING);
			}

		} else {
			System.out.println("Not a \"class\" id: " + root.getUID());
		}
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
