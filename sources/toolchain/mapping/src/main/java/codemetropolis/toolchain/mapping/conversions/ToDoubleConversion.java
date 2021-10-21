package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Limit;

public class ToDoubleConversion extends Conversion {

	public static final String NAME = "to_double";
	
	@Override
	public Object apply(Object value, Limit limit) {
		return toDouble(value);
	}
	
}
