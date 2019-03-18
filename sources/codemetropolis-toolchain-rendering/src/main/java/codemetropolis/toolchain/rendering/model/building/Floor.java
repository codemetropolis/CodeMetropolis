package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RandomPattern;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.Door;
import codemetropolis.toolchain.rendering.model.primitive.EmptyBox;
import codemetropolis.toolchain.rendering.model.primitive.Row;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.model.primitive.WallSign;
import codemetropolis.toolchain.rendering.util.Character;
import codemetropolis.toolchain.rendering.util.Colour;
import codemetropolis.toolchain.rendering.util.Orientation;

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
	}
	
	protected void prepareDoor() {
		BasicBlock _red = BasicBlock.get( "minecraft:redstone_block" );
		BasicBlock _lgt = BasicBlock.get( "minecraft:lit_redstone_lamp" );
		BasicBlock _rwl = BasicBlock.get( "minecraft:wool", Colour.RED);
		BasicBlock _gwl = BasicBlock.get( "minecraft:wool", Colour.LIME);
		BasicBlock _bwl = BasicBlock.get( "minecraft:wool", Colour.LIGHT_BLUE);
		BasicBlock _ywl = BasicBlock.get( "minecraft:wool", Colour.YELLOW);
		primitives.add(
			new SolidBox(
				position.translate( new Point( center.getX() - 1, 0, 0 ) ), new Point( 3, 4, 1 ),
				new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.get( "minecraft:air") } } } ),
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
				new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.get( "minecraft:air") } } } ),
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
				new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.get( "minecraft:air") } } } ),
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
				new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.get( "minecraft:air") } } } ),
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
		BasicBlock _air = BasicBlock.get( (short)  0);
		BasicBlock _str = BasicBlock.get( (short)  1);
		BasicBlock _cre = BasicBlock.get( (short) 85);

		// Use the topBlock, since that's (usually) a fence too
		if(innerBuildable.hasAttribute("external_character"))
			_cre = Character.getTopBlock(innerBuildable.getAttributeValue("external_character").toLowerCase(), _cre);
		
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
				new RepeationPattern( new BasicBlock[][][] { { { _cre } } } ),
				Orientation.NearY ) );
	}
	
	protected void prepareWalls() {
		RepeationPattern _bottomFill;
		RepeationPattern _topFill;
		RandomPattern _sideFill;
		RepeationPattern _stroke;
		BasicBlock _sideBlock;
		BasicBlock _strcBlock;
		
		if(innerBuildable.hasAttribute( "character" )){
			String str = innerBuildable.getAttributeValue("character").toLowerCase();
			_sideBlock = Character.getBlock(str);
		} else {
			_sideBlock = BasicBlock.get( "minecraft:wool", Colour.MAGENTA);
		}
		
		if(innerBuildable.hasAttribute( "external_character" )){
			String str = innerBuildable.getAttributeValue("external_character").toLowerCase();
			_strcBlock = Character.getBlock(str);
			_topFill = new RepeationPattern( new BasicBlock[][][] { { { Character.getTopBlock(str) } } } );
			_bottomFill = new RepeationPattern( new BasicBlock[][][] { { { Character.getBlock(str) } } } );
			_stroke = new RepeationPattern( new BasicBlock[][][] { { { Character.getBlock(str) } } } );
		} else {
			_strcBlock = BasicBlock.get( "minecraft:wool", 10 );
			_topFill = new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.get( "minecraft:wool", Colour.MAGENTA) } } } );
			_bottomFill = new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.get( "minecraft:wool", Colour.MAGENTA ) } } } );
			_stroke = new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.get( "minecraft:wool", Colour.BLACK ) } } } );
		}
		
		RandomPattern _fallbackPattern = new RandomPattern( new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.NonBlock } } } ) );
		_fallbackPattern.add( new RepeationPattern( new BasicBlock[][][] { { { BasicBlock.get( "minecraft:fence" ) } } } ), .5 );
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
		BasicBlock torch = BasicBlock.get((short) 50, data);
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

}
