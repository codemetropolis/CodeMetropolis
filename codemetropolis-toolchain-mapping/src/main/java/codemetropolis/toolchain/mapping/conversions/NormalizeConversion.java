package codemetropolis.toolchain.mapping.conversions;

import codemetropolis.toolchain.mapping.model.Limit;

public class NormalizeConversion extends Conversion {

	@Override
	public Object apply(String value, Limit limit) {
		double valueDouble = (double)new ToDoubleConversion().apply(value, null);
		double distance = limit.getMax() - limit.getMin();
		valueDouble = distance == 0 ? 1 : (valueDouble - limit.getMin()) / distance;
		return valueDouble;
	}	
	
}
