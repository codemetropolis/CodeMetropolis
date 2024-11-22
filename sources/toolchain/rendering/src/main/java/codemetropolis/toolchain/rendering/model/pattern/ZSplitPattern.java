package codemetropolis.toolchain.rendering.model.pattern;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.BlockType;

public class ZSplitPattern extends SplitPattern {

	public ZSplitPattern(int limit, Pattern nearPatter, Pattern farPattern) {
		super(limit, nearPatter, farPattern);
	}

	@Override
	public BlockType applyTo(Point position, PositionModification positionModification) {
		if ( position.getZ() > limit ) {
			return farPattern.applyTo( position, positionModification );
		} else {
			return nearPattern.applyTo( position, positionModification );
		}
	}

}
