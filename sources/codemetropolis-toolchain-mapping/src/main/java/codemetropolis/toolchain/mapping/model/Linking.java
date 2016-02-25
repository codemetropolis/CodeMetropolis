package codemetropolis.toolchain.mapping.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.mapping.conversions.Conversion;;

public class Linking {
	
	private static final Map<Type, String[]> SUPPORTED_TARGETS = new HashMap<>();

	static {
		SUPPORTED_TARGETS.put(Type.FLOOR, new String[]{"width", "height", "length", "character", "external_character", "torches"});
		SUPPORTED_TARGETS.put(Type.CELLAR, new String[]{"width", "height", "length", "character", "external_character", "torches"});
		SUPPORTED_TARGETS.put(Type.GARDEN, new String[]{"tree-ratio", "mushroom-ratio", "flower-ratio"});
		SUPPORTED_TARGETS.put(Type.GROUND, new String[]{});
	}

	private String sourceName;
	private String sourceFrom;
	private String targetName;
	private String targetTo;
	private List<Conversion> conversions;
	
	public Linking() {
		this("", "", "", "");
	}
	
	public Linking(String sourceName, String sourceFrom, String targetName, String targetTo) {
		this.sourceName = sourceName;
		this.sourceFrom = sourceFrom;
		this.targetName = targetName;
		this.targetTo = targetTo;
		conversions = new ArrayList<Conversion>();
	}
	
	public Boolean isSupported() {
		if("".equals(targetTo)) return true;
		for(Entry<Type, String[]> e : SUPPORTED_TARGETS.entrySet()) {
			if(e.getKey().toString().equalsIgnoreCase(targetName)) {
				for(String s : e.getValue()) {
					if(s.equals(targetTo))
						return true;
				}
			}
		}
		return false;
	}
	
	public Boolean clearConverions() {
		conversions.clear();
		return true;
	}
	
	public Boolean addConversion(Conversion c) {
		conversions.add(c);
		return true;
	}
	
	public Boolean addConversions(Conversion... conversions) {
		for(Conversion c : conversions) {
			this.conversions.add(c);
		}
		return true;
	}
	
	public List<Conversion> getConversions() {
		return new ArrayList<>(conversions);
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetTo() {
		return targetTo;
	}

	public void setTargetTo(String targetTo) {
		this.targetTo = targetTo;
	}

	@Override
	public String toString() {
		return "Linking [sourceName=" + sourceName + ", sourceFrom="
				+ sourceFrom + ", targetName=" + targetName + ", targetTo="
				+ targetTo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sourceFrom == null) ? 0 : sourceFrom.hashCode());
		result = prime * result
				+ ((sourceName == null) ? 0 : sourceName.hashCode());
		result = prime * result
				+ ((targetName == null) ? 0 : targetName.hashCode());
		result = prime * result
				+ ((targetTo == null) ? 0 : targetTo.hashCode());
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
		Linking other = (Linking) obj;
		if (sourceFrom == null) {
			if (other.sourceFrom != null)
				return false;
		} else if (!sourceFrom.equals(other.sourceFrom))
			return false;
		if (sourceName == null) {
			if (other.sourceName != null)
				return false;
		} else if (!sourceName.equals(other.sourceName))
			return false;
		if (targetName == null) {
			if (other.targetName != null)
				return false;
		} else if (!targetName.equals(other.targetName))
			return false;
		if (targetTo == null) {
			if (other.targetTo != null)
				return false;
		} else if (!targetTo.equals(other.targetTo))
			return false;
		return true;
	}
	
}

