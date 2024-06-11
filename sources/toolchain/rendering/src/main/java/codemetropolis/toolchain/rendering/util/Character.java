package codemetropolis.toolchain.rendering.util;

import codemetropolis.toolchain.rendering.model.BasicBlock;

import java.util.HashMap;
import java.util.Map;

public enum Character {
	STONE(BasicBlock.STONE),
	COBBLESTONE(BasicBlock.COBBLESTONE),
	MOSSY_STONE(BasicBlock.MOSSY_COBBLESTONE),
	SANDSTONE(BasicBlock.SANDSTONE),
	OBSIDIAN(BasicBlock.OBSIDIAN),
	WOOD(BasicBlock.OAK_WOOD, BasicBlock.FENCE),
	DARK_WOOD(BasicBlock.DARK_OAK_WOOD, BasicBlock.FENCE),
	BIRCH_WOOD(BasicBlock.BIRCH_WOOD, BasicBlock.FENCE),
	PLANKS(BasicBlock.OAK_PLANKS, BasicBlock.FENCE),
	DARK_PLANKS(BasicBlock.DARK_OAK_PLANKS, BasicBlock.FENCE),
	METAL(BasicBlock.IRON_BLOCK),
	DIRT(BasicBlock.DIRT),
	SAND(BasicBlock.CUT_SANDSTONE),
	RED_SAND(BasicBlock.RED_SAND),
	BRICK(BasicBlock.BRICK_BLOCK),
	STONE_BRICK(BasicBlock.STONE_BRICKS),
	DARK_BRICK(BasicBlock.STONE_BRICKS),
	GLASS(BasicBlock.GLASS),
	GOLD(BasicBlock.GOLD_BLOCK),
	DIAMOND(BasicBlock.DIAMOND_BLOCK),
	UNDEFINED(BasicBlock.WHITE_WOOL);

	private final BasicBlock block;
	private final BasicBlock topBlock;

	Character(BasicBlock block) {
		this(block, block);
	}

	Character(BasicBlock block, BasicBlock topBlock) {
		this.block = block;
		this.topBlock = topBlock;
	}

	public static Character parse(String str) {
		try {
			return valueOf(str.toUpperCase());
		} catch (IllegalArgumentException e) {
			return UNDEFINED;
		}
	}

	public BasicBlock getBlock() {
		return block;
	}

	public BasicBlock getTopBlock() {
		return topBlock;
	}
}
