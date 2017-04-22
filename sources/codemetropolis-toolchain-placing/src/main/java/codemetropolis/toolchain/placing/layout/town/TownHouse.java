package codemetropolis.toolchain.placing.layout.town;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.layout.pack.House;

/**
 * A subclass for {@link House} with a little modification for {@link Buildable.Type#CELLAR} floors. So cellars are not
 * under the ground.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TownHouse extends House {

	/**
	 * Set the min and max height. So houses will be realistic in height.
	 * 
	 * @param minHeight The minimum height.
	 * @param maxHeight The maximum height.
	 */
	public TownHouse(int minHeight, int maxHeight) {
		super(minHeight, maxHeight);
	}

	/**
	 * Height of a house can only be 1 floor height.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean addFloor(Buildable floor) throws LayoutException {
		// Only allow a single floor (except for an additional decoration floor
		if (topFloor != null && floor.getType() != Buildable.Type.DECORATION_FLOOR) {
			return false;
		}

		if (floor.getType() != Buildable.Type.FLOOR && floor.getType() != Buildable.Type.DECORATION_FLOOR) {
			throw new LayoutException(String.format("Cannot add %s %d as a floor. Wrong buildable type.",
				floor.getType().toString(), floor.getId()));
		}

		if (bottomFloor == null) {
			floors.add(floor);
			if (topCellar != null) {
				floor.setPositionX(topCellar.getCenter().getX() - (floor.getSizeX() / 2));
				floor.setPositionZ(topCellar.getCenter().getZ() - (floor.getSizeZ() / 2));
			} else {
				floor.setPositionY(minHeight);
			}
			parent = floor.getParent();
			bottomFloor = floor;
			topFloor = floor;
			return true;
		}

		if (floor.getParent() != parent) {
			throw new LayoutException(String.format(
				"Cannot add %s %d as a floor. Floors and cellars in the house must be childs of the same parent.",
				floor.getType().toString(), floor.getId()));
		}

		if (topFloor.getPositionY() + topFloor.getSizeY() + floor.getSizeY() > maxHeight)
			return false;
		if (floor.getSizeX() > topFloor.getSizeX() || floor.getSizeZ() > topFloor.getSizeZ())
			return false;

		floors.add(floor);
		floor.setPositionY(topFloor.getPositionY() + topFloor.getSizeY());
		floor.setPositionX(topFloor.getCenter().getX() - (floor.getSizeX() / 2));
		floor.setPositionZ(topFloor.getCenter().getZ() - (floor.getSizeZ() / 2));
		topFloor = floor;

		return true;
	}

	/**
	 * Cellars are added similarly to floors, so they won't be under the ground. Height is maximum one floor height.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean addCellar(Buildable cellar) throws LayoutException {
		// Only allow a single floor
		if (topFloor != null) {
			return false;
		}

		if (cellar.getType() != Buildable.Type.CELLAR) {
			throw new LayoutException(String.format("Cannot add %s %d as a cellar. Wrong buildable type.",
				cellar.getType().toString(), cellar.getId()));
		}

		if (bottomFloor == null) {
			floors.add(cellar);
			if (topCellar != null) {
				cellar.setPositionX(topCellar.getCenter().getX() - (cellar.getSizeX() / 2));
				cellar.setPositionZ(topCellar.getCenter().getZ() - (cellar.getSizeZ() / 2));
			} else {
				cellar.setPositionY(minHeight);
			}
			setParent(cellar.getParent());
			bottomFloor = cellar;
			topFloor = cellar;
			return true;
		}

		if (cellar.getParent() != getParent()) {
			throw new LayoutException(String.format(
				"Cannot add %s %d as a cellar. Floors and cellars in the house must be childs of the same parent.",
				cellar.getType().toString(), cellar.getId()));
		}

		if (getTopFloor().getPositionY() + getTopFloor().getSizeY() + cellar.getSizeY() > maxHeight)
			return false;
		if (cellar.getSizeX() > getTopFloor().getSizeX() || cellar.getSizeZ() > getTopFloor().getSizeZ())
			return false;

		floors.add(cellar);
		cellar.setPositionY(getTopFloor().getPositionY() + getTopFloor().getSizeY());
		cellar.setPositionX(getTopFloor().getCenter().getX() - (cellar.getSizeX() / 2));
		cellar.setPositionZ(getTopFloor().getCenter().getZ() - (cellar.getSizeZ() / 2));
		topFloor = cellar;

		return true;
	}

	/**
	 * Parent's size is no more subtracted.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void setPositionXR(int x) {
		for (Buildable b : floors) {
			int offset = b.getPositionX();
			b.setPositionXR(x + offset);
		}
		for (Buildable b : cellars) {
			int offset = b.getPositionX();
			b.setPositionXR(x + offset);
		}
	}

	/**
	 * Parent's position is no more subtracted.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void setPositionZR(int z) {
		for (Buildable b : floors) {
			int offset = b.getPositionZ();
			b.setPositionZR(z + offset);
		}
		for (Buildable b : cellars) {
			int offset = b.getPositionZ();
			b.setPositionZR(z + offset);
		}
	}

}
