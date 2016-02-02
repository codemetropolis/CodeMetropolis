package codemetropolis.toolchain.mapping.control;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.BuildableTree.Iterator;
import codemetropolis.toolchain.mapping.conversions.Conversion;
import codemetropolis.toolchain.mapping.model.Linking;
import codemetropolis.toolchain.mapping.model.Mapping;



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

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();;
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			File xmlFile = new File(filename);
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("element");
			for(int i=0; i<nodes.getLength(); i++){
				Node nNode = nodes.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element element = (Element) nNode;
					Buildable actualB = createBuildable(element);
					
					Map<String,String> attributes = new HashMap<>();
					Node properties = element.getChildNodes().item(3);
					if(properties.getNodeType() == Node.ELEMENT_NODE){
						Element propertiesElements = (Element) properties;
						attributes.putAll(createAttributeMap(propertiesElements));
						attributes.put("SourceType", element.getAttribute("type"));				
					}
					attributesByBuildables.put(actualB, attributes);
					
					
					if("children".equals(element.getParentNode().getNodeName()) && "element".equals(element.getParentNode().getParentNode().getNodeName())){
						Element parentElement = (Element) element.getParentNode().getParentNode();
						if(getBuildable(parentElement) != null){
							Buildable b = getBuildable(parentElement);
							b.addChild(actualB);						
						}
					}
				}
			}
		
		} catch (ParserConfigurationException | SAXException | IOException e){
			e.printStackTrace();
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
							convertedValue = c.apply(value, limitController.getLimit(l.getSourceName().toLowerCase(), l.getSourceFrom()));
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
							b.addAttribute(l.getTargetTo(), String.valueOf(convertedValue));
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
	
	private Buildable getBuildable(Element element){
		String id = element.getAttribute("id");
		for(Buildable b : attributesByBuildables.keySet()){
			if(b.getId().equals(id)){
				return b;
			}
		}		
		return null;		
	}
	
	private Buildable createBuildable(Element element) {	
		
		String id = element.getAttribute("id");
		
		//TODO: id generálás
		String name = element.getAttribute("name");
		
		Type type = null;
		switch(element.getAttribute("type")) {
			case "package":
			case "namespace":
				type = Type.GROUND;
				break;
			case "interface":
			case "class":
			case "enum":
				type = Type.GARDEN;
				break;
			case "method":
				type = Type.FLOOR;
				break;
			case "attribute":
				type = Type.CELLAR;
		}
		return new Buildable(id, name, type);
	}
	
	private Map<String, String> createAttributeMap(Element element) {

		Map<String, String> attributes = new HashMap<String, String>();
		
		NodeList propNodes = element.getChildNodes();
		for(int i = 0; i< propNodes.getLength(); i++){
			Node propNode = propNodes.item(i);
			if(propNode.getNodeType() == Node.ELEMENT_NODE){
				Element propElement = (Element) propNode;
				Object value;
				Double doubleValue = null;
				switch(propElement.getAttribute("type")) {
					case "string":
						value = propElement.getAttribute("value");
						break;
					case "int":
						doubleValue = Double.valueOf(propElement.getAttribute("value"));
						value = doubleValue;
						break;
					case "float":
						doubleValue = Double.valueOf(propElement.getAttribute("value"));
						value = doubleValue;
						break;
					default:
						value = "";
				}
				
				if(doubleValue != null){
					Element parentElement = (Element)element.getParentNode();
					limitController.add(parentElement.getAttribute("type").toLowerCase(), propElement.getAttribute("name"), doubleValue);				
				}
				String name = propElement.getAttribute("name");
				attributes.put(
						name,
						String.valueOf(value)
						);
			}							
		
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
