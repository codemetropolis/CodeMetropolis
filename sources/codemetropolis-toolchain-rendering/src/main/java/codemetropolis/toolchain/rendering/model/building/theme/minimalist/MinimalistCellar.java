package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Cellar} subclass for the {@link Themes#MINIMALIST} theme.
 * Repeating {@link Floor} downwards, also without stairs.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class MinimalistCellar extends Cellar {
	
	/**
	 * Creates a simple cellar, without stairs.
	 * It creates an instance of {@link MinimalistFloor}, then in this one stairs are removed. 
	 * 
	 * @param innerBuildable The buildable of which cellar type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public MinimalistCellar(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
		
		if ( innerBuildable.getType() != Type.CELLAR )
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());
		
		MinimalistFloor minFloor = new MinimalistFloor(innerBuildable);
		primitives = minFloor.getPrimitives();
		
		primitives.add(
				0,
				new SolidBox(
					position.translate( new Point( 1, size.getY() - ( size.getY() / 2 ), 1 ) ),
					size.translate( new Point( -2, -2, -2 ) ),
					new RepeationPattern( new BasicBlock[][][] { { { MinimalistBlocks.EMPTY_BLOCK } } } ),
					new RepeationPattern( new BasicBlock[][][] { { { MinimalistBlocks.EMPTY_BLOCK } } } ),
					Orientation.NearX ) );
	}
	
	/**
	 * Walls are copied from {@link MinimalistFloor}.
	 */
	@Override
	protected void prepareWalls() {}
	
	/**
	 * According to {@link Themes#MINIMALIST} theme there are no stairs.
	 */
	@Override
	protected void prepareStairs() {}
	
	/**
	 * According to {@link Themes#MINIMALIST} theme there are no doors.
	 */
	@Override
	protected void prepareDoor() {}
	
	/**
	 * According to {@link Themes#MINIMALIST} theme there are no signs.
	 */
	@Override
	protected void prepareSigns() {}
	
	/**
	 * According to {@link Themes#MINIMALIST} theme there are no torches.
	 */
	@Override
	protected void prepareTorches() {}

}
