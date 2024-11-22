package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.property.Orientation4;
import codemetropolis.toolchain.rendering.model.BasicBlock;

import static codemetropolis.toolchain.commons.model.BlockType.WALL_SIGN;

public class WallSign implements Primitive {
	
	private Point position;
	private Orientation4 orientation4;
	private String text;

	public WallSign(int x, int y, int z, Orientation4 orientation4, String text) {
		this(new Point(x,y,z), orientation4, text);
	}
	
	public WallSign(Point position, Orientation4 orientation4, String text) {
		super();
		this.position = position;
		this.orientation4 = orientation4;
		this.text = text;
	}
	
	@Override
	public int toCSVFile(File directory) {

		BasicBlock wallSign = new BasicBlock(WALL_SIGN);
		wallSign.addProperty(orientation4);


		new Boxel(wallSign, position, text).toCSVFile(directory);
		return 1;
	}

	@Override
	public int getNumberOfBlocks() {
		return 1;
	}
	
}
