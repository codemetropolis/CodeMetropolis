package codemetropolis.toolchain.rendering.model.pattern;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.BlockType;

public abstract class SplitPattern implements Pattern {

	protected int limit;
	protected Pattern nearPattern;
	protected Pattern farPattern;
	
	public SplitPattern(int limit, Pattern nearPattern, Pattern farPattern) {
		super();
		this.limit = limit;
		this.nearPattern = nearPattern;
		this.farPattern = farPattern;
	}

	@Override
	public abstract BlockType applyTo(Point position, PositionModification positionModification);

	public int getLimit() {
		return limit;
	}

	public Pattern getNearPatter() {
		return nearPattern;
	}

	public Pattern getFarPattern() {
		return farPattern;
	}
	
}
