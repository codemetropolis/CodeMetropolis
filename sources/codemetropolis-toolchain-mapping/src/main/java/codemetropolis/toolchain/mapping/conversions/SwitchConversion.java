package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Limit;
import codemetropolis.toolchain.mapping.model.Parameter;

public class SwitchConversion extends Conversion {

	public static final String NAME = "switch";
	
	@Override
	public Object apply(Object value, Limit limit) {
		String defaultValue = null;
		for(Parameter p : parameters) {
			if(p.getName().equals("default")) {
				defaultValue = p.getValue();
				break;
			}
		}
		
		for(Parameter p : parameters) {
			if(p.getName().matches("^value_.*")) {
				if(p.getName().split("value_")[1].equals(value)) return p.getValue();
			}
		}
		return defaultValue;
	}

}
