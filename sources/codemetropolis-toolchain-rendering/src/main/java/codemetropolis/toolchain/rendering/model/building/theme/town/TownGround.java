package codemetropolis.toolchain.rendering.model.building.theme.town;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Themes;
import codemetropolis.toolchain.rendering.model.building.Ground;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SignPost;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A {@link Ground} subclass for the {@link Themes#TOWN} theme.
 * Creates a simple base for the buildings, using a simple block type.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TownGround extends Ground {

	/**
	 * Runs all the parent's functions.
	 * 
	 * @param innerBuildable The buildable of which floor type is created.
	 * @throws BuildingTypeMismatchException Throws exception if type of {@code innerBuildable} is incorrect.
	 */
	public TownGround(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}
	
	/**
	 * Pattern is determined depending on which side of the street we're building.
	 */
	@Override
	protected void prepareBase( ) {
		primitives.add(
				new SimpleBox(
					position,
					new Point( size.getX(), 1, 2 ),
					new RepeationPattern( new BasicBlock[][][]{ { { TownBlocks.PATH } } } ),
					Orientation.NearX ) );
		primitives.add(
				new SimpleBox(
					position.translate( new Point(0, 0, 2) ),
					new Point( size.getX(), 1, 6 ),
					new RepeationPattern( new BasicBlock[][][]{ { { TownBlocks.ROAD } } } ),
					Orientation.NearX ) );
		primitives.add(
				new SimpleBox(
					position.translate( new Point(0, 0, 8) ),
					new Point( size.getX(), 1, 2 ),
					new RepeationPattern( new BasicBlock[][][]{ { { TownBlocks.PATH } } } ),
					Orientation.NearX ) );	
	}
	
	/**
	 * Signs are oriented along the sides of the road, facing the road.
	 */
	@Override
	protected void prepareSigns( ) {
		if(innerBuildable.hasAttribute("left") && innerBuildable.getAttributeValue("left").equals("true")) { // left side
			primitives.add(new SignPost(position.getX(), position.getY() + 1, position.getZ() + 1, SignPost.Orientation.SOUTH, innerBuildable.getName()));
			primitives.add(new SignPost(position.getX() + size.getX() - 7, position.getY() + 1, position.getZ() + 1, SignPost.Orientation.SOUTH, innerBuildable.getName()));
		} else { // right side
			primitives.add(new SignPost(position.getX(), position.getY() + 1, position.getZ() + size.getZ() - 2, SignPost.Orientation.NORTH, innerBuildable.getName()));
			primitives.add(new SignPost(position.getX() + size.getX() - 7, position.getY() + 1, position.getZ() + size.getZ() - 2, SignPost.Orientation.NORTH, innerBuildable.getName()));
		}
	}

}
