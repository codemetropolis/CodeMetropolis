package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Chest implements Primitive {
/**
     * Contains all the possible orientations a chest can have. This orientation is also used by the generated Droppers, because they have the
     * same possible orientations.
     * 
     * 0 - Up (used for Droppers)
     * 1 - Down (used for Droppers)
     * 2 - North
     * 3 - South
     * 4 - West
     * 5 - East
     * 
    */

	public enum Orientation {
		UP(0),
		DOWN(1),
		
		NORTH(2),
		SOUTH(3),
		WEST(4),
		EAST(5);
		
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
	
	/**
	 * Generated a Minecraft chest at the {x, y, z} coordinates given, with an orientation.
	 * 
	 * @param   x   Minecraft world's x coordinate where the chest needs to be generated
	 * @param   y   Minecraft world's y coordinate where the chest needs to be generated
	 * @param   z   Minecraft world's z coordinate where the chest needs to be generated
	 * @param   orientation     the orientation of the chest, so the chest always openes in front
	 * @return  the Chest object
	 * 
	*/

	public Chest(int x, int y, int z, Orientation orientation) {
		this(new Point(x, y, z), orientation);
	}
	
	public Chest(Point position, Orientation orientation) {
		super();
		this.position = position;
		this.orientation = orientation;
	}
	
	@Override
	public int toCSVFile(File directory) {
		new Boxel(new BasicBlock((short) 54, orientation.getValue()), position).toCSVFile(directory);
		return 1;
	}
	
	@Override
	public int getNumberOfBlocks() {
		return 1;
	}
	
	public BasicBlock chestToBlock() {
		return new BasicBlock((short) 54, orientation.getValue());
	}
}
