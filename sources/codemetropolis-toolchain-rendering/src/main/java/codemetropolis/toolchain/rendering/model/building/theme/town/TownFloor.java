package codemetropolis.toolchain.rendering.model.building.theme.town;

import java.util.Random;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.Door;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.model.primitive.WallSign;
import codemetropolis.toolchain.rendering.util.Character;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Floor} subclass for the {@link Themes#TOWN} theme.
 * Nice, little houses, with windows and doors.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TownFloor extends Floor {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which floor type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public TownFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}
	
	/**
	 * Less doors are created, so the house looks more realistic.
	 * In this functions windows are also created.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void prepareDoor() {
		// Prepare windows
		int sizeRow = 2;
		int sizeLine = 4;
		for(int i = 1; i <= (size.getY() + 1) / 5; i++) {
			for(int j = 1; j <= size.getX() / 3; j++) {
				primitives.add(
						new SimpleBox(
								position.translate( new Point(j + (j-1) * sizeRow, i + (i-1) * sizeLine + 1, 0) ),
								new Point( 1, 2, 1 ),
								new RepeationPattern( new BasicBlock[][][] { { { TownBlocks.WINDOW } } } ),
								Orientation.NearX )
						);
				primitives.add(
						new SimpleBox(
								position.translate( new Point(j + (j-1) * sizeRow, i + (i-1) * sizeLine + 1, size.getZ() - 1) ),
								new Point( 1, 2, 1 ),
								new RepeationPattern( new BasicBlock[][][] { { { TownBlocks.WINDOW } } } ),
								Orientation.NearX )
						);
			}
			for(int j = 1; j <= size.getZ() / 3; j++) {
				primitives.add(
						new SimpleBox(
								position.translate( new Point(0, i + (i-1) * sizeLine + 1, j + (j-1) * sizeRow) ),
								new Point( 1, 2, 1 ),
								new RepeationPattern( new BasicBlock[][][] { { { TownBlocks.WINDOW } } } ),
								Orientation.NearX )
						);
				primitives.add(
						new SimpleBox(
								position.translate( new Point(size.getX() - 1, i + (i-1) * sizeLine + 1, j + (j-1) * sizeRow) ),
								new Point( 1, 2, 1 ),
								new RepeationPattern( new BasicBlock[][][] { { { TownBlocks.WINDOW } } } ),
								Orientation.NearX )
						);
			}
		}
		
		// Prepare doors
		primitives.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ(), Door.Orientation.SOUTH));
		primitives.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ()  + size.getZ() - 1, Door.Orientation.NORTH));
	}
	
	/**
	 * There is no need for stairs in a one floor house.
	 */
	@Override
	protected void prepareStairs() { }
	
	/**
	 * Creates a simple wall of wool.
	 */
	@Override
	protected void prepareWalls() {
		RepeationPattern wall;
		
		if(innerBuildable.hasAttribute( "external_character" ))
		{
			Character externalCharacter = Character.parse(innerBuildable.getAttributeValue("external_character"));
			wall = new RepeationPattern( new BasicBlock[][][] { { { externalCharacter.getBlock() } } } );
		} else {
			Random rand = new Random();
			BasicBlock block;
			try {
				block = (BasicBlock)TownBlocks.class.getField("WALL_" + (rand.nextInt(5 - 1 + 1) + 1)).get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				block = TownBlocks.WALL_4;
			}
			wall = new RepeationPattern( new BasicBlock[][][] { { { block } } } );
		}
		
		primitives.add( new SimpleBox(position, new Point( size.getX(), 1, size.getZ() ), wall, Orientation.NearX ) );
		
		for(int i = 0; i < size.getY() - 2; i++) {
			primitives.add(
					new SolidBox(
						position.translate( new Point(0, i + 1, 0) ), new Point( size.getX(), 1, size.getZ() ),
						new RepeationPattern( new BasicBlock[][][] { { { TownBlocks.EMPTY_BLOCK } } } ), wall,
						Orientation.NearX )
					);
		}
		
		primitives.add(
				new SimpleBox(
						position.translate( new Point(0, size.getY() - 1, 0) ), new Point( size.getX(), 1, size.getZ() ),
						wall, Orientation.NearX ) );
		
	}
	
	/**
	 * Only one sign above every each door.
	 */
	@Override
	protected void prepareSigns( ) {
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() - 1, WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ()  + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
	}

}
