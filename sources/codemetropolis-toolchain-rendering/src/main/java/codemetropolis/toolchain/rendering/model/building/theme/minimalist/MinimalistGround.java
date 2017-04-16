package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.building.Ground;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

public class MinimalistGround extends Ground {

	public MinimalistGround(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}
	
	@Override
	protected void prepareBase( ) {		
		primitives.add(
			new SimpleBox(
				position,
				new Point( size.getX(), 1, size.getZ() ),
				new RepeationPattern( new BasicBlock[][][]{ { { MinimalistBlocks.BASE } } } ),
				Orientation.NearX ) );
	}
	
	@Override
	protected void prepareSigns( ) {}

}
