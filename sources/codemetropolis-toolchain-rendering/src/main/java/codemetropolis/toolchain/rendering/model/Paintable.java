package codemetropolis.toolchain.rendering.model;

import codemetropolis.toolchain.rendering.model.pattern.Pattern;

public class Paintable {

	protected Pattern fill;
	protected Pattern stroke;
	
	public Paintable(Pattern fill, Pattern stroke) {
		this.fill = fill;
		this.stroke = stroke;
	}

	public Pattern getFill() {
		return fill;
	}

	public Pattern getStroke() {
		return stroke;
	}
	
}
