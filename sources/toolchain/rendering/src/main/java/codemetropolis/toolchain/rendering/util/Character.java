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
			case STONE: return BasicBlock.STONE;
			case COBBLESTONE: return BasicBlock.COBBLESTONE;
			case MOSSY_STONE: return BasicBlock.MOSSY_COBBLESTONE;
			case SANDSTONE: return BasicBlock.SANDSTONE;
			case OBSIDIAN: return BasicBlock.OBSIDIAN;
			case WOOD: return BasicBlock.OAK_WOOD;
			case DARK_WOOD: return BasicBlock.DARK_OAK_WOOD;
			case BIRCH_WOOD: return BasicBlock.BIRCH_WOOD;
			case PLANKS: return BasicBlock.OAK_PLANKS;
			case DARK_PLANKS: return BasicBlock.DARK_OAK_PLANKS;
			case METAL: return BasicBlock.IRON_BLOCK;
			case DIRT: return BasicBlock.DIRT;
			case SAND: return BasicBlock.CUT_SANDSTONE;
			case RED_SAND: return BasicBlock.RED_SAND;
			case BRICK: return BasicBlock.BRICK_BLOCK;
			case STONE_BRICK, DARK_BRICK: return BasicBlock.STONE_BRICKS;
            case GLASS: return BasicBlock.GLASS;
			case GOLD: return BasicBlock.GOLD_BLOCK;
			case DIAMOND: return BasicBlock.DIAMOND_BLOCK;
			case UNDEFINED: return BasicBlock.WHITE_WOOL;
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
