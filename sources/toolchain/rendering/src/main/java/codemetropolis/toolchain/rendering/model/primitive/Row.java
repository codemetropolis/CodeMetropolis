package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.BlockType;
import codemetropolis.toolchain.commons.model.property.BlockFacingTorch;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Row implements Primitive {
	
	public enum Direction {
		UP,
		DOWN,
		NORTH,
		SOUTH,
		WEST,
		EAST
	}
	
	private Point position;
	private int length;
	private Direction orientation;
	private BlockType[] pattern;
	private BlockFacingTorch facing;
	
	public Row(Point position, int length, Direction orientation, BlockType[] pattern, BlockFacingTorch facing) {
		super();
		this.facing = facing;
		this.position = position;
		this.length = length;
		this.orientation = orientation;
		this.pattern = pattern;
	}

	public Row(Point position, int length, Direction orientation, BlockType[] pattern) {
		this(position, length, orientation, pattern, BlockFacingTorch.NONE);
	}

	/**
	 * Converts the pattern configuration to CSV files in the specified directory.
	 *
	 * <p>This method converts the pattern configuration to CSV files representing the blocks in the pattern,
	 * and saves them in the specified directory. It iterates through the pattern, creating a BasicBlock instance
	 * for each block in the pattern and assigning properties such as facing direction if applicable. The method
	 * calculates the position of each block based on the orientation and position of the pattern, and then converts
	 * them to CSV files using the Boxel class. The method returns the length of the pattern, indicating the number
	 * of CSV files created.</p>
	 *
	 * @param directory the directory where the CSV files will be saved
	 * @return the length of the pattern, indicating the number of CSV files created
	 */
	@Override
	public int toCSVFile(File directory) {
		int c = 0;
		for(int i = 0; i < length; i++) {
			BasicBlock block = new BasicBlock(pattern[c]);
			if(facing != BlockFacingTorch.NONE) {
				block.getProperties().add(facing);
			}
			Point blockPos = switch (orientation) {
                case UP -> new Point(position.getX(), position.getY() + i, position.getZ());
                case DOWN -> new Point(position.getX(), position.getY() - i, position.getZ());
                case NORTH -> new Point(position.getX(), position.getY(), position.getZ() + i);
                case SOUTH -> new Point(position.getX(), position.getY(), position.getZ() - i);
                case WEST -> new Point(position.getX() + i, position.getY(), position.getZ());
                case EAST -> new Point(position.getX() - i, position.getY(), position.getZ());
            };

            new Boxel(block, blockPos).toCSVFile(directory);
			if(++c > pattern.length - 1) c = 0;
		}
		return length;
	}
	
	@Override
	public int getNumberOfBlocks() {
		return length;
	}
	
}
