package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.property.OrientationDoor;
import codemetropolis.toolchain.rendering.model.BasicBlock;

import static codemetropolis.toolchain.commons.model.BlockType.DOOR;

public class Door implements Primitive {

	
	private Point position;
	private OrientationDoor orientationDoor;

	public Door(int x, int y, int z, OrientationDoor orientationDoor) {
		super();
		this.position = new Point(x, y, z);
		this.orientationDoor = orientationDoor;
	}

	@Override
	public int toCSVFile(File directory) {
		BasicBlock upperDoor = new BasicBlock(DOOR);
		upperDoor.addProperty(orientationDoor);
		BasicBlock lowerDoor = new BasicBlock(DOOR);
		lowerDoor.addProperty(orientationDoor);

		new Boxel(lowerDoor, position).toCSVFile(directory);
		new Boxel(upperDoor, new Point(position.getX(), position.getY() + 1, position.getZ())).toCSVFile(directory);
		return 2;
	}
	@Override
	public int getNumberOfBlocks() {
		return 2;
	}
	
}
