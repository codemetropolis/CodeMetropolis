package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.DecorationFloor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link DecorationFloor} subclass for the {@link Themes#MINIMALIST} theme.
 * Closing the top of the building with only a horizontal layer.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class MinimalistDecorationFloor extends DecorationFloor {

	public MinimalistDecorationFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}
	
	/**
	 * Closing the top of the building with only a horizontal layer.
	 */
	@Override
	protected void prepareCeiling() {
		primitives.add(
			new SimpleBox(
				position,
				new Point( size.getX(), 1, size.getZ() ),
				new RepeationPattern( new BasicBlock[][][] { { { MinimalistBlocks.PILLAR } } } ),
				Orientation.NearY ) );
	}
	
	/**
	 * According to {@link Themes#MINIMALIST} theme there is no top decoration.
	 */
	@Override
	protected void prepareRoof() {}

}
