package codemetropolis.toolchain.rendering.util;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.Colour;

public abstract class Character {
	protected static BasicBlock _fallback;
	protected static BasicBlock _topFallback;
	
	static{
		_fallback = new BasicBlock( (short)35, Colour.MAGENTA.getValue(), "minecraft:wool", "Magenta Wool", (short)0);
		_topFallback = new BasicBlock( (short)85, 0, "minecraft:fence", "Fence", (short)0);
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
			switch (block.getId()){
				// TODO Add more fences in the .csv
				case 5:
				case 17:
					return BasicBlock.userBlock("fence", fallback);
				case 1:
				case 43:
				case 44:
					return BasicBlock.userBlock("cobblestone wall", fallback);
				case 48:
					return BasicBlock.userBlock("mossy cobblestone wall", fallback);
				case 112:
					return BasicBlock.userBlock("nether brick fence", fallback);
				case 98:
					if (block.getData() == 1)
						return BasicBlock.userBlock("mossy cobblestone wall", fallback);
					return BasicBlock.userBlock("cobblestone wall", fallback);
			}
		}
		return fallback;
	}
}
