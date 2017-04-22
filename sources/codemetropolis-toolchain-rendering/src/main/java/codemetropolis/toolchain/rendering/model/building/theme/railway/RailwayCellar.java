package codemetropolis.toolchain.rendering.model.building.theme.railway;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.model.primitive.WallSign;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Cellar} subclass for the {@link Themes#RAILWAY} theme. Representing cellars with only an empty wagon base.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayCellar extends Cellar {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which cellar type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public RailwayCellar(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}

	/**
	 * In this theme there are no walls for wagons, which represents cellars.
	 */
	@Override
	protected void prepareWalls() {
		primitives.add(
			new SimpleBox(
				position,
				new Point(size.getX() + 2, 2, size.getZ()),
				new RepeationPattern(RailwayUtils.createWagonBase(2, size.getZ(), size.getX() + 2)),
				Orientation.NearY));
	}

	/**
	 * According to {@link Themes#RAILWAY} theme there are no stairs.
	 */
	@Override
	protected void prepareStairs() {}

	/**
	 * According to {@link Themes#RAILWAY} theme there are no doors.
	 */
	@Override
	protected void prepareDoor() {}

	/**
	 * Sings are on the sides of these wagons.
	 */
	@Override
	protected void prepareSigns() {
		primitives.add(new WallSign(position.getX() + (size.getX() + 2) / 2, position.getY() + 1, position.getZ() - 1,
			WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + (size.getX() + 2) / 2, position.getY() + 1,
			position.getZ() + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
	}

	/**
	 * According to {@link Themes#RAILWAY} theme there are no torches.
	 */
	@Override
	protected void prepareTorches() {}

}
