package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.BlockType;
import codemetropolis.toolchain.commons.model.property.Orientation8;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Banner implements Primitive {

	private Point position;
	private BasicBlock block;
	private Orientation8 orientation8;

	public Banner(BlockType block, Orientation8 orientation8, Point p) {
		super();
		this.position = p;
		this.orientation8 = orientation8;
		this.block = new BasicBlock(block);
	}


	@Override
	public int toCSVFile(File directory) {
		BasicBlock block = this.block;
		block.addProperty(orientation8);
		new Boxel(block, position).toCSVFile(directory);
		return 1;
	}

	@Override
	public int getNumberOfBlocks() {
		return 1;
	}
}
