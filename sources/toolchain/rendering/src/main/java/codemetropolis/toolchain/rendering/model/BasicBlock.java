package codemetropolis.toolchain.rendering.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BasicBlock {

	// Blocks
	public static final BasicBlock NON_BLOCK = new BasicBlock("",(short) -1);
	public static final BasicBlock AIR = new BasicBlock("minecraft:air",(short) 0);
	public static final BasicBlock STONE = new BasicBlock("minecraft:stone",(short) 1);
	public static final BasicBlock STONE_BRICKS = new BasicBlock("minecraft:stone_bricks",(short) 98);
	public static final BasicBlock SANDSTONE = new BasicBlock("minecraft:sandstone",(short) 24);
	public static final BasicBlock GRASS_BLOCK = new BasicBlock("minecraft:grass_block",(short) 2);
	public static final BasicBlock REDSTONE_BLOCK = new BasicBlock("minecraft:redstone_block",(short) 152);
	public static final BasicBlock REDSTONE_LAMP = new BasicBlock("minecraft:lit_redstone_lamp",(short) 124,
			new HashMap<>() {{put("lit", "true");}});

	// Items
	public static final BasicBlock FENCE = new BasicBlock("minecraft:oak_fence",(short) 85);
	public static final BasicBlock TORCH = new BasicBlock("minecraft:wall_torch",(short) 50);
	public static final BasicBlock BANNER = new BasicBlock("minecraft:white_banner",(short) 176);
	public static final BasicBlock DOOR = new BasicBlock("minecraft:oak_door",(short) 64);
	public static final BasicBlock SIGN = new BasicBlock("minecraft:sign",(short) 63);
	public static final BasicBlock WALL_SIGN = new BasicBlock("minecraft:wall_sign",(short) 68);

	// Plants
	public static final BasicBlock POPPY = new BasicBlock("minecraft:poppy",(short) 38);
	public static final BasicBlock DANDELION = new BasicBlock("minecraft:dandelion",(short) 37);
	public static final BasicBlock BROWN_MUSHROOM = new BasicBlock("minecraft:brown_mushroom",(short) 39);
	public static final BasicBlock OAK_SAPLING = new BasicBlock("minecraft:oak_sapling",(short) 6);

	// Wools
	public static final BasicBlock RED_WOOL = new BasicBlock("minecraft:red_wool",(short) 35,
			new HashMap<>() {{put("color", "14");}});
	public static final BasicBlock GREEN_WOOL = new BasicBlock("minecraft:green_wool", (short) 35,
			new HashMap<>() {{put("color", "13");}});
	public static final BasicBlock BLUE_WOOL = new BasicBlock("minecraft:blue_wool",(short) 35,
			new HashMap<>() {{put("color", "3");}});// light blue
	public static final BasicBlock YELLOW_WOOL = new BasicBlock("minecraft:yellow_wool",(short) 35,
			new HashMap<>() {{put("color", "4");}});
	public static final BasicBlock MAGENTA_WOOL = new BasicBlock("minecraft:magenta_wool",(short) 35,
			new HashMap<>() {{put("color", "2");}});
	public static final BasicBlock PURPLE_WOOL = new BasicBlock("minecraft:purple_wool",(short) 35,
			new HashMap<>() {{put("color", "10");}});
	public static final BasicBlock BLACK_WOOL = new BasicBlock("minecraft:black_wool",(short) 35,
			new HashMap<>() {{put("color", "15");}});

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
