package codemetropolis.toolchain.gui.components;
import javax.swing.JPanel;

import codemetropolis.toolchain.gui.CodeMetropolisGUI;
import codemetropolis.toolchain.gui.metricgenerators.MetricGenerator;

/**
 * Extension to JPanel, as we need to store to which metric generator (SourceMeter, SonarQube) this panel
 * provides information.
 * 
 * @author szkabel
 *
 */
public class CMMetricPanel extends JPanel {				
	
	private static final long serialVersionUID = 1L;
		
	//Parent GUI to this object	 
	private CodeMetropolisGUI mainWindow;
	private MetricGenerator metricGenerator;

	public CodeMetropolisGUI getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(CodeMetropolisGUI parent) {
		this.mainWindow = parent;
	}	
	
	public MetricGenerator getMetricGenerator() {
		return metricGenerator;
	}

	public void setMetricGenerator(MetricGenerator metricGenerator) {
		this.metricGenerator = metricGenerator;
	}

	public CMMetricPanel(MetricGenerator mc, CodeMetropolisGUI p) {
		super();
		mainWindow = p;
		metricGenerator = mc;
	}
	

}
