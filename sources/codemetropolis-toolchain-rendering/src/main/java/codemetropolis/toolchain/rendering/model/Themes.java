package codemetropolis.toolchain.rendering.model;

/**
 * An enum class for storing themes.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public enum Themes {

	BASIC("basic"),
	MINIMALIST("minimalist"),
	RAILWAY("railway"),
	TOWN("town");

	private final String theme;

	private Themes(String theme) {
		this.theme = theme;
	}

	public String toString() {
		return this.theme;
	}

}
