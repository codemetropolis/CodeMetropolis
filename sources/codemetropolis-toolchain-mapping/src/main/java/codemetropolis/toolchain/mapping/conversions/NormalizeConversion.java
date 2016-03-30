package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Limit;

public class NormalizeConversion extends Conversion {

	public static final String NAME = "normalize";
	
	@Override
	public Object apply(Object value, Limit limit) {
		double dValue = toDouble(value);
		double distance = limit.getMax() - limit.getMin();
		dValue = distance == 0 ? 1 : (dValue - limit.getMin()) / distance;
		return dValue;
	}
	
}
