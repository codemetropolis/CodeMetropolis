package codemetropolis.toolchain.rendering.model.building.theme.railway;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Cellar;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.model.primitive.WallSign;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Cellar} subclass for the {@link Themes#RAILWAY} theme.
 * Representing cellars with only an empty wagon base.
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
		BasicBlock[][][] wagonBase = createWagonBase();
		
		primitives.add(
				new SimpleBox(
					position,
					new Point( size.getX() + 2, 2, size.getZ()),
					new RepeationPattern( wagonBase ),
					Orientation.NearY ) );
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
		primitives.add(new WallSign(position.getX() + (size.getX() + 2) / 2, position.getY() + 1, position.getZ() - 1, WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + (size.getX() + 2) / 2, position.getY() + 1, position.getZ()  + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
	}
	
	/**
	 * According to {@link Themes#RAILWAY} theme there are no torches.
	 */
	@Override
	protected void prepareTorches() {}
	
	/**
	 * Create the base of a wagon, with an iron base and wheels.
	 * 
	 * @return The requested layer.
	 */
	private BasicBlock[][][] createWagonBase() {		
		BasicBlock[][][] base = new BasicBlock[2][size.getZ()][size.getX() + 2];
		
		for(int k = 0; k < base.length; k++) {
			for(int j = 0; j < base[0].length; j++) {
				for(int i = 0; i < base[0][0].length; i++) {
					base[1][j][i] = RailwayBlocks.EMPTY_BLOCK;
					base[0][j][i] = RailwayBlocks.RAIL_IRON;
				}
			}
		}
		for(int i = 0; i < base[0].length; i++) {
			base[0][i][0] = RailwayBlocks.EMPTY_BLOCK;
			base[0][i][base[0][0].length - 1] = RailwayBlocks.EMPTY_BLOCK;
		}
		base[0][base[0].length / 2][0] = RailwayBlocks.RAIL_IRON;
		base[0][base[0].length / 2][base[0][0].length - 1] = RailwayBlocks.RAIL_IRON;
		base[1][0][2] = RailwayBlocks.RAIL_IRON;
		base[1][base[0].length - 1][2] = RailwayBlocks.RAIL_IRON;
		base[1][0][base[0][0].length - 3] = RailwayBlocks.RAIL_IRON;
		base[1][base[0].length - 1][base[0][0].length - 3] = RailwayBlocks.RAIL_IRON;
				
		BasicBlock[][][] transpose = new BasicBlock[2][size.getX() + 2][size.getZ()];
		for(int i = 0; i < size.getX() + 2; i++) {
			for(int j = 0; j < size.getZ(); j++) {
				for(int k = 0; k < 2; k++) {
					transpose[k][i][j] = base[k][j][i];
				}
			}
		}
		
		return transpose;
	}

}
