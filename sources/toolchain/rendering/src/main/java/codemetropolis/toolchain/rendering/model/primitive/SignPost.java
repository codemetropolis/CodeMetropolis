package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SignPost implements Primitive {
	
	public enum Orientation {
		SOUTH(0),
		SOUTHWEST(2),
		WEST(4),
		NORTHWEST(6),
		NORTH(8),
		NORTHEAST(10),
		EAST(12),
		SOUTHEAST(14);
		
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
	private Map<String, String> signPostText = new HashMap<>();

	public SignPost(int x, int y, int z, Orientation orientation, String text) {
		super();
		this.position = new Point(x, y, z);
		this.orientation = orientation;
		this.signPostText.put("textOnSign", text);
	}
	
	@Override
	public int toCSVFile(File directory) {
		String jsonString = convertMapToJson(signPostText);
		assertJsonString(jsonString);

		new Boxel(new BasicBlock((short) 63, orientation.getValue()), position, jsonString).toCSVFile(directory);
		return 1;
	}

	private static String convertMapToJson(Map<String, String> map) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void assertJsonString(String jsonString) {
		if (jsonString.contains(";")) {
			throw new IllegalArgumentException("Json string cannot contain semicolons! The blocks' individual " +
					"data such as position and block type are separated by semicolons in the csv file. A semicolon in " +
					"the json would break the structure of the csv file.");
		}
	}

	@Override
	public int getNumberOfBlocks() {
		return 1;
	}
	
}
