package codemetropolis.toolchain.placing.layout.town;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.placing.layout.pack.PackLayout;
import codemetropolis.toolchain.placing.layout.town.TownHouse;

/**
 * A wrapper class for preparing houses for the {@link PackLayout} packer.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class BuildableWrapper implements Comparable<BuildableWrapper> {

	private Object buildable;

	/**
	 * Creates a {@link BuildableWrapper} of a {@link Buildable}.
	 * 
	 * @param buildable {@link Buildable} type {@code buildable}.
	 */
	public BuildableWrapper(Buildable buildable) {
		this.buildable = buildable;
	}

	/**
	 * Creates a {@link BuildableWrapper} of a {@link TownHouse}.
	 * 
	 * @param buildable {@link TownHouse} type {@code buildable}.
	 */
	public BuildableWrapper(TownHouse buildable) {
		this.buildable = buildable;
	}

	/**
	 * Gets a {@link Buildable} object of a wrapper.
	 * 
	 * @return
	 */
	public Object getInnerBuildable() {
		return buildable;
	}

	/**
	 * Get the x dimensional position depending on the type of the {@link Buildable}.
	 * 
	 * @return The x dimensional position as an integer.
	 */
	public int getPositionX() {
		if (buildable instanceof Buildable) return ((Buildable) buildable).getPositionX();
		if (buildable instanceof TownHouse) return ((TownHouse) buildable).getPositionX();
		return 0;
	}

	/**
	 * Get the z dimensional position depending on the type of the {@link Buildable}.
	 * 
	 * @return The z dimensional position as an integer.
	 */
	public int getPositionZ() {
		if (buildable instanceof Buildable) return ((Buildable) buildable).getPositionZ();
		if (buildable instanceof TownHouse) return ((TownHouse) buildable).getPositionZ();
		return 0;
	}

	/**
	 * Get the x dimensional size depending on the type of the {@link Buildable}.
	 * 
	 * @return The x dimensional size as an integer.
	 */
	public int getSizeX() {
		if (buildable instanceof Buildable) return ((Buildable) buildable).getSizeX();
		if (buildable instanceof TownHouse) return ((TownHouse) buildable).getSizeX();
		return 0;
	}

	/**
	 * Get the z dimensional size depending on the type of the {@link Buildable}.
	 * 
	 * @return The z dimensional size as an integer.
	 */
	public int getSizeZ() {
		if (buildable instanceof Buildable) return ((Buildable) buildable).getSizeZ();
		if (buildable instanceof TownHouse) return ((TownHouse) buildable).getSizeZ();
		return 0;
	}

	/**
	 * Set the x dimensional position depending on the type of the {@link Buildable}.
	 * 
	 * @param x The x dimensional position.
	 */
	public void setPositionX(int x) {
		if (buildable instanceof Buildable) ((Buildable) buildable).setPositionXR(x);
		if (buildable instanceof TownHouse) ((TownHouse) buildable).setPositionXR(x);
	}

	/**
	 * Set the z dimensional position depending on the type of the {@link Buildable}.
	 * 
	 * @param z The z dimensional position.
	 */
	public void setPositionZ(int z) {
		if (buildable instanceof Buildable) ((Buildable) buildable).setPositionZR(z);
		if (buildable instanceof TownHouse) ((TownHouse) buildable).setPositionZR(z);
	}

	/**
	 * Gets the parent of a {@link Buildable} depending on the type of the the {@link Buildable}.
	 * 
	 * @return The desired parent.
	 */
	public Buildable getParent() {
		if (buildable instanceof Buildable) return ((Buildable) buildable).getParent();
		if (buildable instanceof TownHouse) return ((TownHouse) buildable).getParent();
		return null;
	}

	/**
	 * Collect all the children of a garden as a list.
	 * 
	 * @param houses The houses which are tested.
	 * @return The list of a garden's houses.
	 */
	public List<BuildableWrapper> getTownChildren(Map<Buildable, List<TownHouse>> houses) {
		List<BuildableWrapper> result = new ArrayList<BuildableWrapper>();
		if (buildable instanceof TownHouse) {
			return result;
		}
		
		for (Buildable child : ((Buildable) buildable).getChildren()) {
			if (child.getType() == Buildable.Type.FLOOR ||
				child.getType() == Buildable.Type.DECORATION_FLOOR ||
				child.getType() == Buildable.Type.CELLAR) {
				
				continue;
			}
			
			result.add(new BuildableWrapper(child));
		}
		
		if (houses.get((Buildable) buildable) != null) {
			for (TownHouse h : houses.get((Buildable) buildable)) {
				result.add(new BuildableWrapper(h));
			}
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
			+ ((buildable == null) ? 0 : buildable.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(BuildableWrapper o) {
		int result = this.getSizeX() - o.getSizeX();
		return result == 0 ? this.getSizeZ() - o.getSizeZ() : result;
	}

}
