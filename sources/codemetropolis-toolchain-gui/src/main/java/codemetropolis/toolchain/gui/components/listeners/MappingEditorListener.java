package codemetropolis.toolchain.gui.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import codemetropolis.toolchain.gui.CodeMetropolisGUI;
import codemetropolis.toolchain.gui.MappingFileEditorDialog;

//This class is not used (temporary or forever is not decided yet).
public class MappingEditorListener implements ActionListener {
	String cdfXmlFilePath;
	CodeMetropolisGUI cmGui;
	
	public MappingEditorListener(String filePath, CodeMetropolisGUI cmGui) {
		this.cdfXmlFilePath = filePath;
		this.cmGui = cmGui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		File cdfXmlFile = new File(cdfXmlFilePath);
		if(!cdfXmlFile.exists()) {
			JOptionPane.showMessageDialog(
					cmGui,
					"There is no file existing on the path specified!",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else {
			MappingFileEditorDialog dialog = new MappingFileEditorDialog(cdfXmlFilePath, cmGui);
			dialog.setVisible(true);
		}
	}

}
