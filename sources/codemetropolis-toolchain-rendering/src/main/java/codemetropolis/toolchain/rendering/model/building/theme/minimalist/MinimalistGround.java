package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Ground;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Ground} subclass for the {@link Themes#MINIMALIST} theme.
 * Creates a simple base for the buildings, using a simple block type.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class MinimalistGround extends Ground {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which ground type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public MinimalistGround(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}
	
	/**
	 * Creates a simple base for the buildings, using a simple block type.
	 */
	@Override
	protected void prepareBase( ) {		
		primitives.add(
			new SimpleBox(
				position,
				new Point( size.getX(), 1, size.getZ() ),
				new RepeationPattern( new BasicBlock[][][]{ { { MinimalistBlocks.BASE } } } ),
				Orientation.NearX ) );
	}
	
	/**
	 * According to {@link Themes#MINIMALIST} theme there are no signs.
	 */
	@Override
	protected void prepareSigns( ) {}

}
