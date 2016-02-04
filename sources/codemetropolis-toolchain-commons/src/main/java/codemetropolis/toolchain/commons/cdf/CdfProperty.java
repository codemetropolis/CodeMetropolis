package codemetropolis.toolchain.commons.cdf;

public class CdfProperty {

	private String name;
	private String value;
	private Type type;
	
	public CdfProperty(String name, String value, Type type) {
		this.name = name;
		this.value = value;
		this.type = type;
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
	
	public enum Type {
		STRING,
		INT,
		FLOAT
	}
}
