package codemetropolis.toolchain.rendering.util;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.Colour;

public abstract class Character{
	protected static BasicBlock	_fallback;
	protected static BasicBlock	_topFallback;
	
	static{
		_fallback = BasicBlock.get("minecraft:wool", Colour.MAGENTA.getValue());
		_topFallback = BasicBlock.get("minecraft:fence", 0);
	}
	
	public static BasicBlock getBlock(String str, BasicBlock fallback){
		return BasicBlock.userBlock(str, fallback);
	}
	
	public static BasicBlock getBlock(String str){
		return getBlock(str, _fallback);
	}
	
	public static BasicBlock getTopBlock(String str){
		return getTopBlock(str, _topFallback);
	}
	
	public static BasicBlock getTopBlock(String str, BasicBlock fallback){
		BasicBlock block = BasicBlock.userBlock(str, fallback);
		if (block != null){
			switch (block.getName()){
				// TODO Add more fences in the .csv
				case "minecraft:planks":			// 5
				case "minecraft:log":				// 17
				case "minecraft:log2":				// 162
					return BasicBlock.get("minecraft:fence");
				case "minecraft:stone":				// 1
				case "minecraft:double_stone_slab":	// 43
				case "minecraft:stone_slab":		// 44
					return BasicBlock.get("minecraft:cobblestone_wall", 0); // Regular cobblestone wall
				case "minecraft:mossy_cobblestone":	// 48
					return BasicBlock.get("minecraft:cobblestone_wall", 1); // Mossy cobblestone wall
				case "minecraft:nether_brick":		// 112
					return BasicBlock.get("minecraft:nether_brick_fence");
				case "minecraft:stonebrick":	// 98
					if (block.getData() == 1)
						return BasicBlock.get("minecraft:cobblestone_wall", 1);	// Mossy cobblestone wall
					return BasicBlock.get("minecraft:cobblestone_wall", 0);		// Regular cobblestone wall
			}
		}
		return fallback;
	}
}
