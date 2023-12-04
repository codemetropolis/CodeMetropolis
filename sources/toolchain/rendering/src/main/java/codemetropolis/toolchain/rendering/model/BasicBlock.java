package codemetropolis.toolchain.rendering.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BasicBlock {

	public static final BasicBlock NON_BLOCK = new BasicBlock("");
	public static final BasicBlock Mob = new BasicBlock("minecraft:pig_place");

	// Blocks
	public static final BasicBlock AIR = new BasicBlock("minecraft:air");
	public static final BasicBlock STONE = new BasicBlock("minecraft:stone");
	public static final BasicBlock DIRT = new BasicBlock("minecraft:dirt");
	public static final BasicBlock NETHERRACK = new BasicBlock("minecraft:netherrack");
	public static final BasicBlock IRONBAR = new BasicBlock("minecraft:iron_bars");
	public static final BasicBlock FIRE = new BasicBlock("minecraft:fire");
	public static final BasicBlock STONE_BRICKS = new BasicBlock("minecraft:stone_bricks");
	public static final BasicBlock IRON_BLOCK = new BasicBlock("minecraft:iron_block");
	public static final BasicBlock GLASS = new BasicBlock("minecraft:glass");
	public static final BasicBlock LOG = new BasicBlock("minecraft:oak_log");
	public static final BasicBlock COBBLESTONE = new BasicBlock("minecraft:cobblestone");
	public static final BasicBlock OBSIDIAN = new BasicBlock("minecraft:obsidian");
	public static final BasicBlock SAND = new BasicBlock("minecraft:cut_sandstone");
	public static final BasicBlock ORIGINAL_SAND = new BasicBlock("minecraft:sand");
	public static final BasicBlock PLANKS = new BasicBlock("minecraft:oak_planks");
	public static final BasicBlock SANDSTONE = new BasicBlock("minecraft:sandstone");
	public static final BasicBlock GRASS_BLOCK = new BasicBlock("minecraft:grass_block");
	public static final BasicBlock REDSTONE_BLOCK = new BasicBlock("minecraft:redstone_block");
	public static final BasicBlock BEDROCK = new BasicBlock("minecraft:bedrock");
	public static final BasicBlock LAPIS = new BasicBlock("minecraft:lapis_block");
	public static final BasicBlock BLUEGLASS = new BasicBlock("minecraft:blue_stained_glass");
	public static final BasicBlock WHITEGLASS = new BasicBlock("minecraft:white_stained_glass");
	public static final BasicBlock GOLDBLOCK = new BasicBlock("minecraft:gold_block");
	public static final BasicBlock EMERALDBLOCK = new BasicBlock("minecraft:emerald_block");
	public static final BasicBlock BRICKS = new BasicBlock("minecraft:bricks");
	public static final BasicBlock NETHERBRICKS = new BasicBlock("minecraft:nether_bricks");
	public static final BasicBlock GRASS_PATH = new BasicBlock("minecraft:grass_path");
	public static final BasicBlock COBBLESTONE_WALL = new BasicBlock("minecraft:cobblestone_wall");
	public static final BasicBlock REDSTONE_LAMP = new BasicBlock("minecraft:redstone_lamp",
			new HashMap<String, String>() {
				{
					put("lit", "true");
				}
			});
	public static final BasicBlock BRICKSTAIRS_NORTH = new BasicBlock("minecraft:brick_stairs",
			new HashMap<String, String>() {
				{
					put("facing", "north");
				}
			});
	public static final BasicBlock BRICKSTAIRS_SOUTH = new BasicBlock("minecraft:brick_stairs",
			new HashMap<String, String>() {
				{
					put("facing", "south");
				}
			});

	// Items
	public static final BasicBlock FENCE = new BasicBlock("minecraft:oak_fence");
	public static final BasicBlock ACACIA_FENCE = new BasicBlock("minecraft:acacia_fence");
	public static final BasicBlock TORCH = new BasicBlock("minecraft:wall_torch");
	public static final BasicBlock BANNER = new BasicBlock("minecraft:white_banner");
	public static final BasicBlock DOOR = new BasicBlock("minecraft:oak_door");
	public static final BasicBlock SIGN = new BasicBlock("minecraft:sign");
	public static final BasicBlock WALL_SIGN = new BasicBlock("minecraft:wall_sign");

	// Plants
	public static final BasicBlock POPPY = new BasicBlock("minecraft:poppy");
	public static final BasicBlock DANDELION = new BasicBlock("minecraft:dandelion");
	public static final BasicBlock BROWN_MUSHROOM = new BasicBlock("minecraft:brown_mushroom");
	public static final BasicBlock OAK_SAPLING = new BasicBlock("minecraft:oak_sapling");

	// Wools
	public static final BasicBlock RED_WOOL = new BasicBlock("minecraft:red_wool");
	public static final BasicBlock GREENWOOL = new BasicBlock("minecraft:lime_wool");
	public static final BasicBlock BLUE_WOOL = new BasicBlock("minecraft:light_blue_wool");
	public static final BasicBlock YELLOW_WOOL = new BasicBlock("minecraft:yellow_wool");
	public static final BasicBlock MAGENTA_WOOL = new BasicBlock("minecraft:magenta_wool");
	public static final BasicBlock PURPLE_WOOL = new BasicBlock("minecraft:purple_wool");
	public static final BasicBlock BLACK_WOOL = new BasicBlock("minecraft:black_wool");
	public static final BasicBlock GRAY_WOOL = new BasicBlock("minecraft:gray_wool");
	public static final BasicBlock LIGHT_GRAY_WOOL = new BasicBlock("minecraft:light_gray_wool");
	public static final BasicBlock ORANGE_WOOL = new BasicBlock("minecraft:orange_gray_wool");
	public static final BasicBlock WHITE_WOOL = new BasicBlock("minecraft:white_gray_wool");
	public static final BasicBlock PINK_WOOL = new BasicBlock("minecraft:pink_gray_wool");
	private String id;
	private Map<String, String> properties = Collections.emptyMap();

	public BasicBlock(String id) {
		this.id = id;
	}

	public BasicBlock(String id, Map<String, String> properties) {
		this.id = id;
		this.properties = properties;
	}

	public BasicBlock(BasicBlock original) {
		this.id = original.id;
		this.properties = new HashMap<>(original.properties);
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "BasicBlock [id=" + id + ", properties=" + properties + "]";
	}

}
