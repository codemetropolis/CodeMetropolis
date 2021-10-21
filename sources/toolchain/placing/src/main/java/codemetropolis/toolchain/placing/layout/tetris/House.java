package codemetropolis.toolchain.placing.layout.tetris;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;

public class House {
	
	private final int maxHeight;
	private final int minHeight;
	
	private List<Buildable> floorsAndCellars = new ArrayList<>();
	private Buildable top = null;
	private Buildable bottom = null;
	
	public House(int minHeight, int maxHeight) {
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

	public boolean addFloor(Buildable b) {
		if(b.getType() != Type.FLOOR)
			return false;
		if(top != null) {
			if(
					getHeight() + b.getSizeY() > maxHeight ||
					b.getSizeX() > getSizeX() ||
					b.getSizeZ() > getSizeZ())
				return false;
			
			b.setPositionY(getHeight());
		} else {
			bottom = b;
			b.setPositionY(b.getParent().getPositionY() + 1);
		}
		top = b;
		floorsAndCellars.add(b);
		setSizeOfContainingBuildables(b);
		return true;
	}

	private void setSizeOfContainingBuildables(Buildable b) {
		Buildable parent = b.getParent();
		while(parent != null) {
			if(getHeight() - parent.getPositionY() + 1 > parent.getSizeY()) {
				parent.setSizeY(getHeight() - parent.getPositionY() + 1);
				parent = parent.getParent();
			} else {
				break;
			}
		}
	}
	
	public boolean addCellar(Buildable b) {
		if(b.getType() != Type.CELLAR)
			return false;
		if(bottom != null) {
			if(
					getDepth() - b.getSizeY() < minHeight ||
					b.getSizeX() > getSizeX() ||
					b.getSizeZ() > getSizeZ())
				return false;
			
			b.setPositionY(getDepth() - b.getSizeY());
		} else {
			top = b;
			b.setPositionY(b.getParent().getPositionY() - b.getSizeY());
		}
		bottom = b;
		floorsAndCellars.add(b);
		
		return true;
	}

	public List<Buildable> getFloorsAndCellars() {
		return new ArrayList<>(floorsAndCellars);
	}
	
	public Buildable getTop() {
		return top;
	}

	public Buildable getBottom() {
		return bottom;
	}
	
	public int getSizeX() {
		int max = 0;
		for(Buildable b : floorsAndCellars)
			if(b.getSizeX() > max) max = b.getSizeX();
		return max;
	}
	
	public int getSizeY() {
		return top.getPositionY() + top.getSizeY();
	}
	
	public int getSizeZ() {
		int max = 0;
		for(Buildable b : floorsAndCellars)
			if(b.getSizeZ() > max) max = b.getSizeZ();
		return max;
	}
	
	public int getHeight() {
		return getSizeY();
	}
	
	public int getDepth() {
		return bottom.getPositionY();
	}
	
}
