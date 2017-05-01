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

public class Ground extends Building{
	
	public Ground(Buildable innerBuildable) throws BuildingTypeMismatchException{
		super(innerBuildable);
		
		if (innerBuildable.getType() != Type.GROUND)
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());
		
		prepareBase();
		prepareSigns();
	}
	
	private void prepareBase(){
		primitives.add(new SolidBox(position, new Point(size.getX(), 1, size.getZ()),
				new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.get("minecraft:stone") } } }),
				new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.get("minecraft:stone_slab", 5) } } }),
				Orientation.NearX)
		);
	}
	
	private void prepareSigns(){
		primitives.add(new SignPost(position.getX() + 1, position.getY() + 1, position.getZ() + 1,
				SignPost.Orientation.NORTHWEST, innerBuildable.getName())
		);
		primitives.add(new SignPost(position.getX() + size.getX() - 2, position.getY() + 1, position.getZ() + 1,
				SignPost.Orientation.NORTHEAST, innerBuildable.getName())
		);
		primitives.add(new SignPost(position.getX() + 1, position.getY() + 1, position.getZ() + size.getZ() - 2,
				SignPost.Orientation.SOUTHWEST, innerBuildable.getName())
		);
		primitives.add(new SignPost(position.getX() + size.getX() - 2, position.getY() + 1, position.getZ() + size.getZ() - 2,
				SignPost.Orientation.SOUTHEAST, innerBuildable.getName())
		);
	}
	
}
