package codemetropolis.toolchain.mapping.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;
import codemetropolis.toolchain.mapping.exceptions.MissingResourceException;
import codemetropolis.toolchain.mapping.exceptions.NotSupportedLinkingException;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Mapping {

	@XmlAttribute
	private String version;
	
	@XmlAttribute
	private String id;
	
	@XmlElementWrapper(name="resources")
	@XmlElement(name="constant")
	private List<Constant> resources = new ArrayList<>();
	
	@XmlElement(name="linking")
	private List<Linking> linkings = new ArrayList<>();

	public String getVersion() {
		return version;
	}

	public String getId() {
		return id;
	}

	public List<Constant> getResources() {
		return Collections.unmodifiableList(resources);
	}
	
	public Map<String, String> getResourceMap() {
		Map<String, String> result = new HashMap<>();
		for(Constant resource : resources) {
			result.put(resource.getId(), resource.getValue());
		}
		return result;
	}

	public List<Linking> getLinkings() {
		return Collections.unmodifiableList(linkings);
	}
	
	public String getTargetTypeOf(String sourceType) {
		for(Linking linking : linkings) {
			if(linking.getSource().equalsIgnoreCase(sourceType)) {
				return linking.getTarget().toUpperCase();
			}
		}
		return null;
	}

	public static Mapping readFromXML(String mappingFile) throws MappingReaderException, FileNotFoundException {

		File file = new File(mappingFile);
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		
		try {
			JAXBContext context = JAXBContext.newInstance(Mapping.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Mapping mapping = (Mapping) unmarshaller.unmarshal(file);
			return mapping;
		} catch (JAXBException e) {
			throw new MappingReaderException(e);
		}
		
	}
	
	public void validate() throws NotSupportedLinkingException, MissingResourceException {
		for(Linking linking : linkings) {
			linking.validate(resources);
		}
	}
	
}
