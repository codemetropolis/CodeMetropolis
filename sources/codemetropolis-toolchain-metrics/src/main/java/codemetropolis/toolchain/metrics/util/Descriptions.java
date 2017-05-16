package codemetropolis.toolchain.metrics.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Descriptions {

	private static ResourceBundle descriptions = ResourceBundle.getBundle("descriptions");
	
	private Descriptions() { }
		
	public static String get(String key) {
	    try {
	    	return descriptions.getString(key);
	    } catch (MissingResourceException e) {
	    	return null;
	    }
	}
	
}
