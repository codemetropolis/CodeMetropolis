package codemetropolis.toolchain.converter.sonarqube;

public class SonarMetric {

	private String name;
	private String value;
	private MetricType type;
	
	public SonarMetric(String name, String value, MetricType type) {
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
	public MetricType getType() {
		return type;
	}
	public void setType(MetricType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SonarMetric other = (SonarMetric) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public enum MetricType {
	    INT,
	    FLOAT,
	    PERCENT,
	    BOOL,
	    STRING,
	    MILLISEC,
	    DATA,
	    LEVEL,
	    DISTRIB,
	    RATING,
	    WORK_DUR
	}
	
}
