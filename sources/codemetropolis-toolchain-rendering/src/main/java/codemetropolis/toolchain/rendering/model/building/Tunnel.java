package codemetropolis.toolchain.rendering.model.building;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.control.WorldBuilder;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.util.Orientation;

public class Tunnel extends Linking {
	
	private List<SolidBox> lighting;
	
	public Tunnel(Buildable innerBuildable) throws RenderingException {

		super(innerBuildable);
		
		if ( innerBuildable.getType() != Type.TUNNEL ) {
			throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());
		}
		
		this.height = 4;
		this.width = 2;
		
		this.level = WorldBuilder.TUNNEL_LEVEL - this.getHeight();
		
		prepareLinking(new BasicBlock[][][] { { { new BasicBlock( "minecraft:air" ) } } });
		
		lighting = this.prepareLighting();
	}

	public int calculateHeight(Buildable buildable) {
		return buildable.getPositionY() - level + this.height;
	}
	
	public Point calculateStepPosition(boolean isTarget) {
		Point stepPosition;
		
		if(!isTarget) {
			if ("NORTH".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), level, position.getZ() + size.getZ() - adjustSize(this.width, MIN_SIZE));
			} else if ("SOUTH".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), level, position.getZ());
			} else if ("WEST".equals(this.orientation)) {
				stepPosition  = new Point(position.getX() + size.getX() - adjustSize(this.width, MIN_SIZE), level, position.getZ());
			} else {
				stepPosition  = new Point(position.getX(), level, position.getZ());
			}
		} else {
			if ("NORTH".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), level, position.getZ());
			} else if ("SOUTH".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), level, position.getZ() + size.getZ() - adjustSize(this.width, MIN_SIZE));
			} else if ("WEST".equals(this.orientation)) {
				stepPosition  = new Point(position.getX(), level, position.getZ());
			} else {
				stepPosition  = new Point(position.getX()+ size.getX() - adjustSize(this.width, MIN_SIZE), level, position.getZ());
			}
		}
		
		return stepPosition;
	}
	
	
	protected List<SolidBox> prepareLighting() {
		// NOTE (wyvick) For now there is a redstone lamp line
		// in each tunnel with redstone blocks below them.
		
		// TODO (wyvick) Since this is a SolidBox implementation,
		// both the length and the width of the lamp line
		// are cut by one block (to ensure that it is only a single line).
		// Row implementation may work better as we would not need to cut
		// and we could specify pattern with it as well,
		// but orientation may cause a problem.
		// (One possible solution is that we check whether the tunnel has
		// North-South or West-East orientation,
		// maybe by using width and length values.)
		
		// redstone lamps (single line in the middle of the tunnel floor)
		
		List<SolidBox> lighting = new ArrayList<SolidBox>();
		lighting.add(new SolidBox(
				new Point(position.getX() + 1, this.level - 1, position.getZ() + 1),
				new Point(size.getX() - 2, 1, size.getZ() - 2),
				new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:lit_redstone_lamp" ), } } } ),
				new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:lit_redstone_lamp" ), } } } ),
				Orientation.NearX
		));
		
		// redstone blocks under lamps
		lighting.add(new SolidBox(
					new Point(position.getX() + 1, this.level - 2, position.getZ() + 1),
					new Point(size.getX() - 2, 1, size.getZ() - 2),
					new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:redstone_block" ), } } } ),
					new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:redstone_block" ), } } } ),
					Orientation.NearX
		));
		primitives.addAll(lighting);
		return lighting;
	}

	public List<SolidBox> getLighting() {
		return lighting;
	}

}
