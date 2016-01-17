package codemetropolis.toolchain.rendering.model.pattern;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class YSplitPattern extends SplitPattern {

	public YSplitPattern(int limit, Pattern nearPatter, Pattern farPattern) {
		super(limit, nearPatter, farPattern);
	}

	@Override
	public BasicBlock applyTo(Point position, PositionModification positionModification) {
		if ( position.getY() > limit ) {
			return farPattern.applyTo( position, positionModification );
		} else {
			return nearPattern.applyTo( position, positionModification );
		}
	}

}
