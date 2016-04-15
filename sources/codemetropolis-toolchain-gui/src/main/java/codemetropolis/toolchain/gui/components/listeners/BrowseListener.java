package codemetropolis.toolchain.gui.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import codemetropolis.toolchain.gui.CodeMetropolisGUI;
import codemetropolis.toolchain.gui.components.CMTextField;

/**
 * Listener class to handle browse callbacks for directories.
 * 
 * @author szkabel
 *
 */
public class BrowseListener implements ActionListener {
			
	private CodeMetropolisGUI mainWindow;
	private int fileMode;
	//A text field to where the path will be saved of the selected directory.
	private CMTextField textField;
	
	public BrowseListener(CodeMetropolisGUI mainWin, CMTextField tf, int fm) {		
		mainWindow = mainWin;
		textField = tf;
		fileMode = fm;
	}

	/**
	 * When a user press browse, a popup File chooser appears and the selected directory path or file
	 * is copied to the appropriate text field. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(fileMode);
		int returnVal = fc.showOpenDialog(mainWindow);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            textField.setText(file.getAbsolutePath());
        }		
	}

}
