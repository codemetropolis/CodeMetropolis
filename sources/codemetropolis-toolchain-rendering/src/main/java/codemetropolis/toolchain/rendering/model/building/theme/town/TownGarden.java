package codemetropolis.toolchain.rendering.model.building.theme.town;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Garden;
import codemetropolis.toolchain.rendering.model.pattern.RandomPattern;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.pattern.YSplitPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.model.primitive.WallSign;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Garden} subclass for the {@link Themes#TOWN} theme. A nice garden with flowers and trees, surrounded by a
 * hedge.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TownGarden extends Garden {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which garden type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public TownGarden(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}

	/**
	 * A nice garden with flowers and trees. It's the same as the {@link Garden}'s appearance.
	 */
	@Override
	protected void prepareBase() {
		RandomPattern _flowers = new RandomPattern(
			new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.NonBlock } } }));

		RandomPattern _redOrYellow = new RandomPattern(
			new RepeationPattern(new BasicBlock[][][] { { { new BasicBlock("minecraft:yellow_flower") } } }));
		_redOrYellow.add(new RepeationPattern(new BasicBlock[][][] { { { new BasicBlock("minecraft:red_flower") } } }),
			0.5);

		_flowers.add(
			_redOrYellow,
			innerBuildable.hasAttribute("flower-ratio")
				? Double.parseDouble(innerBuildable.getAttributeValue("flower-ratio"))
				: 0);
		_flowers.add(
			new RepeationPattern(new BasicBlock[][][] { { { new BasicBlock("minecraft:brown_mushroom") } } }),
			innerBuildable.hasAttribute("mushroom-ratio")
				? Double.parseDouble(innerBuildable.getAttributeValue("mushroom-ratio"))
				: 0);
		_flowers.add(
			new RepeationPattern(new BasicBlock[][][] { { { new BasicBlock("minecraft:sapling") } } }),
			innerBuildable.hasAttribute("tree-ratio")
				? Double.parseDouble(innerBuildable.getAttributeValue("tree-ratio"))
				: 0);

		primitives.add(
			new SolidBox(
				position,
				new Point(size.getX(), 2, size.getZ()),
				new YSplitPattern(
					0,
					new RepeationPattern(new BasicBlock[][][] { { { new BasicBlock("minecraft:grass") } } }),
					_flowers),
				new RepeationPattern(
					new BasicBlock[][][] {
						{
							{ TownBlocks.FENCE },
							{ new BasicBlock("minecraft:grass") }
						}
					}),
				Orientation.NearX));

		primitives.add(
			new SolidBox(
				position.translate(new Point(0, 2, 0)), new Point(size.getX(), 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.EMPTY_BLOCK } } }),
				new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.FENCE } } }), Orientation.NearX));
	}

	/**
	 * According to {@link Themes#TOWN} theme there are no doors, just spaces in the hedge.
	 */
	@Override
	protected void prepareDoor() {
		RepeationPattern space = new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.EMPTY_BLOCK } } });

		primitives.add(new SimpleBox(position.translate(new Point(center.getX(), 1, 0)), new Point(1, 2, 1), space,
			Orientation.NearX));
		primitives.add(new SimpleBox(position.translate(new Point(center.getX(), 1, size.getZ() - 1)),
			new Point(1, 2, 1), space, Orientation.NearX));
		primitives.add(new SimpleBox(position.translate(new Point(0, 1, center.getZ())), new Point(1, 2, 1), space,
			Orientation.NearX));
		primitives.add(new SimpleBox(position.translate(new Point(size.getX() - 1, 1, center.getZ())),
			new Point(1, 2, 1), space, Orientation.NearX));
	}

	/**
	 * Signs are added on the side of the hedge like street signs.
	 */
	@Override
	protected void prepareSigns() {
		primitives.add(new WallSign(position.getX(), position.getY() + 2, position.getZ() - 1,
			WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() - 1, position.getY() + 2, position.getZ() - 1,
			WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX(), position.getY() + 2, position.getZ() + size.getZ(),
			WallSign.Orientation.SOUTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() - 1, position.getY() + 2,
			position.getZ() + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
	}

}
