package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

/**
 * Represents the ladder object in Minecraft.
 */
public class Ladder implements Primitive{

	/**
	 * Defines the direction that the ladder is facing.
	 */
    public enum Orientation {
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
	 * Creates a new ladder object.
	 * @param x The coords position of the ladder on the X axis.
	 * @param y The coords position of the ladder on the Y axis.
	 * @param z The coords position of the ladder on the Z axis.
	 * @param orientation The direction that the ladder is facing.
	 */
    public Ladder(int x, int y, int z, Orientation orientation) {
		super();
		this.position = new Point(x, y, z);
		this.orientation = orientation;
	}

	/**
	 * Tells how the ladder block would be saved into the final CSV file. 
	 * @return The number of blocks placed.
	 */
    @Override
    public int toCSVFile(File directory) {
        new Boxel(new BasicBlock((short) 65, orientation.getValue()), position).toCSVFile(directory);
        return getNumberOfBlocks();
    }

	/**
	 * Tells us a ladder object in Minecraft consist of how many ingame blocks.
	 */
    @Override
    public int getNumberOfBlocks() {
        return 1;
    }
    
}
