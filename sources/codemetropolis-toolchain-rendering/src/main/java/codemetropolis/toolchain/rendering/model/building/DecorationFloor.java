package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;

/**
 * A new type of {@link Building}, which is meant to close the top of buildings. On top of this floor you can add fancy
 * decorations.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class DecorationFloor extends Building {

	public DecorationFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);

		if (innerBuildable.getType() != Type.DECORATION_FLOOR)
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());

		prepareCeiling();
		prepareRoof();
	}

	/**
	 * This function creates one simple layer. It closes the top of the building with the same block, which was used for
	 * the {@link Floor}.
	 */
	protected void prepareCeiling() {
		// The BASIC theme doesn't use decoration floors.
	}

	/**
	 * This function creates some fancy decoration on the top of the building. It can even be a creative decoration or a
	 * simple roof.
	 */
	protected void prepareRoof() {
		// The BASIC theme doesn't use decoration floors.
	}

}
