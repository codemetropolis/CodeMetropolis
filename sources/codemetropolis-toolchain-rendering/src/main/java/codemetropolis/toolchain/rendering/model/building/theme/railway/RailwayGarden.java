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

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which garden type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
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
	 * Representing doors as bumpers.
	 */
	@Override
	protected void prepareDoor() {
		BasicBlock I = RailwayBlocks.EMPTY_BLOCK;
		BasicBlock O = RailwayBlocks.RAIL_IRON;
		BasicBlock S = RailwayBlocks.RAIL_STONE;
		BasicBlock W = RailwayBlocks.RAIL_WOOD;
		BasicBlock[][][] bumper = new BasicBlock[][][]
				{
					{
						{ O, I },
						{ I, I },
						{ O, I }
					},
					{
						{ O, O },
						{ I, O },
						{ O, O }						
					},
					{
						{ I, O },
						{ I, I },
						{ I, O }						
					},
					{
						{ W, O },
						{ W, S },
						{ W, O }
					}
				};
		
		
		BasicBlock[][][] transpose = new BasicBlock[4][2][3];
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 4; k++) {
					transpose[k][i][j] = bumper[k][j][i];
				}
			}
		}
		
		primitives.add(
		new SimpleBox(
			position.translate( new Point( 0, 0, (size.getZ() - 3) / 2) ),
			new Point( 2, 4, 3 ),
			new RepeationPattern( transpose ),
			Orientation.NearY ) );
	}
	
	/**
	 * In {@link Themes#RAILWAY} theme signs are oriented the same way in a default {@link Garden}.
	 */
	@Override
	protected void prepareSigns() {
		primitives.add(new SignPost(position.getX(), position.getY() + 3, position.getZ() + size.getZ() / 2, SignPost.Orientation.WEST, innerBuildable.getName()));
		
	}

}
