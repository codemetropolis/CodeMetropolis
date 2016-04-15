package codemetropolis.toolchain.gui.metricgenerators;

import javax.swing.JPanel;

import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolchain.gui.CodeMetropolisGUI;
import codemetropolis.toolchain.gui.components.CMMetricPanel;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * Abstract class for different metric generators such as SonarQube, or SourceMeter.
 * 
 * @author szkabel
 *
 */
public abstract class MetricGenerator {
	private ConverterType type;
	private String name;
	
	/**
	 * Empty consturctor for array use.
	 */
	public MetricGenerator() {	
	}	
	/**
	 * Constructor with the type of the metricGenerator 
	 * @param type
	 */	
	public MetricGenerator(ConverterType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	
	/**
	 * get a panel on which the necessary parameters can be set
	 * @param gui
	 * @return CMMetricPanel
	 */
	public abstract CMMetricPanel getGUIpanel(CodeMetropolisGUI gui);
	/**
	 * Executes the metric generator from the options set on the guiPanel. The result file (e.g. graph in SourceMeter)
	 * will be saved to dst path.
	 * @param dst
	 * @param execOpt Other execution options if needed.
	 * @return String The filepath where the input of the converter can be found.
	 */
	public abstract String execute(String dst, ExecutionOptions execOpt);
	
	//getters and setters
	public ConverterType getType() {
		return type;
	}
	public void setType(ConverterType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	
}
