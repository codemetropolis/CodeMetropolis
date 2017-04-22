package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Garden;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.pattern.YSplitPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Garden} subclass for the {@link Themes#MINIMALIST} theme. A simple garden without flowers, or any kind of
 * obstacles.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class MinimalistGarden extends Garden {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which garden type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public MinimalistGarden(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}

	/**
	 * A simple garden without flowers, or any kind of obstacles.
	 */
	@Override
	protected void prepareBase() {
		BasicBlock[][][] fence = new BasicBlock[size.getX()][1][size.getZ()];

		// Initialize the matrix
		for (int i = 0; i < fence.length; i++) {
			for (int j = 0; j < fence[0][0].length; j++) {
				fence[i][0][j] = MinimalistBlocks.EMPTY_BLOCK;
			}
		}

		// Add fence to the outer edges
		for (int i = 0; i < fence.length; i++) {
			fence[i][0][0] = MinimalistBlocks.FENCE;
			fence[i][0][size.getZ() - 1] = MinimalistBlocks.FENCE;
		}
		for (int j = 0; j < fence[0][0].length; j++) {
			fence[0][0][j] = MinimalistBlocks.FENCE;
			fence[size.getX() - 1][0][j] = MinimalistBlocks.FENCE;
		}

		primitives.add(
			new SimpleBox(
				position, new Point(size.getX(), 2, size.getZ()),
				new YSplitPattern(
					0,
					new RepeationPattern(new BasicBlock[][][] { { { MinimalistBlocks.GARDEN } } }),
					new RepeationPattern(fence)),
				Orientation.NearX));
	}

	/**
	 * According to {@link Themes#MINIMALIST} theme there are no doors.
	 */
	@Override
	protected void prepareDoor() {

	}

	/**
	 * According to {@link Themes#MINIMALIST} theme there are no signs.
	 */
	@Override
	protected void prepareSigns() {

	}

}
