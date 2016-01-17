package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Row implements Primitive {
	
	public enum Direction {
		UP,
		DOWN,
		NORTH,
		SOUTH,
		WEST,
		EAST;
	}
	
	private Point position;
	private int length;
	private Direction orientation;
	private BasicBlock[] pattern;
	
	public Row(Point position, int length, Direction orientation, BasicBlock[] pattern) {
		super();
		this.position = position;
		this.length = length;
		this.orientation = orientation;
		this.pattern = pattern;
	}
	
	@Override
	public int toCSVFile(File directory) {
		int c = 0;
		for(int i = 0; i < length; i++) {
			Point blockPos = null;
			switch(orientation) {
				case UP:
					blockPos = new Point(position.getX(), position.getY() + i, position.getZ());
					break;
				case DOWN:
					blockPos = new Point(position.getX(), position.getY() - i, position.getZ());
					break;
				case NORTH:
					blockPos = new Point(position.getX(), position.getY(), position.getZ() + i);
					break;
				case SOUTH:
					blockPos = new Point(position.getX(), position.getY(), position.getZ() - i);
					break;
				case WEST:
					blockPos = new Point(position.getX() + i, position.getY(), position.getZ());
					break;
				case EAST:
					blockPos = new Point(position.getX() - i, position.getY(), position.getZ());
					break;
			}
			
			new Boxel(pattern[c], blockPos).toCSVFile(directory);
			if(++c > pattern.length - 1) c = 0;
		}
		return length;
	}
	
	@Override
	public int getNumberOfBlocks() {
		return length;
	}
	
}
