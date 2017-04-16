package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Floor} subclass for the {@link Themes#MINIMALIST} theme.
 * Transparent floors, for a simple look-and-feel, with only pillars, without stairs.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class MinimalistFloor extends Floor {

	public MinimalistFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);		
	}
	
	/**
	 * Transparent glass walls, with a simple stone frame, without floor bases.
	 */
	@Override
	protected void prepareWalls() {		
		BasicBlock[][][] section = createSection(MinimalistBlocks.WALL);
		BasicBlock[][][] bottomSection = createSection(MinimalistBlocks.PILLAR);
		
		primitives.add(
				new SimpleBox(
					position,
					new Point( size.getX(), 1, size.getZ()),
					new RepeationPattern( bottomSection ),
					Orientation.NearX ) );
		
		primitives.add(
				new SimpleBox(
					position.translate( new Point( 0, 1, 0) ),
					new Point( size.getX(), size.getY() - 1, size.getZ()),
					new RepeationPattern( section ),
					Orientation.NearX) );		
	}
	
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
	
	/**
	 * Create a layer of the current floor according to the type, so it can be a middle or a bottom layer.
	 * 
	 * @param type Define if it is a middle or a bottom layer.
	 * @return The requested layer.
	 */
	private BasicBlock[][][] createSection(BasicBlock type) {
		BasicBlock[][][] section = new BasicBlock[size.getX()][1][size.getZ()];
		for(int i = 0; i < section.length; i++) {
			for(int j = 0; j < section[0][0].length; j++) {
				section[i][0][j] = MinimalistBlocks.EMPTY_BLOCK;
			}
		}
		for(int i = 0; i < section.length; i++) {
			section[i][0][0] = type;
			section[i][0][size.getZ() - 1] = type;
		}
		for(int j = 0; j < section[0][0].length; j++) {
			section[0][0][j] = type;
			section[size.getX() - 1][0][j] = type;
		}
		section[0][0][0] = MinimalistBlocks.PILLAR;
		section[0][0][size.getZ() - 1] = MinimalistBlocks.PILLAR;
		section[size.getX() - 1][0][0] = MinimalistBlocks.PILLAR;
		section[size.getX() - 1][0][size.getZ() - 1] = MinimalistBlocks.PILLAR;
		
		return section;
	}

}
