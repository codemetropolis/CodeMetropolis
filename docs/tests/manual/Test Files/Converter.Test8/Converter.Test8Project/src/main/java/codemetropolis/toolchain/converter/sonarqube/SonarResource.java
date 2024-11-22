package codemetropolis.toolchain.converter.sonarqube;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SonarResource {

	private int id;
	private String name;
	private String key;
	private Scope scope;
	private List<SonarMetric> metrics = new ArrayList<>();
	private List<Integer> childIdList = new ArrayList<>();
	
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public List<SonarMetric> getMetrics() {
		return Collections.unmodifiableList(metrics);
	}
	
	public void addMetrics(Collection<SonarMetric> metrics) {
		this.metrics.addAll(metrics);
	}
	
	public void addMetric(SonarMetric... metric) {
		for(SonarMetric m : metric) {
			metrics.add(m);
		}
	}
	
	public List<Integer> getChildIdList() {
		return Collections.unmodifiableList(childIdList);
	}
	
	public void addChildren(Collection<Integer> idCollection) {
		childIdList.addAll(idCollection);
	}
	
	public void addChild(int... id) {
		for(int c : id) {
			childIdList.add(c);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childIdList == null) ? 0 : childIdList.hashCode());
		result = prime * result + id;
		result = prime * result + ((metrics == null) ? 0 : metrics.hashCode());
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
		if (childIdList == null) {
			if (other.childIdList != null)
				return false;
		} else if (!childIdList.equals(other.childIdList))
			return false;
		if (id != other.id)
			return false;
		if (metrics == null) {
			if (other.metrics != null)
				return false;
		} else if (!metrics.equals(other.metrics))
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
