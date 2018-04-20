package codemetropolis.toolchain.gui.beans;

/**
 * Contains the quantization information.
 *
 * @author Tamas Keri {@literal <KETWAAT.SZE>}
 */
public class QuantizationInformation {
	private int index;
	private String buildableAttribute;
	private String metric;

	public QuantizationInformation() {
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getBuildableAttribute() {
		return buildableAttribute;
	}

	public void setBuildableAttribute(String buildableAttribute) {
		this.buildableAttribute = buildableAttribute;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String toString() {
		return index + ";" + buildableAttribute + ";" + metric;
	}
}
