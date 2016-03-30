package codemetropolis.toolchain.mapping.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Linking {

	@XmlAttribute
	private String source;
	
	@XmlAttribute
	private String target;
	
	@XmlElement(name="binding")
	private List<Binding> bindings = new ArrayList<>();

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public List<Binding> getBindings() {
		return Collections.unmodifiableList(bindings);
	}
}
