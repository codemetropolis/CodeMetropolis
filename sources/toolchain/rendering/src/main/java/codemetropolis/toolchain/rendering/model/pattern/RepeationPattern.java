package codemetropolis.toolchain.rendering.model.pattern;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class RepeationPattern implements Pattern {

	BasicBlock[][][] pattern;
	
	public RepeationPattern(BasicBlock[][][] pattern) {
		super();
		this.pattern = pattern;
	}

	@Override
	public BasicBlock applyTo(Point position, PositionModification positionModification) {
		Point size = new Point(
				pattern.length,
				pattern[0].length,
				pattern[0][0].length
				);
		Point modifiedPosition = positionModification.apply(position, size);
		return pattern
				[size.getX() - 1 - modifiedPosition.getX() % size.getX()]
				[size.getY() - 1 - modifiedPosition.getY() % size.getY()]
				[size.getZ() - 1 - modifiedPosition.getZ() % size.getZ()];
	}

}
