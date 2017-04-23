package codemetropolis.toolchain.rendering.util;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.Colour;
import java.util.Map;

public abstract class Character {
	
	protected static Map<String, Block> blocks;
	
	public static void init(Map<String, Block> blocks){
		Character.blocks = blocks;
	}
	
	public static BasicBlock getBlock(String str){
		Block block;
		if (blocks.containsKey(str) && blocks.get(str).getHazardous() != 2){
			System.out.println("Using \"" + str + "\"");
			block = blocks.get(str);
		}
		else{
			System.out.println("Couldn't find block \"" + str + "\", using magenta wool instead.");
			block = blocks.get("wool");
		}
		return new BasicBlock(block.getId(), Colour.MAGENTA);
	}
	
	public static BasicBlock getTopBlock(String str){
		if (blocks.containsKey(str) && blocks.get(str).getHazardous() != 2){
			Block block = blocks.get(str);
			switch (block.getId()){
				// TODO Add more fences in the .csv
				case 5:
				case 17:
					return new BasicBlock((short)85);
				case 1:
				case 43:
				case 44:
					return new BasicBlock((short)139);
				case 48:
					return new BasicBlock((short)139, (short)1);
				case 112:
					return new BasicBlock((short)113);
			}
		}
		return getBlock(str);
	}
}
