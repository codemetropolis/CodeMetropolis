package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Limit;
import codemetropolis.toolchain.mapping.model.Parameter;

public class MultiplyConversion extends Conversion {

	@Override
	public Object apply(String value, Limit limit) {
		double multiplier = 1;
		for(Parameter p : parameters) {
			if(p.getName().matches("multiplier")) {
				multiplier = Double.parseDouble(p.getValue());
			}
		}
		return Double.parseDouble(value) * multiplier;
	}	
	
}
