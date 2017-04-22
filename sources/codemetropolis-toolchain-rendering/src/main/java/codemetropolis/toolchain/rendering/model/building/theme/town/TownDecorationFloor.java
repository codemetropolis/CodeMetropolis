package codemetropolis.toolchain.rendering.model.building.theme.town;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.DecorationFloor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Character;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link DecorationFloor} subclass for the {@link Themes#TOWN} theme. Closing the top of the building with a
 * horizontal layer, then a nice, classical, red roof.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TownDecorationFloor extends DecorationFloor {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which decoration floor type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public TownDecorationFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}

	/**
	 * Closing the top of the building with only a horizontal layer.
	 */
	@Override
	protected void prepareCeiling() {
		BasicBlock houseBlock = innerBuildable.hasAttribute("external_character")
			? Character.parse(innerBuildable.getAttributeValue("external_character")).getBlock()
			: TownBlocks.ROOF;

		primitives.add(
			new SimpleBox(
				position,
				new Point(size.getX(), 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { houseBlock } } }),
				Orientation.NearY));
	}

	/**
	 * According to {@link Themes#TOWN} theme on the top there is a classical, red roof.
	 */
	@Override
	protected void prepareRoof() {
		BasicBlock houseBlock = innerBuildable.hasAttribute("external_character")
			? Character.parse(innerBuildable.getAttributeValue("external_character")).getBlock()
			: TownBlocks.ROOF;

		for (int i = 0; i < (size.getZ() + 1) / 2; i++) {
			primitives.add(
				new SimpleBox(
					position.translate(new Point(0, i, i)),
					new Point(size.getX(), 1, size.getZ() - (i * 2)),
					new RepeationPattern(new BasicBlock[][][] { { { houseBlock } } }),
					Orientation.NearX));
		}

		for (int i = 0; i < ((size.getZ() + 1) / 2) + 1; i++) {
			primitives.add(
				new SimpleBox(
					position.translate(new Point(-1, i, i - 1)),
					new Point(size.getX() + 2, 1, 1),
					new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.ROOF_LEFT } } }),
					Orientation.NearX));

			primitives.add(
				new SimpleBox(
					position.translate(new Point(-1, i, size.getZ() - i)),
					new Point(size.getX() + 2, 1, 1),
					new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.ROOF_RIGHT } } }),
					Orientation.NearX));
		}

		if (size.getZ() % 2 != 0) {
			primitives.add(
				new SimpleBox(
					position.translate(new Point(-1, (size.getZ() / 2) + 1, size.getZ() / 2)),
					new Point(size.getX() + 2, 1, 1),
					new RepeationPattern(new BasicBlock[][][] { { { TownBlocks.ROOF } } }),
					Orientation.NearX));
		}
	}

}
