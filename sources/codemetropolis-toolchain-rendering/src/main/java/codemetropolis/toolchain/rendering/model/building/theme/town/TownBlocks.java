package codemetropolis.toolchain.rendering.model.building.theme.town;

import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;

/**
 * Collection of the used blocks in the {@link Themes#TOWN} theme.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TownBlocks {
	public final static BasicBlock FENCE = new BasicBlock( (short) 18 );
	public final static BasicBlock EMPTY_BLOCK = new BasicBlock( (short) 0 );
	public final static BasicBlock CELLAR_WALL = new BasicBlock( (short) 45 );
	public final static BasicBlock ROOF = new BasicBlock( (short) 112 );
	public final static BasicBlock ROOF_LEFT= new BasicBlock( "minecraft:nether_brick_stairs", 2 );
	public final static BasicBlock ROOF_RIGHT= new BasicBlock( "minecraft:nether_brick_stairs", 3 );
	public final static BasicBlock WINDOW = new BasicBlock( (short) 95 );
	public final static BasicBlock CELLAR_DOOR = new BasicBlock( (short) 42 );
	public final static BasicBlock WALL_1 = new BasicBlock( "minecraft:wool", 4 );
	public final static BasicBlock WALL_2 = new BasicBlock( "minecraft:wool", 8 );
	public final static BasicBlock WALL_3 = new BasicBlock( "minecraft:wool", 1 );
	public final static BasicBlock WALL_4 = new BasicBlock( "minecraft:wool" );
	public final static BasicBlock WALL_5 = new BasicBlock( "minecraft:wool", 6 );
	public final static BasicBlock PATH = new BasicBlock( (short) 208 );
	public final static BasicBlock ROAD = new BasicBlock( "minecraft:stone", 6 );
}
