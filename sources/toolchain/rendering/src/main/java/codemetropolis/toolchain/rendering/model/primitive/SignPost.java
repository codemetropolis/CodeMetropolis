package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.property.Orientation8;
import codemetropolis.toolchain.rendering.model.BasicBlock;

import static codemetropolis.toolchain.commons.model.BlockType.SIGN;


public class SignPost implements Primitive {
	
	private Point position;
	private Orientation8 orientation8;
	private String text;

	public SignPost(int x, int y, int z, Orientation8 orientation8, String text) {
		super();
		this.position = new Point(x, y, z);
		this.orientation8 = orientation8;
		this.text = text;
	}
	
	@Override
	public int toCSVFile(File directory) {

		BasicBlock signPost = new BasicBlock(SIGN);
		signPost.addProperty(orientation8);

		new Boxel(signPost, position, text).toCSVFile(directory);
		return 1;
	}

	@Override
	public int getNumberOfBlocks() {
		return 1;
	}
	
}
