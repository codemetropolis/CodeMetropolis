package codemetropolis.toolchain.cdfreducer.model;


import javax.xml.bind.annotation.*;

@XmlRootElement(name="property")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType()
public class CdfReducerProperty {

    @XmlAttribute(name="name")
    private String name;
    @XmlAttribute(name="type")
    private Type type;
    @XmlAttribute(name="value")
    private String value;

    public CdfReducerProperty(String name, String value, Type type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public CdfReducerProperty() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @XmlEnum
    public enum Type {
        @XmlEnumValue(value="string")
        STRING,
        @XmlEnumValue(value="int")
        INT,
        @XmlEnumValue(value="float")
        FLOAT
    }

}
