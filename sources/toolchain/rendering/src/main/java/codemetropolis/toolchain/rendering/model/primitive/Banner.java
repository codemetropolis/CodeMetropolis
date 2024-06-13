package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Banner implements Primitive {

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
	private BasicBlock block;
	private Orientation orientation;
	private String color;

	public Banner(BasicBlock block, Orientation orientation, Point p) {
		super();
		this.position = p;
		this.orientation = orientation;
		this.block = block;
        this.color = Integer.toString(getBannerColor(block.getStringId()));
	}

	private int getBannerColor(String blockType) {
        return switch (blockType) {
            case "minecraft:white_banner", "minecraft:white_wall_banner" -> 15;
            case "minecraft:orange_banner", "minecraft:orange_wall_banner" -> 14;
            case "minecraft:magenta_banner", "minecraft:magenta_wall_banner" -> 13;
            case "minecraft:light_blue_banner", "minecraft:light_blue_wall_banner" -> 12;
            case "minecraft:yellow_banner", "minecraft:yellow_wall_banner" -> 11;
            case "minecraft:lime_banner", "minecraft:lime_wall_banner" -> 10;
            case "minecraft:pink_banner", "minecraft:pink_wall_banner" -> 9;
            case "minecraft:gray_banner", "minecraft:gray_wall_banner" -> 8;
            case "minecraft:light_gray_banner", "minecraft:light_gray_wall_banner" -> 7;
            case "minecraft:cyan_banner", "minecraft:cyan_wall_banner" -> 6;
            case "minecraft:purple_banner", "minecraft:purple_wall_banner" -> 5;
            case "minecraft:blue_banner", "minecraft:blue_wall_banner" -> 4;
            case "minecraft:brown_banner", "minecraft:brown_wall_banner" -> 3;
            case "minecraft:green_banner", "minecraft:green_wall_banner" -> 2;
            case "minecraft:red_banner", "minecraft:red_wall_banner" -> 1;
            case "minecraft:black_banner", "minecraft:black_wall_banner" -> 0;
			default -> -1;
        };
	}


	@Override
	public int toCSVFile(File directory) {

		Map<String, String> properties = new HashMap<>();
		properties.put("facing", Integer.toString(this.orientation.getValue()));
		properties.put("bannerColor", color);
		this.block.setProperties(properties);
		new Boxel(this.block, position).toCSVFile(directory);
		return 1;
	}

	@Override
	public int getNumberOfBlocks() {
		return 1;
	}
}
