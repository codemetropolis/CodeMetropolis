package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.control.WorldBuilder;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Bridge extends Linking {
	
	public Bridge(Buildable innerBuildable) throws RenderingException {

		super(innerBuildable);
		
		if ( innerBuildable.getType() != Type.BRIDGE ) {
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());
		}
		
		this.width = 4;
		this.height = 2;
		
		this.level = WorldBuilder.MAX_HEIGHT;
		
		prepareLinking(new BasicBlock[][][] { { { new BasicBlock( "minecraft:stone" ) } } });
	}
	
	public int calculateHeight(Buildable buildable) {

		return level - WorldBuilder.GROUND_LEVEL;
	}
	
	public Point calculateStepPosition(boolean isTarget) {
		Point stepPosition;
		
		if(!isTarget) {
			if ("NORTH".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), WorldBuilder.GROUND_LEVEL, position.getZ() + size.getZ() - adjustSize(this.width, MIN_SIZE));
			} else if ("SOUTH".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), WorldBuilder.GROUND_LEVEL, position.getZ());
			} else if ("WEST".equals(this.orientation)) {
				stepPosition  = new Point(position.getX() + size.getX() - adjustSize(this.width, MIN_SIZE), WorldBuilder.GROUND_LEVEL, position.getZ());
			} else {
				stepPosition  = new Point(position.getX(), WorldBuilder.GROUND_LEVEL, position.getZ());
			}
		} else {
			if ("NORTH".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), WorldBuilder.GROUND_LEVEL, position.getZ());
			} else if ("SOUTH".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), WorldBuilder.GROUND_LEVEL, position.getZ() + size.getZ() - adjustSize(this.width, MIN_SIZE));
			} else if ("WEST".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), WorldBuilder.GROUND_LEVEL, position.getZ());
			} else {
				stepPosition  = new Point(position.getX()+ size.getX() - adjustSize(this.width, MIN_SIZE), WorldBuilder.GROUND_LEVEL, position.getZ());
			}
		}
		
		return stepPosition;
	}
}
