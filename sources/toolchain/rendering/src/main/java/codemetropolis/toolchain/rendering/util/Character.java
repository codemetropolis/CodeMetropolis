package codemetropolis.toolchain.rendering.util;

import codemetropolis.toolchain.rendering.model.BasicBlock;

import java.util.HashMap;

public enum Character {
	
	STONE,
	COBBLESTONE,
	MOSSY_STONE,
	SANDSTONE,
	OBSIDIAN,
	WOOD,
	DARK_WOOD,
	BIRCH_WOOD,
	PLANKS,
	DARK_PLANKS,
	METAL,
	DIRT,
	SAND,
	RED_SAND,
	BRICK,
	STONE_BRICK,
	DARK_BRICK,
	GLASS,
	GOLD,
	DIAMOND,
	UNDEFINED;
	
	public static Character parse(String str) {
		for(Character c : Character.values()) {
			if(c.toString().equalsIgnoreCase(str)) return c;
		}
		return Character.UNDEFINED;
	}
	
	public BasicBlock getBlock() {
		switch(this) {
			case STONE: return new BasicBlock( "minecraft:stone",(short) 1);
			case COBBLESTONE: return new BasicBlock( "minecraft:cobblestone",(short) 4);
			case MOSSY_STONE: return new BasicBlock( "minecraft:mossy_cobblestone",(short) 48);
			case SANDSTONE: return new BasicBlock( "minecraft:sandstone",(short) 24);
			case OBSIDIAN: return new BasicBlock( "minecraft:obsidian",(short) 49);
			case WOOD: return new BasicBlock( "minecraft:oak_wood",(short) 17);
			case DARK_WOOD: return new BasicBlock( "minecraft:dark_oak_wood",(short) 162);
			case BIRCH_WOOD: return new BasicBlock( "minecraft:birch_wood",(short) 17,
					new HashMap<>() {{put("type", "2");}});
			case PLANKS: return new BasicBlock( "minecraft:oak_planks",(short) 5);
			case DARK_PLANKS: return new BasicBlock( "minecraft:dark_oak_planks",(short) 5,
					new HashMap<>() {{put("type", "5");}});
			case METAL: return new BasicBlock( "minecraft:iron_block",(short) 42);
			case DIRT: return new BasicBlock( "minecraft:dirt",(short) 3);
			case SAND: return new BasicBlock( "minecraft:cut_sandstone",(short) 24,
					new HashMap<>() {{put("type", "2");}});
			case RED_SAND: return new BasicBlock( "minecraft:red_sand",(short) 12,
					new HashMap<>() {{put("type", "1");}});
			case BRICK: return new BasicBlock( "minecraft:bricks",(short) 45);
			case STONE_BRICK, DARK_BRICK: return new BasicBlock( "minecraft:stone_bricks",(short) 98);
            case GLASS: return new BasicBlock( "minecraft:glass",(short) 20);
			case GOLD: return new BasicBlock( "minecraft:gold_block",(short) 41);
			case DIAMOND: return new BasicBlock( "minecraft:diamond_block",(short) 57);
			case UNDEFINED: return new BasicBlock( "minecraft:wool",(short) 35);
			default: return null;
		}
	}
	
	public BasicBlock getTopBlock() {
		switch(this) {
			case WOOD:
			case DARK_WOOD:
			case BIRCH_WOOD:
			case PLANKS:
			case DARK_PLANKS:
				return new BasicBlock( "minecraft:fence" );
			default: return getBlock();
		}
	}
}
