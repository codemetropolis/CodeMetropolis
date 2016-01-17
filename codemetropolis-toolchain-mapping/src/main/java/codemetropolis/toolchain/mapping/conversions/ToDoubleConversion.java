package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Limit;

public class ToDoubleConversion extends Conversion {

	@Override
	public Object apply(String value, Limit limit) {
		return Double.parseDouble(value);
	}	
}
