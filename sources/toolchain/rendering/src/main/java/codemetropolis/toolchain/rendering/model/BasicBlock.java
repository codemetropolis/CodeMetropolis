package codemetropolis.toolchain.rendering.model;

import codemetropolis.toolchain.commons.model.BlockType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BasicBlock {

	private String stringId;
	private short shortId;
	private HashSet<Enum<?>> properties;
	private List<Integer> iProperties;

	public BasicBlock(BlockType blockType){
		this.stringId = blockType.getStringId();
		this.shortId = blockType.getShortId();
		this.properties = new HashSet<>(blockType.getProperties());
	}

	public BasicBlock(BasicBlock block, HashSet<Enum<?>> properties) {
		this.stringId = block.getStringId();
		this.shortId = block.getShortId();
		this.properties = new HashSet<>(properties);
	}

	public BasicBlock(String stringId, short shortId, List<Integer> iProperties){
		this.stringId = stringId;
		this.shortId = shortId;
		this.iProperties = new ArrayList<>(iProperties);
	}

	public BasicBlock(BasicBlock original) {
		this.stringId = original.stringId;
		this.shortId = original.getShortId();
		this.properties = new HashSet<>(original.properties);
		this.iProperties = new ArrayList<>(original.iProperties);
	}

	public HashSet<Enum<?>> getProperties() {
		return properties;
	}

	public void setProperties(HashSet<Enum<?>> properties) {
			this.properties = properties;
	}

	public String getStringId() {
			return stringId;
		}

	public short getShortId() {
			return shortId;
	}

	public List<Integer> getiProperties() { return iProperties; }

	public void setiProperties(List<Integer> iProperties) {
		this.iProperties = iProperties;
	}

	public void addProperty(Enum<?> property){
		this.properties.add(property);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stringId == null) ? 0 : stringId.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		BasicBlock other = (BasicBlock) obj;
		if (stringId == null) {
			if (other.stringId != null)
				return false;
		} else if (!stringId.equals(other.stringId))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	@Override
	public String toString() {
			return "BasicBlock [id=" + stringId + ", properties=" + properties + "]";
		}
}
