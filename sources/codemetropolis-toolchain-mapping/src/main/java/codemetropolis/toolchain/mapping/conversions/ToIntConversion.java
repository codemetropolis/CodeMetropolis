package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Limit;

public class ToIntConversion extends Conversion {

	@Override
	public Object apply(String value, Limit limit) {
		return (int)Double.parseDouble(value);
	}
	
}
