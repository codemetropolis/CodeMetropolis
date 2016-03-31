package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Limit;

public class ToIntConversion extends Conversion {

	public static final String NAME = "to_int";
	
	@Override
	public Object apply(Object value, Limit limit) {
		return toInt(value);
	}
	
}
