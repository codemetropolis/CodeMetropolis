package codemetropolis.toolchain.commons.util;

import java.util.ResourceBundle;

public final class Resources {

	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");
	
	private Resources() {}
	
	public static String get(String key) {
		return resourceBundle.getString(key);
	}
	
}
