package codemetropolis.toolchain.mapping.control;

import org.apache.commons.collections4.map.MultiKeyMap;

import codemetropolis.toolchain.mapping.model.Limit;

public class LimitController {
	
	private MultiKeyMap<String, Limit> limits = new MultiKeyMap<>();
	
	public void add(String sourceName, String sourceFrom, double value) {
		if(!limits.containsKey(sourceName, sourceFrom)) {
			limits.put(sourceName, sourceFrom, new Limit());
		}
		Limit limit = limits.get(sourceName, sourceFrom);
		limit.add(value);
	}

	public Limit getLimit(String sourceName, String sourceFrom) {
		return limits.get(sourceName, sourceFrom);
	}

}
