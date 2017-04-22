package codemetropolis.toolchain.rendering.model.building.theme.town;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.model.primitive.WallSign;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Cellar} subclass for the {@link Themes#TOWN} theme. Repeating {@link Floor} downwards.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TownCellar extends Cellar {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which cellar type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public TownCellar(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}

	/**
	 * A simple wall.
	 */
	@Override
	protected void prepareWalls() {
		primitives.add(
			new SimpleBox(
				position.translate(new Point(0, 1, 0)),
				new Point(size.getX(), 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.CELLAR_WALL } } }),
				Orientation.NearX));

		primitives.add(
			new SimpleBox(
				position.translate(new Point(0, 1, 0)),
				new Point(1, size.getY() - 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.CELLAR_WALL } } }),
				Orientation.NearX));

		primitives.add(
			new SimpleBox(
				position.translate(new Point(size.getX() -1, 1, 0)),
				new Point(1, size.getY() - 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.CELLAR_WALL } } }),
				Orientation.NearX));

		primitives.add(
			new SimpleBox(
				position.translate(new Point(0, size.getY(), 0)),
				new Point(size.getX(), 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.CELLAR_WALL } } }),
				Orientation.NearX));
	}

	/**
	 * According to {@link Themes#TOWN} theme there are no stairs in a garage.
	 */
	@Override
	protected void prepareStairs() {}

	/**
	 * Doors are only on opposite sides of the garage in {@link Themes#TOWN}.
	 */
	@Override
	protected void prepareDoor() {
		primitives.add(
			new SimpleBox(
				position.translate(new Point(1, 1, 0)),
				new Point(size.getX() - 2, size.getY() - 1, 1),
				new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.CELLAR_DOOR } } }),
				Orientation.NearX));

		primitives.add(
			new SimpleBox(
				position.translate(new Point(1, 1, size.getZ() - 1)),
				new Point(size.getX() - 2, size.getY() - 1, 1),
				new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.CELLAR_DOOR } } }),
				Orientation.NearX));
	}

	/**
	 * There are two signs on the building in {@link Themes#TOWN}.
	 */
	@Override
	protected void prepareSigns() {
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + size.getY(),
			position.getZ() - 1, WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + size.getY(),
			position.getZ() + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
	}

	/**
	 * According to {@link Themes#TOWN} theme there are no torches in a garage.
	 */
	@Override
	protected void prepareTorches() {}

}
