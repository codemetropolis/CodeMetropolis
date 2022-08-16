package codemetropolis.toolchain.cdfreducer.model;
import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Settings;

import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

@XmlRootElement(name="element")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlSeeAlso(CdfReducerProperty.class)
public class CdfReducerElement {

    @XmlTransient
    private static final String SOURCE_ID_KEY = "source_id";
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String type;
    @XmlElementWrapper(name="children")
    @XmlElement(name="element", type=CdfReducerElement.class, nillable = true)
    private List<CdfReducerElement> childElements;
    @XmlElementWrapper(name="properties")
    @XmlElement(name="property", type=CdfReducerProperty.class, nillable = true)
    private List<CdfReducerProperty> properties;

    public CdfReducerElement() {
        //	this(null, null);
    }

    public CdfReducerElement(String name, String type) {
        this.name = name;
        this.type = type;
        properties = new ArrayList<>();
        childElements = new ArrayList<>();
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

    public void setType(String type) {
        this.type = type;
    }

    public List<CdfReducerProperty> getProperties() {
        return new ArrayList<>(properties);
    }

    public String getPropertyValue(String name){
        CdfReducerProperty property = getProperty(name);
        if(property == null) return null;
        return property.getValue();
    }

    public CdfReducerProperty getProperty(String name){
        for(CdfReducerProperty property : properties) {
            if(name.equals(property.getName()))
            {
                return property;
            }
        }
        return null;
    }

    public void addProperty(String name, String value, CdfReducerProperty.Type type) {
        properties.add(new CdfReducerProperty(name, value, type));
    }

    public String getSourceId() {
        return getPropertyValue(SOURCE_ID_KEY);
    }

    public void setSourceId(String id) {
        addProperty(SOURCE_ID_KEY, id, CdfReducerProperty.Type.STRING);
    }

    public List<CdfReducerElement> getChildElements() {
        return new ArrayList<>(childElements);
    }

    public void addChildElement(CdfReducerElement child) {
        childElements.add(child);
    }

    public void removeChildElement(CdfReducerElement child) {
        childElements.remove(child);
    }

    public int getNumberOfChildren() {
        return childElements.size();
    }

    public List<CdfReducerElement> getDescendants() {

        List<CdfReducerElement> result = new ArrayList<CdfReducerElement>();
        Stack<CdfReducerElement> temp = new Stack<CdfReducerElement>();
        temp.push(this);
        while(!temp.isEmpty()) {
            CdfReducerElement current = temp.pop();
            if(current.getNumberOfChildren() > 0) {
                for(CdfReducerElement child : current.getChildElements()) {
                    result.add(child);
                    temp.push(child);
                }
            }
        }
        return result;
    }

    /**
     * Creates the elements.
     * If the element is a <property><property/> and it's name contains the property-name-regex or
     * it' value contains the prperty-value-regex, then the element is going to be skipped.
     * @param doc  document to create
     * @param regexParams params for the regexes
     * @return
     * @author h759770 Fülep Martin Patrik
     */
    public Element toXmlElement(Document doc, Map<String, String> regexParams) {

        Element element = doc.createElement("element");
        element.setAttribute("name", name);
        element.setAttribute("type", type.toString().toLowerCase());
        Element children = doc.createElement("children");
        element.appendChild(children);
        for(CdfReducerElement c : this.childElements) {
            children.appendChild(c.toXmlElement(doc, regexParams));
        }

        boolean hasNameRegex = regexParams.containsKey("property-name-regex");
        boolean hasValueRegex = regexParams.containsKey("property-value-regex");
        String nameRegex = hasNameRegex ? regexParams.get("property-name-regex") : null;
        String valueRegex = hasValueRegex ? regexParams.get("property-value-regex") : null;

        Element propertiesElement = doc.createElement("properties");
        element.appendChild(propertiesElement);

        if(!hasNameRegex && !hasValueRegex) {
            for (CdfReducerProperty prop : this.properties) {
                addPropertyToXML(doc, propertiesElement, prop);
            }
        }else{
            if(hasNameRegex && hasValueRegex){
                for (CdfReducerProperty prop : this.properties) {
                    if(!prop.getName().matches(nameRegex) && !prop.getValue().matches(valueRegex))
                        addPropertyToXML(doc, propertiesElement, prop);
                }
            }else if(hasNameRegex){
                for (CdfReducerProperty prop : this.properties) {
                    if(!prop.getName().matches(nameRegex))
                        addPropertyToXML(doc, propertiesElement, prop);
                }
            }else{
                for (CdfReducerProperty prop : this.properties) {
                    if(!prop.getValue().matches(valueRegex))
                        addPropertyToXML(doc, propertiesElement, prop);
                }
            }
        }

        return element;
    }

    /**
     * Only created this function to avoid code duplication in toXmlElement
     * It adds the property to the output file.
     * @param doc output file
     * @param propertiesElement the properties wrapper around this property
     * @param prop property itself
     * @return
     * @author h759770 Fülep Martin Patrik
     */
    public void addPropertyToXML(Document doc, Element propertiesElement, CdfReducerProperty prop){

        Element attr = doc.createElement("property");
        attr.setAttribute("type", prop.getType().name().toLowerCase());
        attr.setAttribute("name", prop.getName());
        attr.setAttribute("value", prop.getValue());
        propertiesElement.appendChild(attr);

    }

    /**
     * Creates a new .xml with the regexes applied on the already unmarshalled .xml input file
     * @param filename output file
     * @param regexParams params for the regexes
     * @throws Exception function can throw an Exception
     * @author h759770 Fülep Martin Patrik
     */
    public void writeToFile(String filename, Map<String, String> regexParams){
        FileLogger.load(Settings.get("cdfreducer_log_file"));
        try {
            FileUtils.createContainingDirs(filename);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            doc.appendChild(this.toXmlElement(doc, regexParams));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            FileLogger.logError("Couldn't build new document!\n", e);
            System.out.println("Couldn't build new document!");;
        } catch (TransformerException e) {
            FileLogger.logError("Couldn't transform output data into an ixml document!\n", e);
            System.out.println("Couldn't transform output data into an ixml document!");;
        }
    }
}
