package codemetropolis.toolchain.rendering.model.building;

import java.util.LinkedList;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RandomPattern;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.pattern.YSplitPattern;
import codemetropolis.toolchain.rendering.model.primitive.Mob;
import codemetropolis.toolchain.rendering.model.primitive.Primitive;
import codemetropolis.toolchain.rendering.model.primitive.SignPost;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.model.primitive.Spawner;
import codemetropolis.toolchain.rendering.util.Orientation;

public class Garden extends Building {
		int monster_count;
		private int BuiltNumber1=0;
		private int BuiltNumber2=0;
		private int BuiltNumber3=0;

	public Garden(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);

		if (innerBuildable.getType() != Type.GARDEN)
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());

		primitives.addAll(prepareBase());
		primitives.addAll(prepareDoor());
		primitives.addAll(prepareSigns());
		primitives.addAll(prepareSpawners());
		primitives.addAll(prepareSignsForSpawners());
		primitives.addAll(prepareMobs());
	}
	
	public int getBuiltNumber1() {
		return BuiltNumber1;
	}

	public void setBuiltNumber1(int builtNumber1) {
		BuiltNumber1 = builtNumber1;
	}

	public int getBuiltNumber2() {
		return BuiltNumber2;
	}

	public void setBuiltNumber2(int builtNumber2) {
		BuiltNumber2 = builtNumber2;
	}

	public int getBuiltNumber3() {
		return BuiltNumber3;
	}

	public void setBuiltNumber3(int builtNumber3) {
		BuiltNumber3 = builtNumber3;
	}

	protected LinkedList<Primitive> prepareBase() {
		BasicBlock _fnc = BasicBlock.FENCE;
		BasicBlock _sns = BasicBlock.SANDSTONE;
		LinkedList<Primitive> repeatPattern = new LinkedList<>();
		
		monster_count = innerBuildable.hasAttribute("monster-count") 
		? Integer.parseInt( innerBuildable.getAttributeValue ("monster-count") ) 
		: 0;

		RandomPattern _flowers = new RandomPattern(
				new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.NON_BLOCK } } }));

		RandomPattern _redOrYellow = new RandomPattern(
				new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.DANDELION } } }));
		_redOrYellow.add(new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.POPPY } } }), 0.5);
		_flowers.add(_redOrYellow,
				innerBuildable.hasAttribute("flower-ratio")
						? Double.parseDouble(innerBuildable.getAttributeValue("flower-ratio"))
						: 0);
		_flowers.add(new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.BROWN_MUSHROOM } } }),
				innerBuildable.hasAttribute("mushroom-ratio")
						? Double.parseDouble(innerBuildable.getAttributeValue("mushroom-ratio"))
						: 0);
		_flowers.add(new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.OAK_SAPLING } } }),
				innerBuildable.hasAttribute("tree-ratio")
						? Double.parseDouble(innerBuildable.getAttributeValue("tree-ratio"))
						: 0);
		repeatPattern.add(new SolidBox(position, new Point(size.getX(), 2, size.getZ()),
				new YSplitPattern(0, new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.GRASS_BLOCK } } }),
						_flowers),
				new RepeationPattern(new BasicBlock[][][] { { { _fnc }, { _sns } } }), Orientation.NearX));

		return repeatPattern;


	}

	protected LinkedList<Primitive> prepareDoor() {

		LinkedList<Primitive> doors = new LinkedList<>();

		BasicBlock _fnc = BasicBlock.FENCE;
		BasicBlock _rwl = BasicBlock.RED_WOOL;
		BasicBlock _gwl = BasicBlock.GREENWOOL;
		BasicBlock _bwl = BasicBlock.BLUE_WOOL;
		BasicBlock _ywl = BasicBlock.YELLOW_WOOL;
		doors.add(new SolidBox(position.translate(new Point(center.getX() - 1, 0, 0)), new Point(3, 4, 1),
				new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.AIR } } }),
				new RepeationPattern(new BasicBlock[][][] { { { _fnc }, { _fnc }, { _fnc }, { _rwl } } }),
				Orientation.NearX));
		doors.add(new SolidBox(position.translate(new Point(center.getX() - 1, 0, size.getZ() - 1)), new Point(3, 4, 1),
				new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.AIR } } }),
				new RepeationPattern(new BasicBlock[][][] { { { _fnc }, { _fnc }, { _fnc }, { _gwl } } }),
				Orientation.NearX));
		doors.add(new SolidBox(position.translate(new Point(0, 0, center.getZ() - 1)), new Point(1, 4, 3),
				new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.AIR } } }),
				new RepeationPattern(new BasicBlock[][][] { { { _fnc }, { _fnc }, { _fnc }, { _bwl } } }),
				Orientation.NearX));
		doors.add(new SolidBox(position.translate(new Point(size.getX() - 1, 0, center.getZ() - 1)), new Point(1, 4, 3),
				new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.AIR } } }),
				new RepeationPattern(new BasicBlock[][][] { { { _fnc }, { _fnc }, { _fnc }, { _ywl } } }),
				Orientation.NearX));

		return doors;
	}

	protected LinkedList<Primitive> prepareSigns() {
		LinkedList<Primitive> signs = new LinkedList<>();

		signs.add(new SignPost(position.getX(), position.getY() + 2, position.getZ(), SignPost.Orientation.NORTHWEST,
				innerBuildable.getName()));
		signs.add(new SignPost(position.getX() + size.getX() - 1, position.getY() + 2, position.getZ(),
				SignPost.Orientation.NORTHEAST, innerBuildable.getName()));
		signs.add(new SignPost(position.getX(), position.getY() + 2, position.getZ() + size.getZ() - 1,
				SignPost.Orientation.SOUTHWEST, innerBuildable.getName()));
		signs.add(new SignPost(position.getX() + size.getX() - 1, position.getY() + 2,
				position.getZ() + size.getZ() - 1, SignPost.Orientation.SOUTHEAST, innerBuildable.getName()));

		return signs;
	}

	protected LinkedList<Primitive> prepareSpawners() {
		LinkedList<Primitive> spawners = new LinkedList<>();
		if (monster_count > 0) spawners.add(new Spawner(position.getX() + size.getX() / 2, position.getY(), position.getZ() - 3));
		if (monster_count > 1) spawners.add(new Spawner(position.getX() + size.getX() / 2, position.getY(), position.getZ() + size.getZ() + 2));
		if (monster_count > 2) spawners.add(new Spawner(position.getX() - 3, position.getY(), position.getZ() + size.getZ() / 2));
		if (monster_count > 3) spawners.add(new Spawner(position.getX() + size.getX() + 2, position.getY(), position.getZ() + size.getZ() / 2));

		return spawners;
	}

	protected LinkedList<Primitive> prepareSignsForSpawners() {
		LinkedList<Primitive> signSpawners = new LinkedList<>();
		String signName = innerBuildable.hasAttribute("monster-label") ? (innerBuildable.getAttributeValue("monster-label")) : "";

		switch (monster_count) {
			case 4:
				signSpawners.add(new SignPost(position.getX() + size.getX() + 2, position.getY(), position.getZ() + size.getZ() / 2 + 1, SignPost.Orientation.EAST, signName));
				signSpawners.add(new SignPost(position.getX() + size.getX() + 2, position.getY(), position.getZ() + size.getZ() / 2 - 1, SignPost.Orientation.EAST, signName));
			case 3:
				signSpawners.add(new SignPost(position.getX() - 3, position.getY(), position.getZ() + size.getZ() / 2 + 1, SignPost.Orientation.WEST, signName));
				signSpawners.add(new SignPost(position.getX() - 3, position.getY(), position.getZ() + size.getZ() / 2 - 1, SignPost.Orientation.WEST, signName));
			case 2:
				signSpawners.add(new SignPost(position.getX() + size.getX() / 2 - 1, position.getY(), position.getZ() + size.getZ() + 2, SignPost.Orientation.SOUTH, signName));
				signSpawners.add(new SignPost(position.getX() + size.getX() / 2 + 1, position.getY(), position.getZ() + size.getZ() + 2, SignPost.Orientation.SOUTH, signName));
			case 1:
				signSpawners.add(new SignPost(position.getX() + size.getX() / 2 - 1, position.getY(), position.getZ() - 3, SignPost.Orientation.NORTH, signName));
				signSpawners.add(new SignPost(position.getX() + size.getX() / 2 + 1, position.getY(), position.getZ() - 3, SignPost.Orientation.NORTH, signName));
		}
		return signSpawners;
	}
	protected LinkedList<Primitive> prepareMobs() {
	LinkedList<Primitive> prepMob = new LinkedList<>();
		prepMob.add(new Mob(position.getX() + size.getX()/2, position.getY() + 2, position.getZ() + size.getZ()/2, innerBuildable.getName()));
	return prepMob;
	}

}
