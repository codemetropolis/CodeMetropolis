package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		//todo: mindegyik boxel blockba a json megoldast atalakitani, hogy egy mapa konvertalja az extra infot es azt adja at a boxelnek
		String jsonString = convertMapToJson(wallSignText);
		assertJsonString(jsonString);

		new Boxel(new BasicBlock((short) 68, orientation.getValue()), position, jsonString).toCSVFile(directory);
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

	/**
	 * This method checks if the string that contains spawner data contains ; character, if it does, it throws an
	 * exception
	 * @param jsonString the string that contains spawner data and need to be checked for ; character
	 */
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
