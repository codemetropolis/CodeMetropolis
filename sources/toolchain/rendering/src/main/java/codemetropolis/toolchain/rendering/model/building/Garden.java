package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RandomPattern;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.pattern.YSplitPattern;
import codemetropolis.toolchain.rendering.model.primitive.Primitive;
import codemetropolis.toolchain.rendering.model.primitive.SignPost;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.util.Orientation;

import java.util.LinkedList;

public class Garden extends Building {

	public Garden(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
		
		if ( innerBuildable.getType() != Type.GARDEN )
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());

		primitives.addAll(prepareBase());
		primitives.addAll(prepareDoor());
		primitives.addAll(prepareSigns());
	}
	
	protected LinkedList<Primitive> prepareBase( ) {
		BasicBlock _fnc = BasicBlock.FENCE;
		BasicBlock _sns = BasicBlock.SANDSTONE;
		LinkedList<Primitive> repeatPattern = new LinkedList<>();
		RandomPattern _flowers = new RandomPattern( new RepeationPattern(  new BasicBlock[][][]{ { { BasicBlock.NON_BLOCK } } } ) );
		
		RandomPattern _redOrYellow = new RandomPattern( new RepeationPattern(  new BasicBlock[][][]{ { { BasicBlock.DANDELION } } } ) );
		_redOrYellow.add(new RepeationPattern(  new BasicBlock[][][]{ { { BasicBlock.POPPY } } } ), 0.5);
		_flowers.add(
			_redOrYellow,
			innerBuildable.hasAttribute( "flower-ratio" )
				? Double.parseDouble( innerBuildable.getAttributeValue("flower-ratio") )
				: 0 );
		_flowers.add(
			new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.BROWN_MUSHROOM } } } ),
			innerBuildable.hasAttribute( "mushroom-ratio" )
				? Double.parseDouble( innerBuildable.getAttributeValue("mushroom-ratio") )
				: 0 );
		_flowers.add(
			new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.OAK_SAPLING } } } ),
			innerBuildable.hasAttribute( "tree-ratio" )
				? Double.parseDouble( innerBuildable.getAttributeValue("tree-ratio") )
				: 0 );
		repeatPattern.add(new SolidBox(position, new Point(size.getX(), 2, size.getZ()),
				new YSplitPattern(0, new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.GRASS_BLOCK } } }),
						_flowers),
				new RepeationPattern(new BasicBlock[][][] { { { _fnc }, { _sns } } }), Orientation.NearX));

		return repeatPattern;
	}

	protected LinkedList<Primitive> prepareDoor() {
		LinkedList<Primitive> doors = new LinkedList<>();
		BasicBlock fence = BasicBlock.FENCE;
		BasicBlock[] woolBlocks = {
				BasicBlock.RED_WOOL,
				BasicBlock.LIME_WOOL,
				BasicBlock.LIGHT_BLUE_WOOL,
				BasicBlock.YELLOW_WOOL
		};

		addDoor(doors, new Point(center.getX() - 1, 0, 0), new Point(3, 4, 1), fence, woolBlocks[0]);
		addDoor(doors, new Point(center.getX() - 1, 0, size.getZ() - 1), new Point(3, 4, 1), fence, woolBlocks[1]);
		addDoor(doors, new Point(0, 0, center.getZ() - 1), new Point(1, 4, 3), fence, woolBlocks[2]);
		addDoor(doors, new Point(size.getX() - 1, 0, center.getZ() - 1), new Point(1, 4, 3), fence, woolBlocks[3]);

		return doors;
	}

	private void addDoor(LinkedList<Primitive> doors, Point translatePoint, Point size, BasicBlock fence, BasicBlock wool) {
		doors.add(
				new SolidBox(
						position.translate(translatePoint),
						size,
						new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.AIR } } }),
						new RepeationPattern(
								new BasicBlock[][][] {
										{
												{ fence },
												{ fence },
												{ fence },
												{ wool }
										}
								}
						),
						Orientation.NearX
				)
		);
	}


	protected LinkedList<Primitive> prepareSigns( ) {
		LinkedList<Primitive> signs = new LinkedList<>();
		signs.add(new SignPost(position.getX(), position.getY() + 2, position.getZ(), SignPost.Orientation.NORTHWEST, innerBuildable.getName()));
		signs.add(new SignPost(position.getX() + size.getX() - 1, position.getY() + 2, position.getZ(), SignPost.Orientation.NORTHEAST, innerBuildable.getName()));
		signs.add(new SignPost(position.getX(), position.getY() + 2, position.getZ() + size.getZ() - 1, SignPost.Orientation.SOUTHWEST, innerBuildable.getName()));
		signs.add(new SignPost(position.getX() + size.getX() - 1, position.getY() + 2, position.getZ() + size.getZ() - 1, SignPost.Orientation.SOUTHEAST, innerBuildable.getName()));
		return signs;
	}

}
