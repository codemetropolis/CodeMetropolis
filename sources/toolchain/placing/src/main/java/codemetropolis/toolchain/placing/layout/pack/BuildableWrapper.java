package codemetropolis.toolchain.placing.layout.pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cmxml.Buildable;

public class BuildableWrapper implements Comparable<BuildableWrapper> {

	Object buildable;

	public BuildableWrapper(Buildable buildable) {
		this.buildable = buildable;
	}
	
	public BuildableWrapper(House buildable) {
		this.buildable = buildable;
	}
	
	public Object getInnerBuildable() {
		return buildable;
	}

	public int getPositionX() {
		if(buildable instanceof Buildable) return ((Buildable)buildable).getPositionX();
		if(buildable instanceof House) return ((House)buildable).getPositionX();
		return 0;
	}

	public int getPositionZ() {
		if(buildable instanceof Buildable) return ((Buildable)buildable).getPositionZ();
		if(buildable instanceof House) return ((House)buildable).getPositionZ();
		return 0;
	}

	public int getSizeX() {
		if(buildable instanceof Buildable) return ((Buildable)buildable).getSizeX();
		if(buildable instanceof House) return ((House)buildable).getSizeX();
		return 0;
	}

	public int getSizeZ() {
		if(buildable instanceof Buildable) return ((Buildable)buildable).getSizeZ();
		if(buildable instanceof House) return ((House)buildable).getSizeZ();
		return 0;
	}
	
	public void setPositionX(int x) {
		if(buildable instanceof Buildable) ((Buildable)buildable).setPositionXR(x);
		if(buildable instanceof House) ((House)buildable).setPositionXR(x);
	}
	
	public void setPositionZ(int z) {
		if(buildable instanceof Buildable) ((Buildable)buildable).setPositionZR(z);
		if(buildable instanceof House) ((House)buildable).setPositionZR(z);
	}
	
	public Buildable getParent() {
		if(buildable instanceof Buildable) return ((Buildable)buildable).getParent();
		if(buildable instanceof House) return ((House)buildable).getParent();
		return null;
	}
	
	public List<BuildableWrapper> getChildren(Map<Buildable, List<House>> houses) {
		List<BuildableWrapper> result = new ArrayList<BuildableWrapper>();
		if(buildable instanceof House) return result;
		for(Buildable c : ((Buildable)buildable).getChildren()) {
			if(c.getType() == Buildable.Type.FLOOR || c.getType() == Buildable.Type.CELLAR) continue;
			result.add(new BuildableWrapper(c));
			}
		if(houses.get((Buildable)buildable) != null) {
			for(House h : houses.get((Buildable)buildable)) {
				result.add(new BuildableWrapper(h));
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((buildable == null) ? 0 : buildable.hashCode());
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
		BuildableWrapper other = (BuildableWrapper) obj;
		if (buildable == null) {
			if (other.buildable != null)
				return false;
		} else if (!buildable.equals(other.buildable))
			return false;
		return true;
	}

	@Override
	public int compareTo(BuildableWrapper o) {
		int result = this.getSizeX() - o.getSizeX();
		return result == 0 ? this.getSizeZ() - o.getSizeZ() : result;
	}

}
