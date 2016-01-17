package codemetropolis.toolchain.placing.layout.pack;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.placing.exceptions.LayoutException;

public class House {
	
	private final int minHeight;
	private final int maxHeight;

	private Buildable parent;
	private List<Buildable> floors = new ArrayList<Buildable>();
	private List<Buildable> cellars = new ArrayList<Buildable>();
	private Buildable topFloor;
	private Buildable bottomFloor;
	private Buildable topCellar;
	private Buildable bottomCellar;
	
	public House(int minHeight, int maxHeight) {
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

	public boolean add(Buildable b) throws LayoutException {
		if(b.getType() == Buildable.Type.FLOOR)
			return addFloor(b);
		else
			return addCellar(b);
	}
	
	public boolean addFloor(Buildable floor) throws LayoutException {
		if(floor.getType() != Buildable.Type.FLOOR) {
			throw new LayoutException(String.format("Cannot add %s %d as a floor. Wrong buildable type.", floor.getType().toString(), floor.getId()));
		}
		if(bottomFloor == null) {
			floors.add(floor);
			if(topCellar != null) {
				floor.setPositionX(topCellar.getCenter().getX() - (floor.getSizeX() / 2));
				floor.setPositionZ(topCellar.getCenter().getZ() - (floor.getSizeZ() / 2));
			}
			parent = floor.getParent();
			bottomFloor = floor;
			topFloor = floor;
			return true;
		}
		if(floor.getParent() != parent) {
			throw new LayoutException(String.format("Cannot add %s %d as a floor. Floors and cellars in the house must be childs of the same parent.", floor.getType().toString(), floor.getId()));
		}
		if(topFloor.getPositionY() + topFloor.getSizeY() + floor.getSizeY() > maxHeight) return false;
		if(floor.getSizeX() > topFloor.getSizeX() || floor.getSizeZ() > topFloor.getSizeZ()) return false;
		
		floors.add(floor);
		floor.setPositionY(topFloor.getPositionY() + topFloor.getSizeY());
		floor.setPositionX(topFloor.getCenter().getX() - (floor.getSizeX() / 2));
		floor.setPositionZ(topFloor.getCenter().getZ() - (floor.getSizeZ() / 2));
		topFloor = floor;
		
		return true;
	}
	
	public boolean addCellar(Buildable cellar) throws LayoutException {
		if(cellar.getType() != Buildable.Type.CELLAR) {
			throw new LayoutException(String.format("Cannot add %s %d as a cellar. Wrong buildable type.", cellar.getType().toString(), cellar.getId()));
		}
		if(topCellar == null) {
			cellars.add(cellar);
			cellar.setPositionY(cellar.getPositionY() - cellar.getSizeY() - 1);
			if(bottomFloor != null) {
				cellar.setPositionX(bottomFloor.getCenter().getX() - (cellar.getSizeX() / 2));
				cellar.setPositionZ(bottomFloor.getCenter().getZ() - (cellar.getSizeZ() / 2));
			}
			parent = cellar.getParent();
			topCellar = cellar;
			bottomCellar = cellar;
			return true;
		}
		if(cellar.getParent() != parent) {
			throw new LayoutException(String.format("Cannot add %s %d as a floor. Floors and cellars in the house must be childs of the same parent.", cellar.getType().toString(), cellar.getId()));
		}
		if(bottomCellar.getPositionY() - cellar.getSizeY() < minHeight) return false;
		if(cellar.getSizeX() > bottomCellar.getSizeX() || cellar.getSizeZ() > bottomCellar.getSizeZ()) return false;
		
		cellars.add(cellar);
		cellar.setPositionY(bottomCellar.getPositionY() - cellar.getSizeY() - 1);
		cellar.setPositionX(bottomCellar.getCenter().getX() - (cellar.getSizeX() / 2));
		cellar.setPositionZ(bottomCellar.getCenter().getZ() - (cellar.getSizeZ() / 2));
		bottomCellar = cellar;
		
		return true;
	}
	
	public int getSizeX() {
		if(bottomFloor == null) return topCellar.getSizeX();
		if(topCellar == null) return bottomFloor.getSizeX();
		return (bottomFloor.getSizeX() > topCellar.getSizeX() ? bottomFloor.getSizeX() : topCellar.getSizeX());
	}
	
	public int getSizeZ() {
		if(bottomFloor == null) return topCellar.getSizeZ();
		if(topCellar == null) return bottomFloor.getSizeZ();
		return (bottomFloor.getSizeZ() > topCellar.getSizeZ() ? bottomFloor.getSizeZ() : topCellar.getSizeZ());
	}
	
	public void translateNearX(int x) {
		for(Buildable b : floors) b.setPositionXR(b.getPositionX() + x);
		for(Buildable b : cellars) b.setPositionXR(b.getPositionX() + x);
	}
	
	public void translateNearZ(int z) {
		for(Buildable b : floors) b.setPositionZR(b.getPositionZ() + z);
		for(Buildable b : cellars) b.setPositionZR(b.getPositionZ() + z);
	}
	
	public int getPositionX() {
		if(bottomFloor == null) return topCellar.getPositionX();
		if(topCellar == null) return bottomFloor.getPositionX();
		return (bottomFloor.getPositionX() < topCellar.getPositionX() ? bottomFloor.getPositionX() : topCellar.getPositionX());
	}
	
	public int getPositionZ() {
		if(bottomFloor == null) return topCellar.getPositionZ();
		if(topCellar == null) return bottomFloor.getPositionZ();
		return (bottomFloor.getPositionZ() < topCellar.getPositionZ() ? bottomFloor.getPositionZ() : topCellar.getPositionZ());
	}
	
	public void setPositionXR(int x) {
		for(Buildable b : floors) {
			int offset = b.getPositionX() - b.getParent().getPositionX();
			b.setPositionXR(x + offset);
		}
		for(Buildable b : cellars) {
			int offset = b.getPositionX() - b.getParent().getPositionX();
			b.setPositionXR(x + offset);
		}
	}
	
	public void setPositionZR(int z) {
		for(Buildable b : floors) {
			int offset = b.getPositionZ() - b.getParent().getPositionZ();
			b.setPositionZR(z + offset);
		}
		for(Buildable b : cellars) {
			int offset = b.getPositionZ() - b.getParent().getPositionZ();
			b.setPositionZR(z + offset);
		}
	}

	public Buildable getParent() {
		return parent;
	}
	
}
