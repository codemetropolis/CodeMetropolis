package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.JsonUtil;

public class WallSign implements Primitive {
	
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
	private Map<String, String> wallSignText = new HashMap<>();

	public WallSign(int x, int y, int z, Orientation orientation, String text) {
		this(new Point(x,y,z), orientation, text);
	}
	
	public WallSign(Point position, Orientation orientation, String text) {
		super();
		this.position = position;
		this.orientation = orientation;
		this.wallSignText.put("textOnSign", text);
	}
	
	@Override
	public int toCSVFile(File directory) {
		String jsonString = JsonUtil.convertMapToJson(wallSignText);

		new Boxel(new BasicBlock((short) 68, orientation.getValue()), position, jsonString).toCSVFile(directory);
		return 1;
	}

	@Override
	public int getNumberOfBlocks() {
		return 1;
	}
	
}
