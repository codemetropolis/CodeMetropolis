package codemetropolis.toolchain.mapping.conversions;

import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.mapping.model.Limit;
import codemetropolis.toolchain.mapping.model.Parameter;

public class QuantizationConversion extends Conversion {

	@Override
	public Object apply(String value, Limit limit) {
		double valueDouble = Double.parseDouble(value);
		double distance = limit.getMax() - limit.getMin();
		
		Map<Integer, String> levelsMap = new HashMap<>();
		
		for(Parameter p : parameters) {
			if(p.getName().matches("level[0-9]+")) {
				int num = Integer.parseInt(p.getName().substring(5));
				levelsMap.put(num, p.getValue());
			}
		}
		
		String[] levels = levelsMap.values().toArray(new String[0]);
		
		double step = distance / levels.length;
		
		for(int i = 0; i < levels.length; i++) {
			double levelLimit = (i + 1) * step + limit.getMin();
			if(valueDouble <= levelLimit) {
				return levels[i];
			}
		}
		
		return null;
	}
	
}
