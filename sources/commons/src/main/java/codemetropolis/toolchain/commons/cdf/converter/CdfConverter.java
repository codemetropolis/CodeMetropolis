package codemetropolis.toolchain.commons.cdf.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public abstract class CdfConverter {
	
	private Map<String, String> params;
	private List<ConverterEventListener> listeners = new ArrayList<>();
	
	protected CdfConverter(Map<String, String> params) {
		this.params = params;
	}
	
	protected String getParameter(String key) {
		return params.get(key);
	}
	
	public abstract CdfTree createElements(String source) throws CodeMetropolisException;
	
	protected void fireConverterEvent(String message) {
		for(ConverterEventListener l : listeners) {
			l.onConverterEvent(new ConverterEvent(message));
		}
	}
	
	public void addConverterEventListener(ConverterEventListener listener) {
		listeners.add(listener);
	}
	
	public void removeConverterEventListener(ConverterEventListener listener) {
		listeners.remove(listener);
	}
	
}
