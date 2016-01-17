package codemetropolis.toolchain.mapping.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.BuildableTree.Iterator;
import codemetropolis.toolchain.mapping.conversions.Conversion;
import codemetropolis.toolchain.mapping.model.Linking;
import codemetropolis.toolchain.mapping.model.Mapping;
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

public class MappingController {
	
	private static final int MIN_SIZE = 9;
	
	private final LimitController limitController = new LimitController();
	private Map<Buildable, Map<String, String>> attributesByBuildables = new HashMap<>();
	private double scale;
	private boolean showNested;
	
	public MappingController() {
		this(1.0, false);
	}
	
	public MappingController(double scale, boolean showNested) {
		this.scale = scale;
		this.showNested = showNested;
	}
	
	public void createBuildablesFromGraph(String filename) {
		attributesByBuildables.clear();
		Graph graph = new Graph();
		graph.loadBinary(filename);
		Node root = graph.findNode("L100");
		List<Node> nodes = getDescendantNodes(root);
		nodes.add(0, root);
		
		for(Node node : nodes) {
			Buildable buildable = createBuildable(node);
			Map<String, String> attributes = createAttributeMap(node);
			attributesByBuildables.put(buildable, attributes);
		}
		
		//Setting the hierarchy of buildables
		for(Node node : nodes) {
			Buildable buildable = null;
			List<Buildable> children = new ArrayList<Buildable>();
			Node[] childNodes = getChildNodes(node);
			
			for(Buildable b : attributesByBuildables.keySet()) {
				if(b.getId().equals(node.getUID())) {
					buildable = b;
				} else {
					for(Node n : childNodes) {
						if(b.getId().equals(n.getUID())) {
							children.add(b);
							break;
						}
					}
				}
				if(buildable != null && children.size() == childNodes.length) break;
			}
			buildable.addChildren(children);
		}
	}
	
	public BuildableTree linkBuildablesToMetrics(Mapping mapping) {
		
		List<Linking> linkings = mapping.getLinkings();
		Map<String, String> constants = mapping.getConstants();
		
		for(Map.Entry<Buildable, Map<String, String>> entry : attributesByBuildables.entrySet()) {
			
			Buildable b = entry.getKey();
			Map<String, String> attributes = entry.getValue();
			String sourceType = (String) attributes.get("SourceType");
			
			for(Linking l : linkings) {
				
				if(l.getSourceName().equalsIgnoreCase("Constant") && b.getType().toString().equalsIgnoreCase(l.getTargetName())) {
					b.addAttribute(l.getTargetTo(), constants.get(l.getSourceFrom()));
					continue;
				}
				
				if(l.getSourceName().equalsIgnoreCase(sourceType)) {
					attributes.remove("SourceType");
					String value = attributes.get(l.getSourceFrom());
					Object convertedValue = null;
					
					try {
						for(Conversion c : l.getConversions()) {
							convertedValue = c.apply(value, limitController.getLimit(l.getSourceName(), l.getSourceFrom()));
						}
					} catch(Exception e) {
						continue;
					}
					
					if(value == null) continue;
					
					switch(l.getTargetTo()) {
						case "height":
							b.setSizeY((int)(MIN_SIZE + scale * (int)convertedValue));
							break;
						case "width":
							b.setSizeX((int)(MIN_SIZE + scale * (int)convertedValue));
							break;
						case "length":
							b.setSizeZ((int)(MIN_SIZE + scale * (int)convertedValue));
							break;
						default:
							b.addAttribute(l.getTargetTo(), (String)convertedValue);
					}	
				}
			}
		}
		
		Buildable root = findRoot(attributesByBuildables.keySet());
		BuildableTree buildableTree = new BuildableTree(root);
		placeGlobalsInGardens(buildableTree);
		prepareBuildables(buildableTree);
		return buildableTree;
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
	
	private Buildable createBuildable(Node node) {
		String id = node.getUID();
		String name = ((AttributeString)node.findAttributeByName("Name").next()).getValue();
		
		Type type = null;
		switch(node.getType().getType()) {
			case "Package":
			case "Namespace":
				type = Type.GROUND;
				break;
			case "Interface":
			case "Class":
			case "Enum":
				type = Type.GARDEN;
				break;
			case "Method":
				type = Type.FLOOR;
				break;
			case "Attribute":
				type = Type.CELLAR;
		}
		return new Buildable(id, name, type);
	}
	
	private Map<String, String> createAttributeMap(Node node) {
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("SourceType", node.getType().getType());
		
		AttributeIterator attributeIterator = node.getAttributes();
		while(attributeIterator.hasNext()) {
			Object value;
			Double doubleValue = null;
			Attribute a = attributeIterator.next();
			switch(a.getType()) {
				case atString:
					value = ((AttributeString)a).getValue();
					break;
				case atInt:
					doubleValue = (double)((AttributeInt)a).getValue();
					value = doubleValue;
					break;
				case atFloat:
					doubleValue = (double)((AttributeFloat)a).getValue(); 
					value = doubleValue;
					break;
				default:
					value = "";
			}
			
			if(doubleValue != null)
				limitController.add(node.getType().getType(), a.getName(), doubleValue);
			
			attributes.put(
					a.getName(),
					String.valueOf(value)
					);
		}
		return attributes;
	}
	
	private Buildable findRoot(Collection<Buildable> buildables) {
		for(Buildable b : buildables)
			if(b.isRoot() && b.getType() == Type.GROUND) return b;
		return null;
	}
	
	private void prepareBuildables(BuildableTree buildables) {
		Iterator it = buildables.iterator();
		while(it.hasNext()) {
			Buildable b = it.next();
			
			if(b.getSizeX() % 2 == 0) b.setSizeX(b.getSizeX() + 1);
			if(b.getSizeY() % 2 == 0) b.setSizeY(b.getSizeY() + 1);
			if(b.getSizeZ() % 2 == 0) b.setSizeZ(b.getSizeZ() + 1);
			
			if(b.getSizeX() < MIN_SIZE) b.setSizeX(MIN_SIZE);
			if(b.getSizeY() < MIN_SIZE) b.setSizeY(MIN_SIZE);
			if(b.getSizeZ() < MIN_SIZE) b.setSizeZ(MIN_SIZE);
			
			Buildable[] children = b.getChildren();
			
			if(showNested) {
				if(b.getType() == Type.FLOOR || b.getType() == Type.CELLAR) {
					if(children.length > 0) {
						b.clearChildren();
						for(Buildable c : children) {
							b.getParent().addChild(c);
						}
					}
				}
			} else {
				if(b.getType() == Type.FLOOR || b.getType() == Type.CELLAR) {
					if(children.length > 0) {
						b.clearChildren();
					}
				}
			}
		}
	}
	
	private void placeGlobalsInGardens(BuildableTree buildables) {
		Map<Buildable, Buildable> globalsByGrounds = new HashMap<Buildable, Buildable>(); 
		Iterator it = buildables.iterator();
		while(it.hasNext()) {
			Buildable b = it.next();
			if(
					(b.getType() == Type.FLOOR || b.getType() == Type.CELLAR) &&
					b.getParent().getType() == Type.GROUND) {
				if(globalsByGrounds.containsKey(b.getParent())) {
					globalsByGrounds.get(b.getParent()).addChild(b);
				} else {
					Buildable globals = new Buildable("GL" + (globalsByGrounds.size() + 1), "globals", Type.GARDEN);
					globalsByGrounds.put(b.getParent(), globals);
					b.getParent().addChild(globals);
					globals.addChild(b);
				}
			}
		}
	}
	
}
