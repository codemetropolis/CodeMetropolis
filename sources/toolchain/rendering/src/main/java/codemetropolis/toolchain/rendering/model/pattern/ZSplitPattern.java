package codemetropolis.toolchain.rendering.model.pattern;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class ZSplitPattern extends SplitPattern {

	public ZSplitPattern(int limit, Pattern nearPatter, Pattern farPattern) {
		super(limit, nearPatter, farPattern);
	}

	@Override
	public BasicBlock applyTo(Point position, PositionModification positionModification) {
		if ( position.getZ() > limit ) {
			return farPattern.applyTo( position, positionModification );
		} else {
			return nearPattern.applyTo( position, positionModification );
		}
	}

}
