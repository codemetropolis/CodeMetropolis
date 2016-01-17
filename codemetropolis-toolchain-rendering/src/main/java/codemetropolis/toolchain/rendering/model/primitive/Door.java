package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Door implements Primitive {
	
	public enum Orientation {
		NORTH(1),
		SOUTH(3),
		WEST(0),
		EAST(2);
		
		private final int value;
		
		Orientation(int v) {
			value = v;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	private Point position;
	private Orientation orientation;

	public Door(int x, int y, int z, Orientation orientation) {
		super();
		this.position = new Point(x, y, z);
		this.orientation = orientation;
	}
	
	@Override
	public int toCSVFile(File directory) {
		new Boxel(new BasicBlock((short) 64, orientation.getValue()), position).toCSVFile(directory);
		new Boxel(new BasicBlock((short) 64, 8), new Point(position.getX(), position.getY() + 1, position.getZ())).toCSVFile(directory);
		return 2;
	}
	@Override
	public int getNumberOfBlocks() {
		return 2;
	}
	
}
