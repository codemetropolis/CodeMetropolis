package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RandomPattern;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.*;
import codemetropolis.toolchain.rendering.util.Character;
import codemetropolis.toolchain.rendering.util.Orientation;

import java.util.LinkedList;

public class Floor extends Building {

	public Floor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
		
		if ( innerBuildable.getType()!= Type.FLOOR && innerBuildable.getType() != Type.CELLAR )
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());

		prepareWalls();
		prepareStairs();
		prepareDoor();
		prepareSigns();
		prepareTorches();
		prepareLanterns();
	}
	
	protected void prepareDoor() {
		BasicBlock _red = new BasicBlock( "minecraft:redstone_block" );
		BasicBlock _lgt = new BasicBlock( "minecraft:lit_redstone_lamp" );
		BasicBlock _rwl = new BasicBlock( "minecraft:wool", 14 );
		BasicBlock _gwl = new BasicBlock( "minecraft:wool", 5 );
		BasicBlock _bwl = new BasicBlock( "minecraft:wool", 3 );
		BasicBlock _ywl = new BasicBlock( "minecraft:wool", 4 );
		primitives.add(
			new SolidBox(
				position.translate( new Point( center.getX() - 1, 0, 0 ) ), new Point( 3, 4, 1 ),
				new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:air", 2 ) } } } ),
				new RepeationPattern(
					new BasicBlock[][][]
					{
						{
							{ _rwl },
							{ _lgt },
							{ _red },
							{ _rwl }
						}
					} ),
				Orientation.NearX )
			);
		primitives.add(
			new SolidBox(
				position.translate( new Point( center.getX() - 1, 0, size.getZ() - 1 ) ), new Point( 3, 4, 1 ),
				new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:air", 2 ) } } } ),
				new RepeationPattern(
					new BasicBlock[][][]
					{
						{
							{ _gwl },
							{ _lgt },
							{ _red },
							{ _gwl }
						}
					} ),
				Orientation.NearX )
			);
		primitives.add(
			new SolidBox(
				position.translate( new Point( 0, 0, center.getZ()-1 ) ), new Point( 1, 4, 3 ),
				new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:air", 2 ) } } } ),
				new RepeationPattern(
					new BasicBlock[][][]
					{
						{
							{ _bwl },
							{ _lgt },
							{ _red },
							{ _bwl }
						}
					} ),
				Orientation.NearX )
			);
		primitives.add(
			new SolidBox(
				position.translate( new Point( size.getX()-1, 0, center.getZ() - 1 ) ), new Point( 1, 4, 3 ),
				new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:air", 2 ) } } } ),
				new RepeationPattern(
					new BasicBlock[][][]
					{
						{
							{ _ywl },
							{ _lgt },
							{ _red },
							{ _ywl }
						}
					} ),
				Orientation.NearX )
			);
		
		primitives.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ(), Door.Orientation.SOUTH));
		primitives.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ()  + size.getZ() - 1, Door.Orientation.NORTH));
		primitives.add(new Door(position.getX(), position.getY() + 1, position.getZ() + size.getZ() / 2, Door.Orientation.EAST));
		primitives.add(new Door(position.getX() + size.getX() - 1, position.getY() + 1, position.getZ() + size.getZ() / 2, Door.Orientation.WEST));	
	}
	
	protected void prepareStairs() {
		BasicBlock _air = new BasicBlock( (short) 0 );
		BasicBlock _str = new BasicBlock( (short) 1 );
		BasicBlock _cre = new BasicBlock( (short) 85 );
		primitives.add(
			new SolidBox(
				position.translate( new Point( center.getX() - 2, 0, center.getZ() - 2 ) ),
				new Point( 5, size.getY() + 1, 5 ),
				new RepeationPattern(
					new BasicBlock[][][]
					{
						{
							{ _air, _air, _air, _air, _air },
							{ _air, _str, _air, _air, _air },
							{ _air, _air, _cre, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air }
						},
						{
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _str, _air, _air },
							{ _air, _air, _cre, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air }
						},
						{
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _str, _air },
							{ _air, _air, _cre, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air }
						},
						{
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _cre, _str, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air }
						},
						{
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _cre, _air, _air },
							{ _air, _air, _air, _str, _air },
							{ _air, _air, _air, _air, _air }
						},
						{
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _cre, _air, _air },
							{ _air, _air, _str, _air, _air },
							{ _air, _air, _air, _air, _air }
						},
						{
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _cre, _air, _air },
							{ _air, _str, _air, _air, _air },
							{ _air, _air, _air, _air, _air }
						},
						{
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _str, _cre, _air, _air },
							{ _air, _air, _air, _air, _air },
							{ _air, _air, _air, _air, _air }
						}
					} ),
				new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:fence" ) } } } ),
				Orientation.NearY ) );
	}
	
	protected void prepareWalls() {
		RepeationPattern _bottomFill;
		RepeationPattern _topFill;
		RandomPattern _sideFill;
		RepeationPattern _stroke;
		BasicBlock _sideBlock;
		BasicBlock _strcBlock;
		
		if(innerBuildable.hasAttribute( "character" ))
		{
			Character character = Character.parse(innerBuildable.getAttributeValue("character"));
			_sideBlock = character.getBlock();
			_topFill = new RepeationPattern( new BasicBlock[][][] { { { character.getTopBlock() } } } );
		} else {
			_sideBlock = new BasicBlock( "minecraft:wool", 2 );
			_topFill = new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:wool", 2 ) } } } );
		}
		
		if(innerBuildable.hasAttribute( "external_character" ))
		{
			Character externalCharacter = Character.parse(innerBuildable.getAttributeValue("external_character"));
			_bottomFill = new RepeationPattern( new BasicBlock[][][] { { { externalCharacter.getBlock() } } } );
			_strcBlock = externalCharacter.getBlock();
			_stroke = new RepeationPattern( new BasicBlock[][][] { { { externalCharacter.getBlock() } } } );
		} else {
			_bottomFill = new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:wool", 2 ) } } } );
			_strcBlock = new BasicBlock( "minecraft:wool", 10 );
			_stroke = new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:wool", 15 ) } } } );
		}
		
		RandomPattern _fallbackPattern = new RandomPattern( new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.NonBlock } } } ) );
		_fallbackPattern.add( new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:fence" ) } } } ), .5 );
		_sideFill = new RandomPattern( _fallbackPattern );
		_sideFill.add(
			new RepeationPattern(
				new BasicBlock[][][]
				{
					{
						{ _sideBlock, _sideBlock, _strcBlock, _sideBlock, _sideBlock },
						{ _sideBlock, _sideBlock, _strcBlock, _sideBlock, _sideBlock },
						{ _strcBlock, _strcBlock, _strcBlock, _strcBlock, _strcBlock },
						{ _sideBlock, _sideBlock, _strcBlock, _sideBlock, _sideBlock },
						{ _sideBlock, _sideBlock, _strcBlock, _sideBlock, _sideBlock }
					}
				} ),
			innerBuildable.hasAttribute( "completeness" )
				? Double.parseDouble( innerBuildable.getAttributeValue("completeness") )
				: 1 );
		primitives.add(
			new EmptyBox(
				position,
				size,
				_bottomFill,
				_topFill,
				_sideFill,
				_stroke,
				new Point( 1, 1, 1 ),
				new Point( 1, 1, 1 ) )
			);
	}
	
	private void prepareSigns( ) {
		//Wall signs outside
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() - 1, WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ()  + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() - 1, position.getY() + 3, position.getZ() + size.getZ() / 2, WallSign.Orientation.WEST, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX(), position.getY() + 3, position.getZ() + size.getZ() / 2, WallSign.Orientation.EAST, innerBuildable.getName()));
		//Wall signs inside
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() + 1, WallSign.Orientation.SOUTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ()  + size.getZ() - 2, WallSign.Orientation.NORTH, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + 1, position.getY() + 3, position.getZ() + size.getZ() / 2, WallSign.Orientation.EAST, innerBuildable.getName()));
		primitives.add(new WallSign(position.getX() + size.getX() - 2, position.getY() + 3, position.getZ() + size.getZ() / 2, WallSign.Orientation.WEST, innerBuildable.getName()));	
	}
	
	private void prepareTorches( ) {
		
		if(!innerBuildable.hasAttribute( "torches" )) return;
		
		int numberOfTorches = Integer.parseInt(innerBuildable.getAttributeValue("torches"));
		BasicBlock[] pattern;

		//creating pattern of the torches
		pattern = createPattern(50, numberOfTorches, 3);
		primitives.add(new Row(
				new Point(position.getX() + size.getX() / 2 + 2, position.getY() + 2, position.getZ() + 1),
				size.getX() / 2 - 2,
				Row.Direction.WEST,
				pattern));

		primitives.add(new Row(
				new Point(position.getX() + size.getX() / 2 - 2, position.getY() + 2, position.getZ() + 1),
				size.getX() / 2 - 2,
				Row.Direction.EAST,
				pattern));

		//creating pattern of the torches
		pattern = createPattern(50, numberOfTorches, 4);
		primitives.add(new Row(
				new Point(position.getX() + size.getX() / 2 + 2, position.getY() + 2, position.getZ()  + size.getZ() - 2),
				size.getX() / 2 - 2,
				Row.Direction.WEST,
				pattern));
		
		primitives.add(new Row(
				new Point(position.getX() + size.getX() / 2 - 2, position.getY() + 2, position.getZ()  + size.getZ() - 2),
				size.getX() / 2 - 2,
				Row.Direction.EAST,
				pattern));

		//creating pattern of the torches
		pattern = createPattern(50, numberOfTorches, 1);
		primitives.add(new Row(
				new Point(position.getX() + 1, position.getY() + 2, position.getZ() + size.getZ() / 2 + 2),
				size.getZ() / 2 - 2,
				Row.Direction.NORTH,
				pattern));
		
		primitives.add(new Row(
				new Point(position.getX() + 1, position.getY() + 2, position.getZ() + size.getZ() / 2 - 2),
				size.getZ() / 2 - 2,
				Row.Direction.SOUTH,
				pattern));

		//creating pattern of the torches
		pattern = createPattern(50, numberOfTorches, 2);
		primitives.add(new Row(
				new Point(position.getX() + size.getX() - 2, position.getY() + 2, position.getZ() + size.getZ() / 2 + 2),
				size.getZ() / 2 - 2,
				Row.Direction.NORTH,
				pattern));
		
		primitives.add(new Row(
				new Point(position.getX() + size.getX() - 2, position.getY() + 2, position.getZ() + size.getZ() / 2 - 2),
				size.getZ() / 2 - 2,
				Row.Direction.SOUTH,
				pattern));
		
	}

	/**
	 * The function returns the position of the door at the given side,
	 * which is specified based on the lantern's rotation
	 * @param lanternData rotation of the lantern
	 *                    = 1: the lantern faces west
	 *                    = 2: the lantern faces north
	 *                    = 3: the lantern faces east
	 *                    = 4: the lantern faces south
	 * @return int[] the array with the [xCoord,yCoord,zCoord] coordinates
	 */
	private int[] getSideDoorCoords(int lanternData) {
		int xCoord = position.getX();
		int yCoord = position.getY();
		int zCoord = position.getZ();

		switch (lanternData) {
			case 1:
				xCoord -= 1;
				zCoord += size.getZ() / 2;
				break;
			case 2:
				xCoord += size.getX() / 2;
				zCoord -= 1;
				break;
			case 3:
				xCoord += size.getX();
				zCoord += size.getZ() / 2;
				break;
			case 4:
				xCoord += size.getX() / 2;
				zCoord += size.getZ();
				break;
			default:
				throw new IllegalArgumentException("The lantern's rotation data is incorrect");
		}
		return new int[]{xCoord, yCoord, zCoord};
	}

	/**
	 * The function is responsible for the creation of the lantern attribute's
	 * design and the placement of them to a single side of the building.
	 * The function iterates from 1 to 3 which are the
	 * three layers of design where 1 is the fence 2 is the lantern and
	 * 3 is the slab
	 *
	 * @param number the density of the lanterns in 6 categories 0-5
	 * @param data the rotation of the lantern
	 * @return returns a linked list containing the "row" type primitives
	 */
	private LinkedList<Row> createLanternDesign(int number, int data) {
		int[] doorCoords = getSideDoorCoords(data);
		int xCoord = doorCoords[0];
		int yCoord = doorCoords[1];
		int zCoord = doorCoords[2];

		BasicBlock[] pumpkinPattern = createPattern(91, number, data);
		BasicBlock[] slabPattern = createPattern(126, number, 0);
		BasicBlock[] fencePattern = createPattern(85, number, 0);
		BasicBlock[] pattern = null;
		LinkedList<Row> rows = new LinkedList<>();

		//The i variable is used to determine the placement on the
		// y axis relative to the bottom of the floor
		for (int i = 1; i <= 3; i++) {
			if (i == 1) {
				pattern = fencePattern;
			}
			if (i == 2) {
				pattern = pumpkinPattern;
			}
			if (i == 3) {
				pattern = slabPattern;
			}
			//the two categories were separated based on the pairing of row directions
			// (east-west, south-north)
			if (data == 1 || data == 3) {
				rows.add(new Row(
						new Point(xCoord, yCoord + i, zCoord + 2),
						size.getZ() / 2 - 2,
						Row.Direction.NORTH,
						pattern));

				rows.add(new Row(
						new Point(xCoord, yCoord + i, zCoord - 2),
						size.getZ() / 2 - 2,
						Row.Direction.SOUTH,
						pattern));
			}

			if (data == 2 || data == 4) {
				rows.add(new Row(
						new Point(xCoord + 2, yCoord + i, zCoord),
						size.getX() / 2 - 2,
						Row.Direction.WEST,
						pattern));

				rows.add(new Row(
						new Point(xCoord - 2, yCoord + i, zCoord),
						size.getX() / 2 - 2,
						Row.Direction.EAST,
						pattern));
			}
		}
		return rows;
	}

/**
 * The method is responsible for the actual placement of the blocks
 * which is done by concatenating the returned list from @createLanternDesign
 * to the primitives.
 * */
	private void prepareLanterns() {
		if (!innerBuildable.hasAttribute( "lanterns" )) return;

		int numberOfLanterns = Integer.parseInt(innerBuildable.getAttributeValue("lanterns"));
		primitives.addAll(createLanternDesign(numberOfLanterns, 1));
		primitives.addAll(createLanternDesign(numberOfLanterns, 2));
		primitives.addAll(createLanternDesign(numberOfLanterns, 3));
		primitives.addAll(createLanternDesign(numberOfLanterns, 4));
	}

/**
 * This method is responsible for creating a pattern for objects to place.
 * The pattern will tell how densely they are placed from each other.
 *
 * @param object_id the id of the minecraft object from the blocks.csv
 * @param number the density of the lanterns in 6 categories 0-5
 * @param data the rotation of the lantern
 * @return returns an array containing BasicBlock type objects.
 * */

	private BasicBlock[] createPattern(int object_id, int number, int data) {
		BasicBlock[] pattern = null;
		BasicBlock object = new BasicBlock((short) object_id, data);
		BasicBlock space = BasicBlock.NonBlock;

		switch (number) {
			case 0:
				pattern = new BasicBlock[] { space };
				break;
			case 1:
				pattern = new BasicBlock[] { object, space, space, space, space };
				break;
			case 2:
				pattern = new BasicBlock[] { object, space, space, space };
				break;
			case 3:
				pattern = new BasicBlock[] { object, space, space };
				break;
			case 4:
				pattern = new BasicBlock[] { object, space };
				break;
			case 5:
				pattern = new BasicBlock[] { object };
				break;
			default:
				throw new IllegalArgumentException("The number argument should be between 0-5");

		}
		return pattern;
	}

}
