package codemetropolis.toolchain.mapping.model;

public class Limit {
	
	private double min;
	private double max;
	private int valueSetSize;
	
	public Limit() {
		this.min = Double.POSITIVE_INFINITY;
		this.max = Double.NEGATIVE_INFINITY;
		this.valueSetSize = 0;
	}

	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}
	
	public int getValueSetSize() {
		return valueSetSize;
	}
	
	public void add(double value) {
		if(value < min) min = value;
		if(value > max) max = value;
		++valueSetSize;
	}
	
}
