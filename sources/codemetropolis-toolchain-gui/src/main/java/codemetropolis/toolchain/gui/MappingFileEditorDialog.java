package codemetropolis.toolchain.gui;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import codemetropolis.toolchain.gui.beans.BadConfigFileFomatException;
import codemetropolis.toolchain.gui.utils.BuildableSettings;
import codemetropolis.toolchain.gui.utils.Property;
import codemetropolis.toolchain.gui.utils.PropertyCollector;

public class MappingFileEditorDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String[]> displayedBuildableAttributes;
	private Map<String, List<Property>> sourceCodeElementProperties;
	
	public MappingFileEditorDialog(String cdfFilePath, CodeMetropolisGUI cmGui) {
		super(cmGui, true);
		loadDisplayedInfo(cdfFilePath);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.setSize(new Dimension(400, 100));
		this.setLocationRelativeTo(cmGui);
	}
	
	private void loadDisplayedInfo(String cdfFilePath) {
		try {
			displayedBuildableAttributes = BuildableSettings.readSettings();
			BuildableSettings.displaySettings();
			
			PropertyCollector pc = new PropertyCollector();
			sourceCodeElementProperties = pc.getFromCdf(cdfFilePath);
			pc.displayProperties();
		}
		catch(BadConfigFileFomatException e) {
			JOptionPane.showMessageDialog(
					null,
					"The format of the config file is invalid! All buildable attributes will be displayed.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			
			displayedBuildableAttributes = BuildableSettings.DEFAULT_SETTINGS;
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(
					null,
					"Config file not found! All buildable attributes will be displayed.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			
			displayedBuildableAttributes = BuildableSettings.DEFAULT_SETTINGS;
		}
	}
}
