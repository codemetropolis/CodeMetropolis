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
 * A {@link Garden} subclass for the {@link Themes#RAILWAY} theme. Instead of a garden it creates a rail for the trains.
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
		BasicBlock[][][] rail = new BasicBlock[1][1][size.getZ()];
		BasicBlock[][][] railBase = new BasicBlock[3][1][size.getZ()];

		for (int i = 0; i < size.getZ(); i++) {
			railBase[0][0][i] = RailwayBlocks.RAIL_DIRT;
			railBase[1][0][i] = RailwayBlocks.RAIL_WOOD;
			railBase[2][0][i] = RailwayBlocks.RAIL_DIRT;
			rail[0][0][i] = RailwayBlocks.EMPTY_BLOCK;
		}

		rail[0][0][1] = RailwayBlocks.RAIL_IRON;
		rail[0][0][size.getZ() - 2] = RailwayBlocks.RAIL_IRON;

		primitives.add(
			new SimpleBox(
				position, new Point(size.getX(), 2, size.getZ()),
				new YSplitPattern(
					0,
					new RepeationPattern(railBase),
					new RepeationPattern(rail)),
				Orientation.NearX));
	}

	/**
	 * Representing doors as bumpers.
	 */
	@Override
	protected void prepareDoor() {
		BasicBlock E = RailwayBlocks.EMPTY_BLOCK;
		BasicBlock I = RailwayBlocks.RAIL_IRON;
		BasicBlock D = RailwayBlocks.RAIL_DIRT;
		BasicBlock W = RailwayBlocks.RAIL_WOOD;

		BasicBlock[][][] bumper = new BasicBlock[][][] {
			{
				{ I, E },
				{ E, E },
				{ I, E }
			},
			{
				{ I, I },
				{ E, I },
				{ I, I }
			},
			{
				{ E, I },
				{ E, E },
				{ E, I }
			},
			{
				{ W, I },
				{ W, D },
				{ W, I }
			}
		};

		primitives.add(
			new SimpleBox(
				position.translate(new Point(0, 0, (size.getZ() - 3) / 2)),
				new Point(2, 4, 3),
				new RepeationPattern(RailwayUtils.transpose(bumper)),
				Orientation.NearY));
	}

	/**
	 * In {@link Themes#RAILWAY} theme signs are oriented the same way in a default {@link Garden}.
	 */
	@Override
	protected void prepareSigns() {
		primitives.add(new SignPost(position.getX(), position.getY() + 3, position.getZ() + size.getZ() / 2,
			SignPost.Orientation.WEST, innerBuildable.getName()));
	}

}
