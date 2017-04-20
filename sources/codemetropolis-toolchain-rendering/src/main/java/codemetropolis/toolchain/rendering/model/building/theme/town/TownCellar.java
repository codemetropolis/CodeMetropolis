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
 * A {@link Cellar} subclass for the {@link Themes#TOWN} theme.
 * Repeating {@link Floor} downwards.
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
		BasicBlock[][][] section = createSection(TownBlocks.CELLAR_WALL, TownBlocks.EMPTY_BLOCK);
		BasicBlock[][][] topBottomSection = createSection(TownBlocks.CELLAR_WALL, TownBlocks.CELLAR_WALL);
		
		primitives.add(
				new SimpleBox(
						position.translate( new Point( 0, 1, 0) ),
					new Point( size.getX(), 1, size.getZ()),
					new RepeationPattern( topBottomSection ),
					Orientation.NearX ) );
		
		primitives.add(
				new SimpleBox(
					position.translate( new Point( 0, 2, 0) ),
					new Point( size.getX(), size.getY() - 2, size.getZ()),
					new RepeationPattern( section ),
					Orientation.NearX) );
		primitives.add(
				new SimpleBox(
					position.translate( new Point( 0, size.getY(), 0) ),
					new Point( size.getX(), 1, size.getZ()),
					new RepeationPattern( topBottomSection ),
					Orientation.NearX ) );
	}
	
	/**
	 * According to {@link Themes#TOWN} theme there are no stairs in a garage.
	 */
	@Override
	protected void prepareStairs() {}
	
	/**
	 * Doors are only on one side of the garage in {@link Themes#TOWN}.
	 */
	@Override
	protected void prepareDoor() {
		primitives.add(
			new SimpleBox(
				position.translate( new Point( 1, 1, 0 ) ), 
				new Point( size.getX() - 2, size.getY() - 1, 1 ),
				new RepeationPattern( new BasicBlock[][][] { { { TownBlocks.CELLAR_DOOR } } } ),
				Orientation.NearX )
			);
		primitives.add(
			new SimpleBox(
				position.translate( new Point( 1, 1, size.getZ() - 1 ) ),
				new Point( size.getX() - 2, size.getY() - 1, 1 ),
				new RepeationPattern( new BasicBlock[][][] { { { TownBlocks.CELLAR_DOOR } } } ),
				Orientation.NearX )
			);
		}
	
	/**
	 * There are two signs on the building in {@link Themes#TOWN}.
	 */
	@Override
	protected void prepareSigns() {
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + size.getY(), position.getZ() - 1, WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + size.getY(), position.getZ()  + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
	}
	
	/**
	 * According to {@link Themes#TOWN} theme there are no torches on a garage.
	 */
	@Override
	protected void prepareTorches() {}
	
	/**
	 * Create a layer of the current floor according to the type, so it can be a middle or a bottom layer.
	 * 
	 * @param type Define if it is a middle or a bottom layer.
	 * @return The requested layer.
	 */
	private BasicBlock[][][] createSection(BasicBlock typeOutside, BasicBlock typeInside) {
		BasicBlock[][][] section = new BasicBlock[size.getX()][1][size.getZ()];
		for(int i = 0; i < section.length; i++) {
			for(int j = 0; j < section[0][0].length; j++) {
				section[i][0][j] = typeInside;
			}
		}
		for(int i = 0; i < section.length; i++) {
			section[i][0][0] = typeOutside;
			section[i][0][size.getZ() - 1] = typeOutside;
		}
		for(int j = 0; j < section[0][0].length; j++) {
			section[0][0][j] = typeOutside;
			section[size.getX() - 1][0][j] = typeOutside;
		}
		
		return section;
	}

}
