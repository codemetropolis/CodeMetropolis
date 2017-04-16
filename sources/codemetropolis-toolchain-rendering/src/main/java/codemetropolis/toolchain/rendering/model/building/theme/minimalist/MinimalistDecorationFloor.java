package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.building.DecorationFloor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

public class MinimalistDecorationFloor extends DecorationFloor {

	public MinimalistDecorationFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);
	}
	
	@Override
	protected void prepareCeiling() {
		primitives.add(
			new SimpleBox(
				position,
				new Point( size.getX(), 1, size.getZ() ),
				new RepeationPattern( new BasicBlock[][][] { { { MinimalistBlocks.PILLAR } } } ),
				Orientation.NearY ) );
	}
	
	@Override
	protected void prepareRoof() {}

}
