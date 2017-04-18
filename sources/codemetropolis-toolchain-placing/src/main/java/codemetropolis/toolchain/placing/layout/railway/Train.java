package codemetropolis.toolchain.placing.layout.railway;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.placing.exceptions.LayoutException;

public class Train {

	private Buildable parent;
	private Buildable firstWagon;
	
	private List<Buildable> wagons = new ArrayList<Buildable>();

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
	
	public Buildable getParent() {
		return parent;
	}
	
	public void setParent(Buildable parent) {
		this.parent = parent;
	}
	
	public Buildable getFirstWagon() {
		return this.firstWagon;
	}

	public int getSizeX() {
		return wagons.get(wagons.size() - 1).getSizeX();
	}
	
	public int getSizeY() {
		return wagons.get(wagons.size() - 1).getSizeY();
	}
	
	public int getSizeZ() {
		return wagons.get(wagons.size() - 1).getSizeZ();
	}
	
	public void setSizeX(int size) {
		wagons.get(wagons.size() - 1).setSizeX(size);
	}
	
	public void setSizeY(int size) {
		wagons.get(wagons.size() - 1).setSizeY(size);
	}
	
	public void setSizeZ(int size) {
		wagons.get(wagons.size() - 1).setSizeZ(size);
	}
	
	public void translateNearX(int x) {
		for(Buildable b : wagons) b.setPositionXR(b.getPositionX() + x);
	}
	
	public void translateNearZ(int z) {
		for(Buildable b : wagons) b.setPositionZR(b.getPositionZ() + z);
	}
	
	public int getPositionX() {
		return wagons.get(wagons.size() - 1).getPositionX();
	}
	
	public int getPositionY() {
		return wagons.get(wagons.size() - 1).getPositionY();
	}
	
	public int getPositionZ() {
		return wagons.get(wagons.size() - 1).getPositionZ();
	}
	
	public void setPositionXR(int x) {
		for(Buildable b : wagons) {
			int offset = b.getPositionX() - b.getParent().getPositionX();
			b.setPositionXR(x + offset);
		}
	}
	
	public void setPositionYR(int y) {
		for(Buildable b : wagons) {
			int offset = b.getPositionY() - b.getParent().getPositionY();
			b.setPositionYR(y + offset);
		}
	}
	
	public void setPositionZR(int z) {
		for(Buildable b : wagons) {
			int offset = b.getPositionZ() - b.getParent().getPositionZ();
			b.setPositionZR(z + offset);
		}
	}
	
}
