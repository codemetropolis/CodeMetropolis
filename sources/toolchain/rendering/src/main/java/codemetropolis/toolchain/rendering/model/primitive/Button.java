package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Button implements Primitive {
    /**
     * Contains all the possible (used) orientations a button can have. 
     * 
     * 1 - West
     * 2 - East
     * 3 - North
     * 4 - South
     * 
    */
	public enum Orientation {
		WEST(1),	
		EAST(2),
		NORTH(3),
		SOUTH(4);
		
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
	 * Generated a Minecraft button at the {x, y, z} coordinates given, with an orientation. The reason why we need this is because the button always have to spawn
	 * on a Dropper, and not in the air, because that would be impossible to achieve in vanilla Minecraft.
	 * 
	 * @param   x   Minecraft world's x coordinate where the button needs to be generated
	 * @param   y   Minecraft world's y coordinate where the button needs to be generated
	 * @param   z   Minecraft world's z coordinate where the button needs to be generated
	 * @param   orientation     the orientation which the button needs to have to spawn on the Drooper
	 * @return  the Button object
	 * 
	*/
	
	public Button(int x, int y, int z, Orientation orientation) {
		this(new Point(x, y, z), orientation);
	}
	
	public Button(Point position, Orientation orientation) {
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
}
