package codemetropolis.toolchain.rendering.model;

public enum Themes {
	
	BASIC ( "basic" ),
	MINIMALIST ( "minimalist" );
	
	private final String theme;
	
	private Themes(String theme) {
		this.theme = theme;
	}
	
	public String toString() {
		return this.theme;
	}	
	
}
