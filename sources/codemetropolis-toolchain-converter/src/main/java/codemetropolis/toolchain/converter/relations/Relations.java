package codemetropolis.toolchain.converter.relations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Relations {

    // Map to store the relations between classes, first argument is the key, the ID of the class
    // second argument is the child classes of the first argument (it can be null that means they have no child)
    private Map<String , ArrayList<String>> relationsMap = new HashMap<String, ArrayList<String>>();
    
    // Map to store the attribute types (classes) of the classes
    // KEY: ID of the class, VALUE: list of attribute type (class) IDs
    private Map<String , ArrayList<String>> attributesMap = new HashMap<String, ArrayList<String>>();

	private String relationFile = null;

    public Relations(String relationFile) {
        this.relationFile = relationFile;
    }

    public void parseRelationFile() throws ParserConfigurationException, SAXException, IOException {

        File inputFile = new File(relationFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        NodeList classList = doc.getElementsByTagName("logical:Class");
        NodeList typeList = doc.getElementsByTagName("type:Type");
        NodeList typeFormerTypeList = doc.getElementsByTagName("type:TypeFormerType");

        String refIdToRelation = null;
        String refIdToRelation2 = null;
        String parent = null;
        String currentClass = null;

        for (int iClass = 0; iClass < classList.getLength(); iClass++) {
            Node nNode = classList.item(iClass);
            Element currentClassNode = (Element) nNode;
            currentClass = currentClassNode.getAttribute("id");

            for (int iClassChild = 0; iClassChild < nNode.getChildNodes().getLength(); iClassChild++) {
                Node n = nNode.getChildNodes().item(iClassChild);

                if ("Class_IsSubclass".equals(n.getNodeName())) {
                    Element classListElement = (Element) n;
                    refIdToRelation = ((Element) n).getAttribute("ref");

                    for (int iType = 0; iType < typeList.getLength(); iType++) {
                        Node nNodeType = typeList.item(iType);
                        ((Element) nNodeType).getAttribute("id");

                        // If the type was found search for "Type_HasTypeFormer" ref
                        if (refIdToRelation.equals(((Element) nNodeType).getAttribute("id"))) {
                            for (int iTypeChild = 0; iTypeChild < nNodeType.getChildNodes().getLength(); iTypeChild++) {
                                Node n2 = nNodeType.getChildNodes().item(iTypeChild);

                                if ("Type_HasTypeFormer".equals(n2.getNodeName())) {
                                    Element hasTypeFormerElement = (Element) n2;
                                    refIdToRelation2 = hasTypeFormerElement.getAttribute("ref");

                                    // // If the Type_HasTypeFormer was found search for "TypeFormerType_RefersTo" ref
                                    for (int iTypeFormerType = 0; iTypeFormerType < typeFormerTypeList.getLength(); iTypeFormerType++) {
                                        Node nNodeTypeFormerType = typeFormerTypeList.item(iTypeFormerType);
                                        ((Element) nNodeTypeFormerType).getAttribute("id"); //?

                                        // If the type was found search for "Type_HasTypeFormer" ref
                                        if (refIdToRelation2.equals(((Element) nNodeTypeFormerType).getAttribute("id"))) {
                                            for (int iTypeFormerTypeChild = 0; iTypeFormerTypeChild < nNodeTypeFormerType.getChildNodes().getLength(); iTypeFormerTypeChild++) {
                                                Node n3 = nNodeTypeFormerType.getChildNodes().item(iTypeFormerTypeChild);

                                                if ("TypeFormerType_RefersTo".equals(n3.getNodeName())) {
                                                    Element hasTypeFormerTypeRefersToElement = (Element) n3;
                                                    parent = hasTypeFormerTypeRefersToElement.getAttribute("ref");

                                                    parent = parent.replaceAll("id", "L");
                                                    currentClass = currentClass.replaceAll("id", "L");

                                                    ArrayList<String> children;
                                                    children = relationsMap.get(parent);

                                                    if (children != null) {
                                                        children.add(currentClass);
                                                    } else {
                                                        children = new ArrayList<String>();
                                                        children.add(currentClass);
                                                    }
                                                    relationsMap.put(parent, children);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // if the child is an attribute in class
                if ("logical:Attribute".equals(n.getNodeName())) {
                    // get the type of the attribute
                    NodeList attrChildren = n.getChildNodes();
                    for (int iAttrChild = 0; iAttrChild < attrChildren.getLength(); ++iAttrChild) {
                        Node attrChild = attrChildren.item(iAttrChild);
                        if ("Attribute_HasType".equals(attrChild.getNodeName())) {
                            String typeRef = attrChild.getAttributes().getNamedItem("ref").getNodeValue();

                            // search for type with obtained id
                            for (int iType = 0; iType < typeList.getLength(); ++iType) {
                                Node type = typeList.item(iType);
                                String typeId = type.getAttributes().getNamedItem("id").getNodeValue();
                                if (typeRef.equals(typeId)) {

                                    // get typeFormerType ref from type
                                    NodeList typeChildren = type.getChildNodes();
                                    for (int iTypeChildren = 0; iTypeChildren < typeChildren.getLength(); ++iTypeChildren) {
                                        Node typeChild = typeChildren.item(iTypeChildren);
                                        if ("Type_HasTypeFormer".equals(typeChild.getNodeName())) {
                                            String typeFormerTypeRef = typeChild.getAttributes().getNamedItem("ref").getNodeValue();

                                            // search for typeFormerType with obtained id
                                            for (int iTypeFormerType = 0; iTypeFormerType < typeFormerTypeList.getLength(); ++iTypeFormerType) {
                                                Node typeFormerType = typeFormerTypeList.item(iTypeFormerType);
                                                String typeFormerTypeId = typeFormerType.getAttributes().getNamedItem("id").getNodeValue();
                                                if (typeFormerTypeRef.equals(typeFormerTypeId)) {

                                                    // get class ref from typeFormerType
                                                    NodeList typeFormerTypeChildren = typeFormerType.getChildNodes();
                                                    for (int iTypeFormerTypeChild = 0; iTypeFormerTypeChild < typeFormerTypeChildren.getLength(); ++iTypeFormerTypeChild) {
                                                        Node typeFormerTypeChild = typeFormerTypeChildren.item(iTypeFormerTypeChild);
                                                        if ("TypeFormerType_RefersTo".equals(typeFormerTypeChild.getNodeName())) {
                                                            String classRef = typeFormerTypeChild.getAttributes().getNamedItem("ref").getNodeValue();

                                                            // search for classes with obtained id (may not need, but builtins are excluded this way for sure)
                                                            for (int jClass = 0; jClass < classList.getLength(); ++jClass) {
                                                                Node klass = classList.item(jClass);
                                                                String classId = klass.getAttributes().getNamedItem("id").getNodeValue();
                                                                if (classRef.equals(classId)) {

                                                                    // add attribute to attributesMap (init list if it does not exist)
                                                                    String classKey = nNode.getAttributes().getNamedItem("id").getNodeValue().replaceAll("id", "L");
                                                                    String attrClassKey = classId.replaceAll("id", "L");
                                                                    ArrayList<String> attributes = attributesMap.get(classKey);
                                                                    if (attributes == null) {
                                                                        attributes = new ArrayList<String>();
                                                                        attributesMap.put(classKey, attributes);
                                                                    }
                                                                    attributes.add(attrClassKey);
                                                                    break;
                                                                }
                                                            }
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    public Map<String, ArrayList<String>> getRelationsMap() {
        return relationsMap;
    }
    
    public Map<String, ArrayList<String>> getAttributesMap() {
		return attributesMap;
	}

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        for (String key : relationsMap.keySet()) {
            s.append("Class: " + key +  " -> " + "SubClasses: " + relationsMap.get(key) + " | ");
        }
     
        for (String key : attributesMap.keySet()) {
            s.append("Class: " + key +  " -> " + "Attributes: " + attributesMap.get(key) + " | ");
        }

        return s.toString();
    }

    public String getRelationFile() {
        return relationFile;
    }

    public void setRelationFile(String relationFile) {
        this.relationFile = relationFile;
    }
}