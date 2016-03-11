package codemetropolis.toolchain.commons.cdf;

import java.util.Map;

public abstract class CdfConverter {
	
	private Map<String, String> params;
	
	protected CdfConverter(Map<String, String> params) {
		this.params = params;
	}
	
	protected String getParameter(String key) {
		return params.get(key);
	}
	
	public abstract CdfTree createElements(String source);
	
}
