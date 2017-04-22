package codemetropolis.toolchain.rendering.model.building.theme.railway;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;

/**
 * Collection of the used blocks in the {@link Themes#RAILWAY} theme.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayBlocks {

	public final static BasicBlock EMPTY_BLOCK = BasicBlock.NonBlock;
	public final static BasicBlock RAIL_DIRT = new BasicBlock("minecraft:dirt", 1);
	public final static BasicBlock RAIL_WOOD = new BasicBlock((short) 5);
	public final static BasicBlock RAIL_IRON = new BasicBlock((short) 42);
	public final static BasicBlock GATE = new BasicBlock("minecraft:planks", 5);
	public final static BasicBlock WAGON_WALL = new BasicBlock("minecraft:stained_glass", 11);
	public final static BasicBlock WAGON_CHIMNEY = new BasicBlock("minecraft:wool", 7);
	public final static BasicBlock[] WAGON_PILLARS = {
		new BasicBlock((short) 22),
		new BasicBlock((short) 152),
		new BasicBlock((short) 41),
		new BasicBlock((short) 133)
	};

}
