package codemetropolis.toolchain.rendering.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BasicBlock {

	// Blocks
	public static final BasicBlock NON_BLOCK = new BasicBlock("", (short) -1);
	public static final BasicBlock AIR = new BasicBlock("minecraft:air", (short) 0);
	public static final BasicBlock STONE = new BasicBlock("minecraft:stone", (short) 1);
	public static final BasicBlock COBBLESTONE = new BasicBlock("minecraft:cobblestone", (short) 4);
	public static final BasicBlock MOSSY_COBBLESTONE = new BasicBlock("minecraft:mossy_cobblestone", (short) 48);
	public static final BasicBlock OBSIDIAN = new BasicBlock("minecraft:obsidian", (short) 49);
	public static final BasicBlock OAK_WOOD = new BasicBlock("minecraft:oak_wood", (short) 17);
	public static final BasicBlock DARK_OAK_WOOD = BasicBlock.createWithProperties("minecraft:dark_oak_wood", (short) 162, Map.of("type", "1"));
	public static final BasicBlock BIRCH_WOOD = BasicBlock.createWithProperties("minecraft:birch_wood", (short) 17, Map.of("type", "2"));
	public static final BasicBlock OAK_PLANKS = new BasicBlock("minecraft:oak_planks", (short) 5);
	public static final BasicBlock DARK_OAK_PLANKS = BasicBlock.createWithProperties("minecraft:dark_oak_planks", (short) 5, Map.of("type", "5"));
	public static final BasicBlock IRON_BLOCK = new BasicBlock("minecraft:iron_block", (short) 42);
	public static final BasicBlock DIRT = new BasicBlock("minecraft:dirt", (short) 3);
	public static final BasicBlock CUT_SANDSTONE = BasicBlock.createWithProperties("minecraft:cut_sandstone", (short) 24, Map.of("type", "2"));
	public static final BasicBlock RED_SAND = BasicBlock.createWithProperties("minecraft:red_sand", (short) 12, Map.of("type", "1"));
	public static final BasicBlock BRICK_BLOCK = new BasicBlock("minecraft:bricks", (short) 45);
	public static final BasicBlock GLASS = new BasicBlock("minecraft:glass", (short) 20);
	public static final BasicBlock GOLD_BLOCK = new BasicBlock("minecraft:gold_block", (short) 41);
	public static final BasicBlock DIAMOND_BLOCK = new BasicBlock("minecraft:diamond_block", (short) 57);
	public static final BasicBlock STONE_BRICKS = new BasicBlock("minecraft:stone_bricks", (short) 98);
	public static final BasicBlock SANDSTONE = new BasicBlock("minecraft:sandstone", (short) 24);
	public static final BasicBlock GRASS_BLOCK = new BasicBlock("minecraft:grass_block", (short) 2);
	public static final BasicBlock REDSTONE_BLOCK = new BasicBlock("minecraft:redstone_block", (short) 152);
	public static final BasicBlock REDSTONE_LAMP = BasicBlock.createWithProperties("minecraft:lit_redstone_lamp", (short) 124, Map.of("lit", "true"));

	// Items
	public static final BasicBlock FENCE = new BasicBlock("minecraft:oak_fence", (short) 85);
	public static final BasicBlock TORCH = new BasicBlock("minecraft:wall_torch", (short) 50);
	public static final BasicBlock BANNER = new BasicBlock("minecraft:white_banner", (short) 176);
	public static final BasicBlock DOOR = new BasicBlock("minecraft:oak_door", (short) 64);
	public static final BasicBlock SIGN = new BasicBlock("minecraft:oak_sign", (short) 63);
	public static final BasicBlock WALL_SIGN = new BasicBlock("minecraft:wall_sign", (short) 68);
	public static final BasicBlock CHEST = new BasicBlock("minecraft:chest", (short) 54);
	public static final BasicBlock MOB_SPAWNER = new BasicBlock("minecraft:mob_spawner", (short) 52);

	// Plants
	public static final BasicBlock POPPY = new BasicBlock("minecraft:poppy", (short) 38);
	public static final BasicBlock DANDELION = new BasicBlock("minecraft:dandelion", (short) 37);
	public static final BasicBlock BROWN_MUSHROOM = new BasicBlock("minecraft:brown_mushroom", (short) 39);
	public static final BasicBlock OAK_SAPLING = new BasicBlock("minecraft:oak_sapling", (short) 6);

	// Wools
	public static final BasicBlock WHITE_WOOL = BasicBlock.createWithProperties("minecraft:white_wool", (short) 35, Map.of("color", "0"));
	public static final BasicBlock ORANGE_WOOL = BasicBlock.createWithProperties("minecraft:orange_wool", (short) 35, Map.of("color", "1"));
	public static final BasicBlock MAGENTA_WOOL = BasicBlock.createWithProperties("minecraft:magenta_wool", (short) 35, Map.of("color", "2"));
	public static final BasicBlock LIGHT_BLUE_WOOL = BasicBlock.createWithProperties("minecraft:light_blue_wool", (short) 35, Map.of("color", "3"));
	public static final BasicBlock YELLOW_WOOL = BasicBlock.createWithProperties("minecraft:yellow_wool", (short) 35, Map.of("color", "4"));
	public static final BasicBlock LIME_WOOL = BasicBlock.createWithProperties("minecraft:lime_wool", (short) 35, Map.of("color", "5"));
	public static final BasicBlock PINK_WOOL = BasicBlock.createWithProperties("minecraft:pink_wool", (short) 35, Map.of("color", "6"));
	public static final BasicBlock GRAY_WOOL = BasicBlock.createWithProperties("minecraft:gray_wool", (short) 35, Map.of("color", "7"));
	public static final BasicBlock LIGHT_GRAY_WOOL = BasicBlock.createWithProperties("minecraft:light_gray_wool", (short) 35, Map.of("color", "8"));
	public static final BasicBlock CYAN_WOOL = BasicBlock.createWithProperties("minecraft:cyan_wool", (short) 35, Map.of("color", "9"));
	public static final BasicBlock PURPLE_WOOL = BasicBlock.createWithProperties("minecraft:purple_wool", (short) 35, Map.of("color", "10"));
	public static final BasicBlock BLUE_WOOL = BasicBlock.createWithProperties("minecraft:blue_wool", (short) 35, Map.of("color", "11"));
	public static final BasicBlock BROWN_WOOL = BasicBlock.createWithProperties("minecraft:brown_wool", (short) 35, Map.of("color", "12"));
	public static final BasicBlock GREEN_WOOL = BasicBlock.createWithProperties("minecraft:green_wool", (short) 35, Map.of("color", "13"));
	public static final BasicBlock RED_WOOL = BasicBlock.createWithProperties("minecraft:red_wool", (short) 35, Map.of("color", "14"));
	public static final BasicBlock BLACK_WOOL = BasicBlock.createWithProperties("minecraft:black_wool", (short) 35, Map.of("color", "15"));

	// Banners
	public static final BasicBlock WHITE_BANNER = new BasicBlock("minecraft:white_banner", (short) 176);
	public static final BasicBlock ORANGE_BANNER = new BasicBlock("minecraft:orange_banner", (short) 176);
	public static final BasicBlock MAGENTA_BANNER = new BasicBlock("minecraft:magenta_banner", (short) 176);
	public static final BasicBlock LIGHT_BLUE_BANNER = new BasicBlock("minecraft:light_blue_banner", (short) 176);
	public static final BasicBlock YELLOW_BANNER = new BasicBlock("minecraft:yellow_banner", (short) 176);
	public static final BasicBlock LIME_BANNER = new BasicBlock("minecraft:lime_banner", (short) 176);
	public static final BasicBlock PINK_BANNER = new BasicBlock("minecraft:pink_banner", (short) 176);
	public static final BasicBlock GRAY_BANNER = new BasicBlock("minecraft:gray_banner", (short) 176);
	public static final BasicBlock LIGHT_GRAY_BANNER = new BasicBlock("minecraft:light_gray_banner", (short) 176);
	public static final BasicBlock CYAN_BANNER = new BasicBlock("minecraft:cyan_banner", (short) 176);
	public static final BasicBlock PURPLE_BANNER = new BasicBlock("minecraft:purple_banner", (short) 176);
	public static final BasicBlock BLUE_BANNER = new BasicBlock("minecraft:blue_banner", (short) 176);
	public static final BasicBlock BROWN_BANNER = new BasicBlock("minecraft:brown_banner", (short) 176);
	public static final BasicBlock GREEN_BANNER = new BasicBlock("minecraft:green_banner", (short) 176);
	public static final BasicBlock RED_BANNER = new BasicBlock("minecraft:red_banner", (short) 176);
	public static final BasicBlock BLACK_BANNER = new BasicBlock("minecraft:black_banner", (short) 176);
	public static final BasicBlock WHITE_WALL_BANNER = new BasicBlock("minecraft:white_wall_banner", (short) 177);
	public static final BasicBlock ORANGE_WALL_BANNER = new BasicBlock("minecraft:orange_wall_banner", (short) 177);
	public static final BasicBlock MAGENTA_WALL_BANNER = new BasicBlock("minecraft:magenta_wall_banner", (short) 177);
	public static final BasicBlock LIGHT_BLUE_WALL_BANNER = new BasicBlock("minecraft:light_blue_wall_banner", (short) 177);
	public static final BasicBlock YELLOW_WALL_BANNER = new BasicBlock("minecraft:yellow_wall_banner", (short) 177);
	public static final BasicBlock LIME_WALL_BANNER = new BasicBlock("minecraft:lime_wall_banner", (short) 177);
	public static final BasicBlock PINK_WALL_BANNER = new BasicBlock("minecraft:pink_wall_banner", (short) 177);
	public static final BasicBlock GRAY_WALL_BANNER = new BasicBlock("minecraft:gray_wall_banner", (short) 177);
	public static final BasicBlock LIGHT_GRAY_WALL_BANNER = new BasicBlock("minecraft:light_gray_wall_banner", (short) 177);
	public static final BasicBlock CYAN_WALL_BANNER = new BasicBlock("minecraft:cyan_wall_banner", (short) 177);
	public static final BasicBlock PURPLE_WALL_BANNER = new BasicBlock("minecraft:purple_wall_banner", (short) 177);
	public static final BasicBlock BLUE_WALL_BANNER = new BasicBlock("minecraft:blue_wall_banner", (short) 177);
	public static final BasicBlock BROWN_WALL_BANNER = new BasicBlock("minecraft:brown_wall_banner", (short) 177);
	public static final BasicBlock GREEN_WALL_BANNER = new BasicBlock("minecraft:green_wall_banner", (short) 177);
	public static final BasicBlock RED_WALL_BANNER = new BasicBlock("minecraft:red_wall_banner", (short) 177);
	public static final BasicBlock BLACK_WALL_BANNER = new BasicBlock("minecraft:black_wall_banner", (short) 177);

	private String stringId;
	private short shortId;
	private Map<String, String> properties = Collections.emptyMap();

	public BasicBlock(String stringId) {
		this.stringId = stringId;
	}

	public BasicBlock(short shortId) {
		this.shortId = shortId;
	}

	public BasicBlock(String stringId, short shortId) {
		this.stringId = stringId;
		this.shortId = shortId;
	}

	public BasicBlock(String stringId, Map<String, String> properties) {
		this.stringId = stringId;
		this.properties = properties;
	}

	public BasicBlock(String stringId, short shortId, Map<String, String> properties) {
		this.stringId = stringId;
		this.shortId = shortId;
		this.properties = properties;
	}

	public BasicBlock(BasicBlock original) {
		this.stringId = original.stringId;
		this.shortId = original.getShortId();
		this.properties = new HashMap<>(original.properties);
	}

	public static BasicBlock createWithProperties(String id, short legacyId, Map<String, String> properties) {
		return new BasicBlock(id, legacyId, properties);
	}

	public Map<String, String> getProperties(){
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getStringId() {
		return stringId;
	}

	public short getShortId(){
		return shortId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stringId == null) ? 0 : stringId.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicBlock other = (BasicBlock) obj;
		if (stringId == null) {
			if (other.stringId != null)
				return false;
		} else if (!stringId.equals(other.stringId))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicBlock [id=" + stringId + ", properties=" + properties + "]";
	}
	
}
