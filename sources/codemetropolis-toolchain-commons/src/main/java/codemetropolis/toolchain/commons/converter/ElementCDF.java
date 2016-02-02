package codemetropolis.toolchain.commons.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElementCDF {

	private String name;
	private String type;
	private String sourceId;
	private List<Property> properties;
	private List<ElementCDF> childrenElements;
	
	public ElementCDF(){
		properties = new ArrayList<>();
		childrenElements = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String sourceType) {
		this.type = sourceType;
	}
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public List<ElementCDF> getChildrenElements() {
		return childrenElements;
	}
	public void setChildrenElements(List<ElementCDF> childrenElements) {
		this.childrenElements = childrenElements;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public int getNumberOfChildren() {
		return childrenElements.size();
	}
	
	public List<ElementCDF> getDescendants() {

		List<ElementCDF> result = new ArrayList<ElementCDF>();
		Stack<ElementCDF> temp = new Stack<ElementCDF>();
		temp.push(this);
		while(!temp.isEmpty()) {
			ElementCDF current = temp.pop();
			if(current.getNumberOfChildren() > 0) {
				for(ElementCDF child : current.getChildrenElements()) {
					result.add(child);
					temp.push(child);
				}
			}
		}
		return result;
	}
	
	public Element toXmlElement(Document doc) {
		Element element = doc.createElement("element");
		element.setAttribute("name", name);
		element.setAttribute("type", type.toString().toLowerCase());		
		element.setAttribute("id", sourceId);
		Element children = doc.createElement("children");
		element.appendChild(children);
		for(ElementCDF c : this.childrenElements) {
			children.appendChild(c.toXmlElement(doc));
		}
		
		Element propertiesElement = doc.createElement("properties");
		element.appendChild(propertiesElement);		
		for(Property prop : this.properties) {
			Element attr = doc.createElement("property");
			attr.setAttribute("type", prop.getType());
			attr.setAttribute("name", prop.getName());
			attr.setAttribute("value", prop.getValue());
			propertiesElement.appendChild(attr);
		}
		
		return element;
	}
}
