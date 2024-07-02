package codemetropolis.toolchain.rendering.util;

import codemetropolis.toolchain.commons.model.BlockType;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public enum Character {
	/*STONE(BasicBlock.BasicBlockType.STONE),
	COBBLESTONE(BasicBlock.BasicBlockType.COBBLESTONE),
	MOSSY_STONE(BasicBlock.BasicBlockType.MOSSY_COBBLESTONE),
	SANDSTONE(BasicBlock.BasicBlockType.SANDSTONE),
	OBSIDIAN(BasicBlock.BasicBlockType.OBSIDIAN),
	WOOD(BasicBlock.BasicBlockType.OAK_WOOD, BasicBlock.BasicBlockType.FENCE),
	DARK_WOOD(BasicBlock.BasicBlockType.DARK_OAK_WOOD, BasicBlock.BasicBlockType.FENCE),
	BIRCH_WOOD(BasicBlock.BasicBlockType.BIRCH_WOOD, BasicBlock.BasicBlockType.FENCE),
	PLANKS(BasicBlock.BasicBlockType.OAK_PLANKS, BasicBlock.BasicBlockType.FENCE),
	DARK_PLANKS(BasicBlock.BasicBlockType.DARK_OAK_PLANKS, BasicBlock.BasicBlockType.FENCE),
	METAL(BasicBlock.BasicBlockType.IRON_BLOCK),
	DIRT(BasicBlock.BasicBlockType.DIRT),
	SAND(BasicBlock.BasicBlockType.CUT_SANDSTONE),
	RED_SAND(BasicBlock.BasicBlockType.RED_SAND),
	BRICK(BasicBlock.BasicBlockType.BRICK_BLOCK),
	STONE_BRICK(BasicBlock.BasicBlockType.STONE_BRICKS),
	DARK_BRICK(BasicBlock.BasicBlockType.STONE_BRICKS),
	GLASS(BasicBlock.BasicBlockType.GLASS),
	GOLD(BasicBlock.BasicBlockType.GOLD_BLOCK),
	DIAMOND(BasicBlock.BasicBlockType.DIAMOND_BLOCK),
	UNDEFINED(BasicBlock.BasicBlockType.WHITE_WOOL);

	private final BlockType block;
	private final BlockType topBlock;

	Character(BasicBlock.BasicBlockType block) {
		this(block, block);
	}

	Character(BasicBlock.BasicBlockType block, BasicBlock.BasicBlockType topBlock) {
		this.block = block.getBlock();
		this.topBlock = topBlock.getBlock();
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
	}*/
}
