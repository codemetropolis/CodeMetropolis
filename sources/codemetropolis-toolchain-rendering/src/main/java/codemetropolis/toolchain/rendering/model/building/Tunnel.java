package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.util.Orientation;

public class Tunnel extends Building {
	
	public static final int TUNNEL_WIDTH = 2;
	public static final int TUNNEL_HEIGHT = 5;
	public static final String TUNNEL_ATTRIBUTE_TARGET = "target";
	
	public Tunnel(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
		prepareTunnel();
	}
	
	protected void prepareTunnel() {
		
		primitives.add(
				new SolidBox(
					new Point(position.getX(), position.getY()-2, position.getZ()),
					new Point(size.getX(), TUNNEL_HEIGHT, size.getZ()),
					new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:sandstone" ) } } } ),
					new RepeationPattern( new BasicBlock[][][] { { { new BasicBlock( "minecraft:sandstone" ) } } } ),
					Orientation.NearX ) );
		
	}
}
