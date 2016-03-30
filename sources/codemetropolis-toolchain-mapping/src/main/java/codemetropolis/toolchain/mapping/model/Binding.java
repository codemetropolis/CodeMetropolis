package codemetropolis.toolchain.mapping.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import codemetropolis.toolchain.mapping.conversions.Conversion;

@XmlAccessorType(XmlAccessType.FIELD)
public class Binding {
	
	@XmlAttribute
	private String from;
	
	@XmlAttribute
	private String to;
	
	@XmlElementWrapper(name="conversions")
	@XmlElement(name="conversion")
	private List<Conversion> conversions = new ArrayList<>();

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
	
	public String getVariableId() {
		String pattern = "^\\$\\{.*\\}$";
		if(from.matches(pattern)) {
			return from.substring(2, from.length() - 1);
		}
		return null;
	}

	public List<Conversion> getConversions() {
		return Collections.unmodifiableList(conversions);
	}
	
}
