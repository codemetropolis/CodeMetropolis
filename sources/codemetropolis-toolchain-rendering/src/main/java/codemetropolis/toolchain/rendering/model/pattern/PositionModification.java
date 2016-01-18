package codemetropolis.toolchain.rendering.model.pattern;

import codemetropolis.toolchain.commons.cmxml.Point;

public interface PositionModification {
	public Point apply(Point original, Point size);
}
