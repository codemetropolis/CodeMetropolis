package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

import static codemetropolis.toolchain.rendering.model.BasicBlock.BasicBlockType.DOOR;

public class Door implements Primitive {

	public enum Orientation {
		NORTH(3),
		SOUTH(1),
		WEST(2),
		EAST(0);

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
		Map<String, String> upperDoorProperties = new HashMap<>();
		upperDoorProperties.put("facing", Integer.toString(orientation.getValue()));
		upperDoorProperties.put("half", "upper");

		Map<String, String> lowerDoorProperties = new HashMap<>();
		lowerDoorProperties.put("facing", Integer.toString(orientation.getValue()));
		lowerDoorProperties.put("half", "lower");

		BasicBlock upperDoor = new BasicBlock(DOOR.getBlock(), upperDoorProperties);
		BasicBlock lowerDoor = new BasicBlock(DOOR.getBlock(), lowerDoorProperties);

		new Boxel(lowerDoor, position).toCSVFile(directory);
		new Boxel(upperDoor, new Point(position.getX(), position.getY() + 1, position.getZ())).toCSVFile(directory);
		return 2;
	}
	@Override
	public int getNumberOfBlocks() {
		return 2;
	}
	
}
