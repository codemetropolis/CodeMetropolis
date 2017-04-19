package codemetropolis.toolchain.rendering.model.building.theme.railway;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;

/**
 * Collection of the used blocks in the {@link Themes#RAILWAY} theme.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayBlocks {
	public final static BasicBlock EMPTY_BLOCK = new BasicBlock( (short) 0 );
	public final static BasicBlock RAIL_STONE = new BasicBlock( "minecraft:dirt", 1);
	public final static BasicBlock RAIL_WOOD = new BasicBlock( (short) 5 );
	public final static BasicBlock RAIL_IRON = new BasicBlock( (short) 42 );
	public final static BasicBlock WAGON_PILLAR_1 = new BasicBlock( (short) 22 );
	public final static BasicBlock WAGON_PILLAR_2 = new BasicBlock( (short) 152 );
	public final static BasicBlock WAGON_PILLAR_3 = new BasicBlock( (short) 41 );
	public final static BasicBlock WAGON_PILLAR_4 = new BasicBlock( (short) 133 );
	public final static BasicBlock WAGON_WALL = new BasicBlock( "minecraft:stained_glass", 11 );
	public final static BasicBlock WAGON_CHIMNEY = new BasicBlock( "minecraft:wool", 7 );
	public final static BasicBlock GATE = new BasicBlock( "minecraft:planks", 5 );
}
