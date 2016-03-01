package codemetropolis.toolchain.commons.sonarqube;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SonarResource {

	private int id;
	private String name;
	private Scope scope;
	private Map<String, String> metricNamesandValues = new HashMap<>();
	private List<Integer> childrenResources = new ArrayList<>();
	
	public SonarResource(){
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Map<String, String> getMetricNamesandValues() {
		return metricNamesandValues;
	}

	public void setMetricNamesandValues(Map<String, String> metricNamesandValues) {
		this.metricNamesandValues = metricNamesandValues;
	}
	
	public List<Integer> getChildrenResources() {
		return childrenResources;
	}

	public void setChildrenResources(List<Integer> childrenResources) {
		this.childrenResources = childrenResources;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childrenResources == null) ? 0 : childrenResources.hashCode());
		result = prime * result + id;
		result = prime * result + ((metricNamesandValues == null) ? 0 : metricNamesandValues.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
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
		SonarResource other = (SonarResource) obj;
		if (childrenResources == null) {
			if (other.childrenResources != null)
				return false;
		} else if (!childrenResources.equals(other.childrenResources))
			return false;
		if (id != other.id)
			return false;
		if (metricNamesandValues == null) {
			if (other.metricNamesandValues != null)
				return false;
		} else if (!metricNamesandValues.equals(other.metricNamesandValues))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (scope != other.scope)
			return false;
		return true;
	}




	public enum Scope{
		PRJ,
		DIR,
		FIL
	}
}
