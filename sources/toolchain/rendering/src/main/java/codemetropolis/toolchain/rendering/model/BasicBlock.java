package codemetropolis.toolchain.rendering.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BasicBlock {

	private String stringId;
	private short shortId;
	private Map<String, String> properties = Collections.emptyMap();

	public BasicBlock(String stringId, short shortId) {
		this.stringId = stringId;
		this.shortId = shortId;
	}

	public BasicBlock(BasicBlock block, Map<String, String> properties) {
		this.stringId = block.getStringId();
		this.shortId = block.getShortId();
		this.properties = properties;
	}

	public BasicBlock(String stringId, short shortId, Map<String, String> properties) {
		this.stringId = stringId;
		this.shortId = shortId;
		this.properties = properties;
	}

	public BasicBlock(BasicBlock original) {
		this.stringId = original.stringId;this.shortId = original.getShortId();
		this.properties = new HashMap<>(original.properties);
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void addProperty(String key, String value){
		properties.put(key, value);
	}

	public void setProperties(Map<String, String> properties) {
			this.properties = properties;
	}

	public String getStringId() {
			return stringId;
		}

	public short getShortId() {
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

	public enum BasicBlockType {

		// Blocks
		NON_BLOCK("", (short) -1),
		AIR("minecraft:air", (short) 0),
		STONE("minecraft:stone", (short) 1),
		COBBLESTONE("minecraft:cobblestone", (short) 4),
		MOSSY_COBBLESTONE("minecraft:mossy_cobblestone", (short) 48),
		OBSIDIAN("minecraft:obsidian", (short) 49),
		OAK_WOOD("minecraft:oak_wood", (short) 17),
		DARK_OAK_WOOD("minecraft:dark_oak_wood", (short) 162, Map.of("type", "1")),
		BIRCH_WOOD("minecraft:birch_wood", (short) 17, Map.of("type", "2")),
		OAK_PLANKS("minecraft:oak_planks", (short) 5),
		DARK_OAK_PLANKS("minecraft:dark_oak_planks", (short) 5, Map.of("type", "5")),
		IRON_BLOCK("minecraft:iron_block", (short) 42),
		DIRT("minecraft:dirt", (short) 3),
		CUT_SANDSTONE("minecraft:cut_sandstone", (short) 24, Map.of("type", "2")),
		RED_SAND("minecraft:red_sand", (short) 12, Map.of("type", "1")),
		BRICK_BLOCK("minecraft:bricks", (short) 45),
		GLASS("minecraft:glass", (short) 20),
		GOLD_BLOCK("minecraft:gold_block", (short) 41),
		DIAMOND_BLOCK("minecraft:diamond_block", (short) 57),
		STONE_BRICKS("minecraft:stone_bricks", (short) 98),
		SANDSTONE("minecraft:sandstone", (short) 24),
		GRASS_BLOCK("minecraft:grass_block", (short) 2),
		REDSTONE_BLOCK("minecraft:redstone_block", (short) 152),
		REDSTONE_LAMP("minecraft:lit_redstone_lamp", (short) 124, Map.of("lit", "true")),

		// Items
		FENCE("minecraft:oak_fence", (short) 85),
		TORCH("minecraft:wall_torch", (short) 50),
		DOOR("minecraft:oak_door", (short) 64),
		SIGN("minecraft:oak_sign", (short) 63),
		WALL_SIGN("minecraft:wall_sign", (short) 68),
		CHEST("minecraft:chest", (short) 54),
		MOB_SPAWNER("minecraft:mob_spawner", (short) 52),

		// Plants
		POPPY("minecraft:poppy", (short) 38),
		DANDELION("minecraft:dandelion", (short) 37),
		BROWN_MUSHROOM("minecraft:brown_mushroom", (short) 39),
		OAK_SAPLING("minecraft:oak_sapling", (short) 6),

		// Wools
		WHITE_WOOL("minecraft:white_wool", (short) 35, Map.of("color", "0")),
		ORANGE_WOOL("minecraft:orange_wool", (short) 35, Map.of("color", "1")),
		MAGENTA_WOOL("minecraft:magenta_wool", (short) 35, Map.of("color", "2")),
		LIGHT_BLUE_WOOL("minecraft:light_blue_wool", (short) 35, Map.of("color", "3")),
		YELLOW_WOOL("minecraft:yellow_wool", (short) 35, Map.of("color", "4")),
		LIME_WOOL("minecraft:lime_wool", (short) 35, Map.of("color", "5")),
		PINK_WOOL("minecraft:pink_wool", (short) 35, Map.of("color", "6")),
		GRAY_WOOL("minecraft:gray_wool", (short) 35, Map.of("color", "7")),
		LIGHT_GRAY_WOOL("minecraft:light_gray_wool", (short) 35, Map.of("color", "8")),
		CYAN_WOOL("minecraft:cyan_wool", (short) 35, Map.of("color", "9")),
		PURPLE_WOOL("minecraft:purple_wool", (short) 35, Map.of("color", "10")),
		BLUE_WOOL("minecraft:blue_wool", (short) 35, Map.of("color", "11")),
		BROWN_WOOL("minecraft:brown_wool", (short) 35, Map.of("color", "12")),
		GREEN_WOOL("minecraft:green_wool", (short) 35, Map.of("color", "13")),
		RED_WOOL("minecraft:red_wool", (short) 35, Map.of("color", "14")),
		BLACK_WOOL("minecraft:black_wool", (short) 35, Map.of("color", "15")),

		// Banners
		WHITE_BANNER("minecraft:white_banner", (short) 176),
		ORANGE_BANNER("minecraft:orange_banner", (short) 176),
		MAGENTA_BANNER("minecraft:magenta_banner", (short) 176),
		LIGHT_BLUE_BANNER("minecraft:light_blue_banner", (short) 176),
		YELLOW_BANNER("minecraft:yellow_banner", (short) 176),
		LIME_BANNER("minecraft:lime_banner", (short) 176),
		PINK_BANNER("minecraft:pink_banner", (short) 176),
		GRAY_BANNER("minecraft:gray_banner", (short) 176),
		LIGHT_GRAY_BANNER("minecraft:light_gray_banner", (short) 176),
		CYAN_BANNER("minecraft:cyan_banner", (short) 176),
		PURPLE_BANNER("minecraft:purple_banner", (short) 176),
		BLUE_BANNER("minecraft:blue_banner", (short) 176),
		BROWN_BANNER("minecraft:brown_banner", (short) 176),
		GREEN_BANNER("minecraft:green_banner", (short) 176),
		RED_BANNER("minecraft:red_banner", (short) 176),
		BLACK_BANNER("minecraft:black_banner", (short) 176),
		WHITE_WALL_BANNER("minecraft:white_wall_banner", (short) 177),
		ORANGE_WALL_BANNER("minecraft:orange_wall_banner", (short) 177),
		MAGENTA_WALL_BANNER("minecraft:magenta_wall_banner", (short) 177),
		LIGHT_BLUE_WALL_BANNER("minecraft:light_blue_wall_banner", (short) 177),
		YELLOW_WALL_BANNER("minecraft:yellow_wall_banner", (short) 177),
		LIME_WALL_BANNER("minecraft:lime_wall_banner", (short) 177),
		PINK_WALL_BANNER("minecraft:pink_wall_banner", (short) 177),
		GRAY_WALL_BANNER("minecraft:gray_wall_banner", (short) 177),
		LIGHT_GRAY_WALL_BANNER("minecraft:light_gray_wall_banner", (short) 177),
		CYAN_WALL_BANNER("minecraft:cyan_wall_banner", (short) 177),
		PURPLE_WALL_BANNER("minecraft:purple_wall_banner", (short) 177),
		BLUE_WALL_BANNER("minecraft:blue_wall_banner", (short) 177),
		BROWN_WALL_BANNER("minecraft:brown_wall_banner", (short) 177),
		GREEN_WALL_BANNER("minecraft:green_wall_banner", (short) 177),
		RED_WALL_BANNER("minecraft:red_wall_banner", (short) 177),
		BLACK_WALL_BANNER("minecraft:black_wall_banner", (short) 177);

		private final BasicBlock block;

		BasicBlockType(String stringId, short shortId) {
			this.block = new BasicBlock(stringId, shortId);
		}

		BasicBlockType(String stringId, short shortId, Map<String, String> properties) {
			this.block = new BasicBlock(stringId, shortId, properties);
		}

		public BasicBlock getBlock() {
			return block;
		}
	}
}
