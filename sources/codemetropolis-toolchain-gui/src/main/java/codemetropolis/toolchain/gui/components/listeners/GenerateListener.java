package codemetropolis.toolchain.gui.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import codemetropolis.toolchain.gui.CodeMetropolisGUI;
import codemetropolis.toolchain.gui.GUIController;
import codemetropolis.toolchain.gui.components.CMMetricPanel;
/**
 * To handle callback for generate (minecraft world) button.
 * 
 * @author szkabel
 *
 */
public class GenerateListener implements ActionListener {
	
	private GUIController controller;
	private CodeMetropolisGUI gui;
	
	/**
	 * Constructor with parameters: controller and the main gui.
	 * @param con
	 * @param gui
	 */
	public GenerateListener(GUIController con, CodeMetropolisGUI gui) {
		controller = con;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		//call the option builder function of the controller
		controller.setOptions(gui.getProjectName().getText(), ((CMMetricPanel)gui.getMetricTabbedPane().getSelectedComponent()).getMetricGenerator(), gui.getMappingPath().getText(), gui.getMcRootPath().getText(), gui.getShowMap().isSelected());
		
		controller.checkOptions();
		
		controller.runWorldGenearation();
	}

}
