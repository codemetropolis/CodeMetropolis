package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SignPost;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.util.Orientation;

public class Ground extends Building {

	public Ground(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);

		if ( innerBuildable.getType() != Type.GROUND )
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());

		prepareBase();
		prepareSigns();
	}
	
	protected void prepareBase( ) {
		primitives.add(
			new SolidBox(
				position,
				new Point( size.getX(), 1, size.getZ() ),
				new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:stone" ) } } } ),
				new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:stonebrick" ) } } } ),
				Orientation.NearX ) );
	}
	
	protected void prepareSigns( ) {
		primitives.add(new SignPost(position.getX(), position.getY() + 1, position.getZ(), SignPost.Orientation.NORTHWEST, innerBuildable.getName()));
		primitives.add(new SignPost(position.getX() + size.getX() - 1, position.getY() + 1, position.getZ(), SignPost.Orientation.NORTHEAST, innerBuildable.getName()));
		primitives.add(new SignPost(position.getX(), position.getY() + 1, position.getZ() + size.getZ() - 1, SignPost.Orientation.SOUTHWEST, innerBuildable.getName()));
		primitives.add(new SignPost(position.getX() + size.getX() - 1, position.getY() + 1, position.getZ() + size.getZ() - 1, SignPost.Orientation.SOUTHEAST, innerBuildable.getName()));
	}

}
