package codemetropolis.toolchain.rendering.model.building.theme.railway;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Garden;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.pattern.YSplitPattern;
import codemetropolis.toolchain.rendering.model.primitive.SignPost;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Garden} subclass for the {@link Themes#RAILWAY} theme.
 * Instead of a garden it creates a rail for the trains.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayGarden extends Garden {

	public RailwayGarden(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}
	
	/**
	 * Instead of a garden it creates a rail for the trains.
	 */
	@Override
	protected void prepareBase() {		
		BasicBlock[][][] iron = new BasicBlock[1][1][size.getZ()];
		BasicBlock[][][] sand = new BasicBlock[3][1][size.getZ()];
		for(int i = 0; i < sand[0][0].length; i++) {
			sand[0][0][i] = RailwayBlocks.RAIL_STONE;
			sand[1][0][i] = RailwayBlocks.RAIL_WOOD;
			sand[2][0][i] = RailwayBlocks.RAIL_STONE;
			iron[0][0][i] = RailwayBlocks.EMPTY_BLOCK;
		}
		iron[0][0][1] = RailwayBlocks.RAIL_IRON;
		iron[0][0][size.getZ() - 2] = RailwayBlocks.RAIL_IRON;
		
		primitives.add(
				new SimpleBox(
						position, new Point( size.getX(), 2, size.getZ() ),
						new YSplitPattern(
							0,
							new RepeationPattern( sand ),
							new RepeationPattern( iron ) ),
						Orientation.NearX ) );
	}
	
	/**
	 * According to {@link Themes#RAILWAY} theme there are no doors.
	 */
	@Override
	protected void prepareDoor() {}
	
	/**
	 * In {@link Themes#RAILWAY} theme signs are oriented the same way in a default {@link Garden}.
	 */
	@Override
	protected void prepareSigns() {
		primitives.add(new SignPost(position.getX(), position.getY(), position.getZ() + size.getZ() / 2, SignPost.Orientation.WEST, innerBuildable.getName()));
		primitives.add(new SignPost(position.getX() + size.getX() - 1, position.getY(), position.getZ() + size.getZ() / 2, SignPost.Orientation.EAST, innerBuildable.getName()));
		
	}

}
