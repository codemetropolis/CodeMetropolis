package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.control.WorldBuilder;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.Row;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.util.Orientation;

public class Tunnel extends Building {
	
	private static final int MIN_SIZE = 2;
	private static final int TUNNEL_WIDTH = 2;
	private static final int TUNNEL_HEIGHT = 4;
	private static final String TUNNEL_ATTRIBUTE_TARGET = "target";
	
	private Orientation orientation;
	
	public Tunnel(Buildable innerBuildable) throws BuildingTypeMismatchException {
		
		if ( innerBuildable.getType() != Type.TUNNEL )
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());
		
		this.innerBuildable = innerBuildable;
		
		size = new Point(
				adjustSize(innerBuildable.getSizeX(), MIN_SIZE),
				adjustSize(innerBuildable.getSizeY(), MIN_SIZE),
				adjustSize(innerBuildable.getSizeZ(), MIN_SIZE)
				);
		
		position = new Point(
				innerBuildable.getPositionX(),
				innerBuildable.getPositionY(),
				innerBuildable.getPositionZ()
				);
		
		center = new Point(
				(int)(size.getX() * 0.5),
				(int)(size.getY() * 0.5),
				(int)(size.getZ() * 0.5)
				);
		
		prepareTunnel();
		prepareStairs();
		// prepareTorches(); ?
	}
	
	protected void prepareTunnel() {
		
		primitives.add(
				new SolidBox(
					new Point(position.getX(), WorldBuilder.TUNNEL_LEVEL, position.getZ()),
					new Point(size.getX(), TUNNEL_HEIGHT, size.getZ()),
					new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:air" ) } } } ),
					new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:air" ) } } } ),
					Orientation.NearX ) );
		
	}
	
	protected void prepareStairs() {
		BasicBlock _air = new BasicBlock( (short) 0 );
		BasicBlock _str = new BasicBlock( (short) 1 );
		BasicBlock _cre = new BasicBlock( (short) 85 );
		
		BasicBlock[][][] steps =
				new BasicBlock[][][]
				{
					{
						{ _str, _air, _air },
						{ _air, _cre, _air },
						{ _air, _air, _air }
					},
					{
						{ _air, _str, _air },
						{ _air, _cre, _air },
						{ _air, _air, _air }
					},
					{
						{ _air, _air, _str },
						{ _air, _cre, _air },
						{ _air, _air, _air }
					},
					{
						{ _air, _air, _air },
						{ _air, _cre, _str },
						{ _air, _air, _air }
					},
					{
						{ _air, _air, _air },
						{ _air, _cre, _air },
						{ _air, _air, _str }
					},
					{
						{ _air, _air, _air },
						{ _air, _cre, _air },
						{ _air, _str, _air }
					},
					{
						{ _air, _air, _air },
						{ _air, _cre, _air },
						{ _str, _air, _air }
					},
					{
						{ _air, _air, _air },
						{ _str, _cre, _air },
						{ _air, _air, _air }
					}
				};
		

				Point pos = calculateStepPosition();
				
		primitives.add(
			new SolidBox(
				pos,
				new Point( 3, calculateHeight(), 3 ),
				new RepeationPattern( steps ),
				new RepeationPattern( steps ),
				Orientation.NearY ) );
	}

	
	private int calculateHeight() {
		int height = WorldBuilder.GROUND_LEVEL - WorldBuilder.TUNNEL_LEVEL + TUNNEL_HEIGHT + 1;
		for(Buildable b : this.innerBuildable.getParent().getChildren()) {
			if(b.getType() == Buildable.Type.CELLAR && height > b.getPositionY() - WorldBuilder.TUNNEL_LEVEL + TUNNEL_HEIGHT) {
				height = b.getPositionY() - WorldBuilder.TUNNEL_LEVEL + TUNNEL_HEIGHT;
			}
		}
		
		return height;
	}
	
	private Point calculateStepPosition() {
		Point stepPosition;
		if(this.innerBuildable.hasAttribute(TUNNEL_ATTRIBUTE_TARGET)) {
			stepPosition = new Point(position.getX(), WorldBuilder.TUNNEL_LEVEL, position.getZ());
		} else {
			if(size.getX() == adjustSize(TUNNEL_WIDTH, MIN_SIZE)) {
				stepPosition  = new Point(position.getX(), WorldBuilder.TUNNEL_LEVEL, position.getZ() + size.getZ());
			} else {
				stepPosition  = new Point(position.getX() + size.getX(), WorldBuilder.TUNNEL_LEVEL, position.getZ());
			}
		}
		return stepPosition;
	}
}
