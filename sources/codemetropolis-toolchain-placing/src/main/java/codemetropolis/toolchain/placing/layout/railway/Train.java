package codemetropolis.toolchain.placing.layout.railway;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.layout.pack.House;

/**
 * A new type of {@link House}, represented as a train.
 * Now you can represent your buildings with a list of wagons.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class Train {

	private Buildable parent;
	private Buildable firstWagon;
	
	private List<Buildable> wagons = new ArrayList<Buildable>();

	/**
	 * Adding wagons one-by-one to a train.
	 * 
	 * @param wagon The wagon which is going to be added to the train.
	 * @return Returns true if it could successfully add the train.
	 * @throws LayoutException Throws exception, if wagon's type is incorrect.
	 */
	public boolean addWagon(Buildable wagon) throws LayoutException {
		if(wagon.getType() != Buildable.Type.FLOOR &&  wagon.getType() != Buildable.Type.CELLAR) {
			throw new LayoutException(String.format("Cannot add %s %d as a wagon. Wrong buildable type.", wagon.getType().toString(), wagon.getId()));
		}
		if(firstWagon == null) {
			wagons.add(wagon);
			parent = wagon.getParent();
			firstWagon = wagon;
			return true;
		}
		if(wagon.getParent() != parent) {
			throw new LayoutException(String.format("Cannot add %s %d as a wagon. Wagons in the train must be childs of the same parent.", wagon.getType().toString(), wagon.getId()));
		}
		
		wagons.add(wagon);
		
		return true;
	}
	
	/**
	 * Add an engine at the beginning of the train, to make it realistic.
	 * 
	 * @param engine The generated engine, which is going to be added to the train.
	 * @return Returns true if it could successfully add the train.
	 * @throws LayoutException Throws exception, if wagon's type is incorrect.
	 */
	public boolean addEngine(Buildable engine) throws LayoutException {
		if(engine.getType() != Buildable.Type.DECORATION_FLOOR) {
			throw new LayoutException(String.format("Cannot add %s %d as an engine. Wrong buildable type.", engine.getType().toString(), engine.getId()));
		}
		if(engine.getParent() != parent) {
			throw new LayoutException(String.format("Cannot add %s %d as an engine. Wagons in the train must be childs of the same parent.", engine.getType().toString(), engine.getId()));
		}
		
		wagons.add(0, engine);
		
		return true;
	}
	
	/**
	 * Get the parent of the current wagon.
	 * 
	 * @return Returns with the parent
	 */
	public Buildable getParent() {
		return parent;
	}
	
	/**
	 * Set the parent for the current wagon.
	 * 
	 * @param parent The desired parent.
	 */
	public void setParent(Buildable parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the first wagon of a train. So engine can be added before this one.
	 * 
	 * @return The first wagon of a train.
	 */
	public Buildable getFirstWagon() {
		return this.firstWagon;
	}

	/**
	 * Gets the x dimensional size of a wagon.
	 * 
	 * @return The size of the wagon in x dimension.
	 */
	public int getSizeX() {
		return wagons.get(wagons.size() - 1).getSizeX();
	}
	
	/**
	 * Gets the y dimensional size of a wagon.
	 * 
	 * @return The size of the wagon in y dimension.
	 */
	public int getSizeY() {
		return wagons.get(wagons.size() - 1).getSizeY();
	}
	
	/**
	 * Gets the z dimensional size of a wagon.
	 * 
	 * @return The size of the wagon in z dimension.
	 */
	public int getSizeZ() {
		return wagons.get(wagons.size() - 1).getSizeZ();
	}
	
	/**
	 * Sets the x dimensional size of a wagon.
	 * 
	 * @param size The desired x dimensional size.
	 */
	public void setSizeX(int size) {
		wagons.get(wagons.size() - 1).setSizeX(size);
	}
	
	/**
	 * Sets the y dimensional size of a wagon.
	 * 
	 * @param size The desired y dimensional size.
	 */
	public void setSizeY(int size) {
		wagons.get(wagons.size() - 1).setSizeY(size);
	}
	
	/**
	 * Sets the z dimensional size of a wagon.
	 * 
	 * @param size The desired z dimensional size.
	 */
	public void setSizeZ(int size) {
		wagons.get(wagons.size() - 1).setSizeZ(size);
	}
	
	/**
	 * Transforms the wagons matrix in x dimension.
	 * 
	 * @param x The value of transformation.
	 */
	public void translateNearX(int x) {
		for(Buildable b : wagons) b.setPositionXR(b.getPositionX() + x);
	}
	
	/**
	 * Transforms the wagons matrix in z dimension.
	 * 
	 * @param x The value of transformation.
	 */
	public void translateNearZ(int z) {
		for(Buildable b : wagons) b.setPositionZR(b.getPositionZ() + z);
	}
	
	/**
	 * Gets the x dimensional position of a wagon.
	 * 
	 * @return The position of the wagon in x dimension.
	 */
	public int getPositionX() {
		return wagons.get(wagons.size() - 1).getPositionX();
	}
	
	/**
	 * Gets the y dimensional position of a wagon.
	 * 
	 * @return The position of the wagon in y dimension.
	 */
	public int getPositionY() {
		return wagons.get(wagons.size() - 1).getPositionY();
	}
	
	/**
	 * Gets the z dimensional position of a wagon.
	 * 
	 * @return The position of the wagon in z dimension.
	 */
	public int getPositionZ() {
		return wagons.get(wagons.size() - 1).getPositionZ();
	}
	
	/**
	 * Sets the new position of a wagon with an x dimensional transformation.
	 * 
	 * @param x The value of the transformation.
	 */
	public void setPositionXR(int x) {
		for(Buildable b : wagons) {
			int offset = b.getPositionX() - b.getParent().getPositionX();
			b.setPositionXR(x + offset);
		}
	}
	
	/**
	 * Sets the new position of a wagon with an y dimensional transformation.
	 * 
	 * @param x The value of the transformation.
	 */
	public void setPositionYR(int y) {
		for(Buildable b : wagons) {
			int offset = b.getPositionY() - b.getParent().getPositionY();
			b.setPositionYR(y + offset);
		}
	}
	
	/**
	 * Sets the new position of a wagon with an z dimensional transformation.
	 * 
	 * @param x The value of the transformation.
	 */
	public void setPositionZR(int z) {
		for(Buildable b : wagons) {
			int offset = b.getPositionZ() - b.getParent().getPositionZ();
			b.setPositionZR(z + offset);
		}
	}
	
}
