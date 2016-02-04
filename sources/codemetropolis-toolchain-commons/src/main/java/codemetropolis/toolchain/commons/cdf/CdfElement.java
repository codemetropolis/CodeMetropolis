package codemetropolis.toolchain.commons.cdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CdfElement {

	private String name;
	private String type;
	private List<CdfProperty> properties;
	private List<CdfElement> childrenElements;
	
	public CdfElement(){
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
	public List<CdfProperty> getProperties() {
		return properties;
	}
	public void setProperties(List<CdfProperty> properties) {
		this.properties = properties;
	}
	public List<CdfElement> getChildrenElements() {
		return childrenElements;
	}
	public void setChildrenElements(List<CdfElement> childrenElements) {
		this.childrenElements = childrenElements;
	}

	public int getNumberOfChildren() {
		return childrenElements.size();
	}
	
	public List<CdfElement> getDescendants() {

		List<CdfElement> result = new ArrayList<CdfElement>();
		Stack<CdfElement> temp = new Stack<CdfElement>();
		temp.push(this);
		while(!temp.isEmpty()) {
			CdfElement current = temp.pop();
			if(current.getNumberOfChildren() > 0) {
				for(CdfElement child : current.getChildrenElements()) {
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
		Element children = doc.createElement("children");
		element.appendChild(children);
		for(CdfElement c : this.childrenElements) {
			children.appendChild(c.toXmlElement(doc));
		}
		
		Element propertiesElement = doc.createElement("properties");
		element.appendChild(propertiesElement);		
		for(CdfProperty prop : this.properties) {
			Element attr = doc.createElement("property");
			attr.setAttribute("type", prop.getType());
			attr.setAttribute("name", prop.getName());
			attr.setAttribute("value", prop.getValue());
			propertiesElement.appendChild(attr);
		}
		
		return element;
	}
}
