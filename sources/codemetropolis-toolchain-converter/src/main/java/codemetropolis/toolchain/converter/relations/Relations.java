package codemetropolis.toolchain.converter.relations;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Relations {

	// Map to store the relations between classes, first argument is the key, the ID of the class
	// second argument is the child classes of the first argument (it can be null that means they have no child)
	private Map<String , ArrayList<String>> relationsMap = new HashMap<String, ArrayList<String>>();

	// Map to store the attribute types (classes) of the classes
	// KEY: ID of the class, VALUE: list of attribute type (class) IDs
	private Map<String , ArrayList<String>> attributesMap = new HashMap<String, ArrayList<String>>();

	private String relationFile = null;

	private NodeList classList;
	private NodeList typeList;
	private NodeList typeFormerTypeList;

	public Relations(String relationFile) {
		this.relationFile = relationFile;
	}

	public void parseRelationFile() throws ParserConfigurationException, SAXException, IOException {

		File inputFile = new File(relationFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		classList = doc.getElementsByTagName("logical:Class");
		typeList = doc.getElementsByTagName("type:Type");
		typeFormerTypeList = doc.getElementsByTagName("type:TypeFormerType");

		for (int iClass = 0; iClass < classList.getLength(); iClass++) {

			Node nClass = classList.item(iClass);
			String idClass = nClass.getAttributes().getNamedItem("id").getNodeValue();

			NodeList classChildren = nClass.getChildNodes();
			for (int iClassChild = 0; iClassChild < classChildren.getLength(); iClassChild++) {
				Node nClassChild = classChildren.item(iClassChild);

				// if the child is a subclass
				if ("Class_IsSubclass".equals(nClassChild.getNodeName())) {
					getSubclassRelation(nClassChild, idClass);

					// if the child is an attribute in class
					if ("logical:Attribute".equals(nClassChild.getNodeName())) {

						// get children
						NodeList attrChildren = nClassChild.getChildNodes();
						getAttributeRelations(nClass, attrChildren);

					}
				}
			}
		}
	}

	private void getSubclassRelation(Node nClassChild, String idClass) {
		String refType = nClassChild.getAttributes().getNamedItem("ref").getNodeValue();
		if (refType == null) {
			return;
		}
		String refTypeFormerType = getTypeFormerTypeRefFromTypes(refType);
		if (refTypeFormerType == null ) {
			return;
		}
		String refParent = getClassRefFromTypeFormerTypes(refTypeFormerType);
		if (refParent == null) {
			return;
		}
		
		refParent = refParent.replaceAll("id", "L");
		idClass = idClass.replaceAll("id", "L");

		ArrayList<String> children;
		children = relationsMap.get(refParent);

		if (children != null) {
			children.add(idClass);
		} else {
			children = new ArrayList<String>();
			children.add(idClass);
		}
		relationsMap.put(refParent, children);
	}
	

	private void getAttributeRelations(Node nClass, NodeList attrChildren) {

		for (int iAttrChild = 0; iAttrChild < attrChildren.getLength(); ++iAttrChild) {

			Node nAttrChild = attrChildren.item(iAttrChild);
			if ("Attribute_HasType".equals(nAttrChild.getNodeName())) {

				String refType = nAttrChild.getAttributes().getNamedItem("ref").getNodeValue();
				if (refType == null) {
					return;
				}
				String refTypeFormerType = getTypeFormerTypeRefFromTypes(refType);
				if (refTypeFormerType == null ) {
					return;
				}
				String refAttrClass = getClassRefFromTypeFormerTypes(refTypeFormerType);
				if (refAttrClass == null) {
					return;
				}

				// search for classes with obtained id (may not need, but builtins are excluded this way for sure)
				for (int iAttrClass = 0; iAttrClass < classList.getLength(); ++iAttrClass) {
					Node nAttrClass = classList.item(iAttrClass);
					String idAttrClass = nAttrClass.getAttributes().getNamedItem("id").getNodeValue();
					if (refAttrClass.equals(idAttrClass)) {

						// add attribute to attributesMap (init list if it does not exist)
						String keyClass = nClass.getAttributes().getNamedItem("id").getNodeValue().replaceAll("id", "L");
						String keyAttrClass = idAttrClass.replaceAll("id", "L");
						ArrayList<String> attributes = attributesMap.get(keyClass);
						if (attributes == null) {
							attributes = new ArrayList<String>();
							attributesMap.put(keyClass, attributes);
						}
						attributes.add(keyAttrClass);
						break;
					}
				}
			}
		}
	}

	private String getTypeFormerTypeRefFromTypes(String ref) {

		// search for type with obtained id
		for (int iType = 0; iType < typeList.getLength(); ++iType) {
			Node nType = typeList.item(iType);
			String idType = nType.getAttributes().getNamedItem("id").getNodeValue();
			if (ref.equals(idType)) {

				// get typeFormerType ref from type
				NodeList typeChildren = nType.getChildNodes();
				for (int iTypeChildren = 0; iTypeChildren < typeChildren.getLength(); ++iTypeChildren) {
					Node typeChild = typeChildren.item(iTypeChildren);
					if ("Type_HasTypeFormer".equals(typeChild.getNodeName())) {
						String refTypeFormerType = typeChild.getAttributes().getNamedItem("ref").getNodeValue();
						return refTypeFormerType;
					}
				}
			}
		}
		// should not happen
		return null;

	}

	private String getClassRefFromTypeFormerTypes(String ref) {

		// search for typeFormerType with obtained id
		for (int iTypeFormerType = 0; iTypeFormerType < typeFormerTypeList.getLength(); ++iTypeFormerType) {
			Node typeFormerType = typeFormerTypeList.item(iTypeFormerType);
			String idTypeFormerType = typeFormerType.getAttributes().getNamedItem("id").getNodeValue();
			if (ref.equals(idTypeFormerType)) {

				// get class ref from typeFormerType
				NodeList typeFormerTypeChildren = typeFormerType.getChildNodes();
				for (int iTypeFormerTypeChild = 0; iTypeFormerTypeChild < typeFormerTypeChildren.getLength(); ++iTypeFormerTypeChild) {
					Node typeFormerTypeChild = typeFormerTypeChildren.item(iTypeFormerTypeChild);
					if ("TypeFormerType_RefersTo".equals(typeFormerTypeChild.getNodeName())) {
						String refClass = typeFormerTypeChild.getAttributes().getNamedItem("ref").getNodeValue();
						return refClass;
					}
				}
			}
		}
		// should not happen
		return null;
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
