package codemetropolis.toolchain.gui.conversions;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing a quantization conversion.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class QuantizationConversion extends Conversion {
	private List<String> levels;
	
	public QuantizationConversion() {
		levels = new ArrayList<String>();		
	}
	
	public List<String> getLevels(){
		return levels;
	}
}
