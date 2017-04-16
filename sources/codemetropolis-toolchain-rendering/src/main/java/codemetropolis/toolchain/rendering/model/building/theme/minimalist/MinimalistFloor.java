package codemetropolis.toolchain.rendering.model.building.theme.minimalist;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.building.Floor;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SimpleBox;
import codemetropolis.toolchain.rendering.util.Orientation;

public class MinimalistFloor extends Floor {

	public MinimalistFloor(Buildable innerBuildable) throws BuildingTypeMismatchException {
		super(innerBuildable);		
	}
	
	@Override
	protected void prepareWalls() {		
		BasicBlock[][][] section = new BasicBlock[size.getX()][1][size.getZ()];
		BasicBlock[][][] bottomSection = new BasicBlock[size.getX()][1][size.getZ()];
		for(int i = 0; i < section.length; i++) {
			for(int j = 0; j < section[0][0].length; j++) {
				section[i][0][j] = MinimalistBlocks.EMPTY_BLOCK;
				bottomSection[i][0][j] = MinimalistBlocks.EMPTY_BLOCK;
			}
		}
		for(int i = 0; i < section.length; i++) {
			section[i][0][0] = MinimalistBlocks.WALL;
			section[i][0][size.getZ() - 1] = MinimalistBlocks.WALL;
			bottomSection[i][0][0] = MinimalistBlocks.PILLAR;
			bottomSection[i][0][size.getZ() - 1] = MinimalistBlocks.PILLAR;
		}
		for(int j = 0; j < section[0][0].length; j++) {
			section[0][0][j] = MinimalistBlocks.WALL;
			section[size.getX() - 1][0][j] = MinimalistBlocks.WALL;
			bottomSection[0][0][j] = MinimalistBlocks.PILLAR;
			bottomSection[size.getX() - 1][0][j] = MinimalistBlocks.PILLAR;
		}
		section[0][0][0] = MinimalistBlocks.PILLAR;
		section[0][0][size.getZ() - 1] = MinimalistBlocks.PILLAR;
		section[size.getX() - 1][0][0] = MinimalistBlocks.PILLAR;
		section[size.getX() - 1][0][size.getZ() - 1] = MinimalistBlocks.PILLAR;
		
		primitives.add(
				new SimpleBox(
					position,
					new Point( size.getX(), 1, size.getZ()),
					new RepeationPattern( bottomSection ),
					Orientation.NearX ) );
		
		primitives.add(
				new SimpleBox(
					position.translate( new Point( 0, 1, 0) ),
					new Point( size.getX(), size.getY() - 1, size.getZ()),
					new RepeationPattern( section ),
					Orientation.NearX) );		
	}
	
	@Override
	protected void prepareStairs() {}
	
	// TODO
	@Override
	protected void prepareDoor() {}
	
	@Override
	protected void prepareSigns() {}
	
	@Override
	protected void prepareTorches() {}

}
