package codemetropolis.toolchain.mapping.conversions;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.mapping.model.Limit;
import codemetropolis.toolchain.mapping.model.Parameter;

public abstract class Conversion {
	
	protected List<Parameter> parameters;
	
	public Conversion() {
		parameters = new ArrayList<Parameter>();
	}
	
	public abstract Object apply(String value, Limit limit);
	
	public void clearParameters() {
		parameters.clear();
	}
	
	public void addParameter(Parameter p) {
		parameters.add(p);
	}
	
	public void addParameters(Parameter... parameters) {
		for(Parameter p : parameters) {
			this.parameters.add(p);
		}
	}
	
	public Parameter[] getParameters() {
		return parameters.toArray(new Parameter[parameters.size()]);
	}
	
	public static Conversion createFromName(String name) {	
		switch(name.toLowerCase()) {
			case "to int":
			case "to_int":
				return new ToIntConversion();
			case "to double":
			case "to_double":
				return new ToDoubleConversion();
			case "multiply":
				return new MultiplyConversion();
			case "quantization":
				return new QuantizationConversion();
			case "normalize":
				return new NormalizeConversion();
			case "switch":
				return new SwitchConversion();
		}
		return null;
	}
		
}
