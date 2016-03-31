package codemetropolis.toolchain.commons.util;

import java.util.ResourceBundle;

public final class Settings {

	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("settings");
	
	private Settings() {}
	
	public static String get(String key) {
		return resourceBundle.getString(key);
	}
	
}
