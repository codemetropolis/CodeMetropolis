package codemetropolis.toolchain.rendering.util;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import java.util.Map;

public abstract class Character {
	
	protected static Map<String, Short> ids;
	
	public static void init(Map<String, Short> ids){
		Character.ids = ids;
	}
	
	public static BasicBlock getBlock(String str){
		if (ids.containsKey(str))
			return new BasicBlock(ids.get(str));
		else
			return new BasicBlock("minecraft:wool", 2);
	}
	
	public static BasicBlock getTopBlock(String str) {
		if (ids.containsKey(str)){
			switch (ids.get(str)){
				case 5:
				case 17:
					return new BasicBlock((short)85);
			}
		}
		return getBlock(str);
	}
}
