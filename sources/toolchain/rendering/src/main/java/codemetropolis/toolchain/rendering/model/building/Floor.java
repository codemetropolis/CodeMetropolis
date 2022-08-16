package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RandomPattern;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.Button;
import codemetropolis.toolchain.rendering.model.primitive.Chest;
import codemetropolis.toolchain.rendering.model.primitive.Door;
import codemetropolis.toolchain.rendering.model.primitive.EmptyBox;
import codemetropolis.toolchain.rendering.model.primitive.Row;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.model.primitive.WallSign;
import codemetropolis.toolchain.rendering.util.Character;
import codemetropolis.toolchain.rendering.util.Orientation;
import codemetropolis.toolchain.rendering.util.Chance;

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

        // If we have the attribute "self-destructs" and the attribute's value is "yes", we start generating the TNT and the Ignition Points
        // Note: this will not work with any example mapping xml's, only custom ones. Use examples/mapping/sourcemeter_mapping_example_2_0_destruct_spinoff.xml for testing.
		if (innerBuildable.hasAttribute("self-destructs") && innerBuildable.getAttributeValue("self-destructs").equals("yes")) {
			prepareTNT();
			prepareIgnitionPoints();
		}
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
	
	/**
	 * Generates the Ignition Points. These points are used to activate the TNT generated. The points will spawn next to the lowest TNT's position.
	 * If one TNT gets activated by the Ignition Point, it will start a chain reaction, exploding every TNT in the range of the original blast.
	 * 
	 * There are 4 Ignition Points at every single building, which are Ignition Point Alpha at the building's North side, Ignition Point Beta at the building's West side,
	 * Ignition Point Gamma the the building's South side, and Ignition Point Delta at the building's East side, so the user can trigger the explosion at multiple places.
	 * 
	 * @return  void
	 * 
	 */
	private void prepareIgnitionPoints(  ) {
		// We only want to generate the Ignition Points at the bottom of the building (below Y level 75)
		// Also we don't want to spawn Ignition Points underground (below Y level 66)
		if (position.getY() > 75 || position.getY() <= 65) return;
		
		// Adding Ignition Point Signs
		primitives.add(new WallSign(position.getX() + size.getX() / 2 + 2, position.getY() + 2, position.getZ() - 1, WallSign.Orientation.NORTH, "Ignition Point Alpha"));
		primitives.add(new WallSign(position.getX() + size.getX() / 2 - 2, position.getY() + 2, position.getZ() + size.getZ(), WallSign.Orientation.SOUTH, "Ignition Point Gamma"));
		primitives.add(new WallSign(position.getX() - 1, position.getY() + 3 - 1, position.getZ() + size.getZ() / 2 - 2, WallSign.Orientation.WEST, "Ignition Point Beta"));
		primitives.add(new WallSign(position.getX() + size.getX(), position.getY() + 3 - 1, position.getZ() + size.getZ() / 2 + 2, WallSign.Orientation.EAST, "Ignition Point Delta"));
		
		// Generating Ignition Point Alpha
		Point point = new Point(position.getX() + size.getX() / 2 + 2, position.getY() + 2 + 1, position.getZ() - 1 + 1);
		BasicBlock[][] pattern = createIgnitionPattern( new Chest(point, Chest.Orientation.NORTH),  Button.Orientation.SOUTH.getValue(), Chest.Orientation.SOUTH.getValue() );
		
		for(int i = 0; i < pattern.length; i++) {
			primitives.add(new Row(
					new Point(point.getX(), point.getY() + i, point.getZ()),
					pattern[i].length,
					Row.Direction.SOUTH,
					pattern[i]));
		}
		
		// Generating Ignition Point Beta
		point = new Point(position.getX() - 1 + 1, position.getY() + 3, position.getZ() + size.getZ() / 2 - 2);
		pattern = createIgnitionPattern( new Chest(point, Chest.Orientation.WEST),  Button.Orientation.EAST.getValue(), Chest.Orientation.EAST.getValue() );
		
		for(int i = 0; i < pattern.length; i++) {
			primitives.add(new Row(
					new Point(point.getX(), point.getY() + i, point.getZ()),
					pattern[i].length,
					Row.Direction.EAST,
					pattern[i]));
		}
		
		// Generating Ignition Point Gamma
		point = new Point(position.getX() + size.getX() / 2 - 2, position.getY() + 3, position.getZ() + size.getZ() - 1);
		pattern = createIgnitionPattern( new Chest(point, Chest.Orientation.SOUTH),  Button.Orientation.NORTH.getValue(), Chest.Orientation.NORTH.getValue() );
				
		for(int i = 0; i < pattern.length; i++) {
			primitives.add(new Row(
					new Point(point.getX(), point.getY() + i, point.getZ()),
					pattern[i].length,
					Row.Direction.NORTH,
					pattern[i]));
		}
		
		// Generating Ignition Point Delta
		point = new Point(position.getX() + size.getX() - 1, position.getY() + 3, position.getZ() + size.getZ() / 2 + 2);
		pattern = createIgnitionPattern( new Chest(point, Chest.Orientation.EAST),  Button.Orientation.WEST.getValue(), Chest.Orientation.WEST.getValue() );
					
		for(int i = 0; i < pattern.length; i++) {
			primitives.add(new Row(
					new Point(point.getX(), point.getY() + i, point.getZ()),
					pattern[i].length,
					Row.Direction.WEST,
					pattern[i]));
		}
	}
	
	private void prepareTorches( ) {
		
		if(!innerBuildable.hasAttribute( "torches" )) return;
		
		int numberOfTorches = Integer.parseInt(innerBuildable.getAttributeValue("torches"));
		BasicBlock[] pattern;
		
		pattern = createTorchPattern(numberOfTorches, 3);
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
		
		pattern = createTorchPattern(numberOfTorches, 4);
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
		
		pattern = createTorchPattern(numberOfTorches, 1);
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
		
		pattern = createTorchPattern(numberOfTorches, 2);
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
	
	private BasicBlock[] createTorchPattern(int number, int data) {
		BasicBlock[] pattern = null;
		BasicBlock torch = new BasicBlock((short) 50, data);
		BasicBlock space = BasicBlock.NonBlock;
		
		switch(number) {
			case 0:
				pattern = new BasicBlock[] { space };
				break;
			case 1:
				pattern = new BasicBlock[] { torch, space, space, space, space };
				break;
			case 2:
				pattern = new BasicBlock[] { torch, space, space, space };
				break;
			case 3:
				pattern = new BasicBlock[] { torch, space, space };
				break;
			case 4:
				pattern = new BasicBlock[] { torch, space };
				break;
			case 5:
				pattern = new BasicBlock[] { torch };
				break;
		}
		return pattern;
	}
	
	
/**
 * This method creates the Ignition Pattern's base schematic, which goes like this:
 * 
 *  C
 *  H
 * PDB
 * 
 * C - Chest
 * H - Hopper
 * P - Pressure Plate
 * D - Dropper
 * B - Button
 * 
 * We will use this method to generate the Ignition Points in {@link #prepareIgnitionPoints() Ignition Point Creation }.
 * 
 * @param   chest   gives the Chest for the Ignition Point creation. This is required because of the Chest's orientation and content.
 * @param   buttonOrientation   gives the Button's orientation. This is required because we don't want floating buttons everywhere.
 * @param   dropperOrientation  gives the Dropper's orientation. This is required, because regarding which Ignition Point we're generating, the dropper needs
 *                              to face a different way, and it always has to face the pressure plate.
 * 
 * @return  the pattern in a two-dimensional array (because the pattern has width and height), ready for generation.
 * 
 * 
 */
	
	private BasicBlock[][] createIgnitionPattern( Chest chest, int buttonOrientation, int dropperOrientation ) {
		
		
		BasicBlock[][] pattern = null;
		BasicBlock space = BasicBlock.NonBlock;
		BasicBlock pressurePlate = new BasicBlock((short) 72);
		BasicBlock button = new BasicBlock((short) 77, buttonOrientation);
		BasicBlock hopper = new BasicBlock((short) 154);
		BasicBlock dispenser = new BasicBlock((short) 158, dropperOrientation);
		
		pattern = new BasicBlock[][] {{ pressurePlate, dispenser, button }, {space, hopper, space}, {space, chest.chestToBlock(), space}};
		return pattern;
	}
	
	/**
	 * Generates the TNT using the torch pattern. If no torches are present, TNT's can't generate. This was the easiest way to incorporate the TNT
	 * generation using pretty much the same code as generating the torches.
	 * 
	 * @return  void 
	 * 
	 */
	
	private void prepareTNT( ) {
		
		// The algorithm only works if there are torches present, issue to fix this?
		if(!innerBuildable.hasAttribute( "torches" )) return;
		
		int numberOfTorches = Integer.parseInt(innerBuildable.getAttributeValue("torches"));
		BasicBlock[] pattern;
		
		for (int i = 1; i < size.getY() - 3; i += 3) {
			pattern = createTNTPattern(numberOfTorches);
			primitives.add(new Row(
					new Point(position.getX() + size.getX() / 2 + 2, position.getY() + 2 + i, position.getZ() + 1),
					size.getX() / 2 - 2,
					Row.Direction.WEST,
					pattern));
			
			primitives.add(new Row(
					new Point(position.getX() + size.getX() / 2 - 2, position.getY() + 2 + i, position.getZ() + 1),
					size.getX() / 2 - 2,
					Row.Direction.EAST,
					pattern));
			
			pattern = createTNTPattern(numberOfTorches);
			primitives.add(new Row(
					new Point(position.getX() + size.getX() / 2 + 2, position.getY() + 2 + i, position.getZ()  + size.getZ() - 2),
					size.getX() / 2 - 2,
					Row.Direction.WEST,
					pattern));
			
			primitives.add(new Row(
					new Point(position.getX() + size.getX() / 2 - 2, position.getY() + 2 + i, position.getZ()  + size.getZ() - 2),
					size.getX() / 2 - 2,
					Row.Direction.EAST,
					pattern));
			
			pattern = createTNTPattern(numberOfTorches);
			primitives.add(new Row(
					new Point(position.getX() + 1, position.getY() + 2 + i, position.getZ() + size.getZ() / 2 + 2),
					size.getZ() / 2 - 2,
					Row.Direction.NORTH,
					pattern));
			
			primitives.add(new Row(
					new Point(position.getX() + 1, position.getY() + 2 + i, position.getZ() + size.getZ() / 2 - 2),
					size.getZ() / 2 - 2,
					Row.Direction.SOUTH,
					pattern));
			
			pattern = createTNTPattern(numberOfTorches);
			primitives.add(new Row(
					new Point(position.getX() + size.getX() - 2, position.getY() + 2 + i, position.getZ() + size.getZ() / 2 + 2),
					size.getZ() / 2 - 2,
					Row.Direction.NORTH,
					pattern));
			
			primitives.add(new Row(
					new Point(position.getX() + size.getX() - 2, position.getY() + 2 + i, position.getZ() + size.getZ() / 2 - 2),
					size.getZ() / 2 - 2,
					Row.Direction.SOUTH,
					pattern));
		}
	}
	
	/**
	 * Creates the TNT pattern schematic in a one-dimensional array (because the schematic only has width).
	 * Contains an easter egg. A TNT has a 10% chance to be "booby trapped". This means if this piece of TNT
	 * is punched with the player's hand, it will instantly trigger, without the use of any base triggering device Minecraft offers.
	 * 
	 * @param   number  used by the torch generating algorithm. The number contains how many TNT's need to be generated in a row.
	 * @return  the pattern in a one-dimensional array.
	 * 
	 */
	
	private BasicBlock[] createTNTPattern(int number) {
		BasicBlock[] pattern = null;
		BasicBlock tnt = new BasicBlock((short) 46);
		BasicBlock space = BasicBlock.NonBlock;
		
		// 10 Percent Chance that the TNT is booby trapped, meaning if you punch it, it will instantly trigger
		Chance chance = new Chance(10);
		if (chance.kick()) {
			tnt = new BasicBlock((short) 46, 1);
		}
		
		switch(number) {
			case 0:
				pattern = new BasicBlock[] { space };
				break;
			case 1:
				pattern = new BasicBlock[] { tnt, space, space, space, space };
				break;
			case 2:
				pattern = new BasicBlock[] { tnt, space, space, space };
				break;
			case 3:
				pattern = new BasicBlock[] { tnt, space, space };
				break;
			case 4:
				pattern = new BasicBlock[] { tnt, space };
				break;
			case 5:
				pattern = new BasicBlock[] { tnt };
				break;
		}
		
		return pattern;
	}

}
