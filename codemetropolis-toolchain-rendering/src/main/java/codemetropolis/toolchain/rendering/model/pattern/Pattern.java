package codemetropolis.toolchain.rendering.model.pattern;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public interface Pattern {
	BasicBlock applyTo(Point position, PositionModification positionModification);
}
