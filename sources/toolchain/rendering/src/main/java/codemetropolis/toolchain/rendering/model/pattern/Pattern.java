package codemetropolis.toolchain.rendering.model.pattern;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.BlockType;

public interface Pattern {
	BlockType applyTo(Point position, PositionModification positionModification);
}
