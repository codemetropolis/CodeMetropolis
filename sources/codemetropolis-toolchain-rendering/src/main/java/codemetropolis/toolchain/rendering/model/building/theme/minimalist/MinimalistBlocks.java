package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;

/**
 * Collection of the used blocks in the {@link Themes#MINIMALIST} theme.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class MinimalistBlocks {

	public final static BasicBlock GARDEN = new BasicBlock("minecraft:sand", 1);
	public final static BasicBlock FENCE = new BasicBlock((short) 192);
	public final static BasicBlock EMPTY_BLOCK = BasicBlock.NonBlock;
	public final static BasicBlock BASE = new BasicBlock((short) 98);
	public final static BasicBlock PILLAR = new BasicBlock("minecraft:bedrock");
	public final static BasicBlock WALL = new BasicBlock((short) 20);

}
