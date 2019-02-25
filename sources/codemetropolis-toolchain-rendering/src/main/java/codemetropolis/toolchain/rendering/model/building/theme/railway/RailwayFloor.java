package codemetropolis.toolchain.rendering.model.building.theme.railway;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.model.primitive.WallSign;
import codemetropolis.toolchain.rendering.util.Character;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Floor} subclass for the {@link Themes#RAILWAY} theme. Floors are represented as railway wagons.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class RailwayFloor extends Floor {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which floor type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public RailwayFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}

	/**
	 * Floors are represented as railway wagons.
	 */
	@Override
	protected void prepareWalls() {
		BasicBlock wall = innerBuildable.hasAttribute("character")
			? Character.parse(innerBuildable.getAttributeValue("character")).getBlock()
			: RailwayBlocks.WAGON_WALL;
		BasicBlock pillar = innerBuildable.hasAttribute("external_character")
			? Character.parse(innerBuildable.getAttributeValue("external_character")).getBlock()
			: RailwayBlocks.WAGON_PILLARS[0];

		primitives.add(
			new SimpleBox(
				position,
				new Point(size.getX() + 2, 2, size.getZ()),
				new RepeationPattern(RailwayUtils.createWagonBase(2, size.getZ(), size.getX() + 2)),
				Orientation.NearY));

		primitives.add(
			new SimpleBox(
				position.translate(new Point(1, 2, 0)),
				new Point(size.getX(), 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { pillar } } }),
				Orientation.NearX));

		primitives.add(
			new SimpleBox(
				position.translate(new Point(1, 3, 0)),
				new Point(size.getX(), size.getY() - 5, size.getZ()),
				new RepeationPattern(createSection(pillar, wall)),
				Orientation.NearX));

		primitives.add(
			new SimpleBox(
				position.translate(new Point(1, size.getY() - 2, 0)),
				new Point(size.getX(), 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { pillar } } }),
				Orientation.NearX));

		primitives.add(
			new SimpleBox(
				position.translate(new Point(1, size.getY() - 1, 1)),
				new Point(size.getX(), 1, size.getZ() - 2),
				new RepeationPattern(new BasicBlock[][][] { { { pillar } } }),
				Orientation.NearX));
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
	 * In {@link Themes#RAILWAY} theme signs are represented on the walls, same as in the original {@link Floor}.
	 */
	@Override
	protected void prepareSigns() {
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 4, position.getZ() - 1,
			WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 4,
			position.getZ() + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
	}

	/**
	 * According to {@link Themes#RAILWAY} theme there are no torches.
	 */
	@Override
	protected void prepareTorches() {}

	/**
	 * Create a middle layer of the current wagon according to the type.
	 * 
	 * @param type The type of the used blocks.
	 * @return The requested layer.
	 */
	private BasicBlock[][][] createSection(BasicBlock pillar, BasicBlock wall) {
		BasicBlock[][][] section = new BasicBlock[size.getX()][1][size.getZ()];

		// Initialize the matrix
		for (int i = 0; i < size.getX(); i++) {
			for (int j = 0; j < size.getZ(); j++) {
				section[i][0][j] = RailwayBlocks.EMPTY_BLOCK;
			}
		}

		// Add walls
		for (int i = 0; i < size.getX(); i++) {
			section[i][0][0] = wall;
			section[i][0][size.getZ() - 1] = wall;
		}
		for (int j = 0; j < size.getZ(); j++) {
			section[0][0][j] = wall;
			section[size.getX() - 1][0][j] = wall;
		}

		// Add pillars to the corners
		section[0][0][0] = pillar;
		section[0][0][size.getZ() - 1] = pillar;
		section[size.getX() - 1][0][0] = pillar;
		section[size.getX() - 1][0][size.getZ() - 1] = pillar;

		return section;
	}

}
