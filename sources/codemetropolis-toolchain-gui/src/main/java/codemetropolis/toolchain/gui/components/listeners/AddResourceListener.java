package codemetropolis.toolchain.gui.components.listeners;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import codemetropolis.toolchain.gui.utils.BuildableSettings;
import codemetropolis.toolchain.gui.utils.Translations;

/**
 * Listener class for handling adding resources.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class AddResourceListener implements ActionListener {
	
	private List<JList<String>> lists;
	
	/**
	 *  Creates a new instance of {@link AddResourceListener} class.
	 */
	public AddResourceListener() {}
	
	/**
	 * Creates a new instance of {@link AddResourceListener} class with the given parameters.
	 * @param lists The lists to which we want to add the new resource as an element.
	 */
	public AddResourceListener(List<JList<String>> lists) {
		this.lists = lists;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField nameField = new JTextField();
		JTextField valueField = new JTextField();
		
		JPanel addResourcePanel = new JPanel();
		addResourcePanel.setLayout(new GridLayout(4, 2));
		addResourcePanel.add(new JLabel("Resource name:"));
		addResourcePanel.add(nameField);
		addResourcePanel.add(new JLabel("Resource value:"));
		addResourcePanel.add(valueField);
		
		int result = JOptionPane.showConfirmDialog(
				null,
				addResourcePanel,
				Translations.t("gui_add_resource_title"),
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if(result == JOptionPane.OK_OPTION) {
			if (nameField.getText().matches("[a-zA-Z0-9_]+") &&
				(valueField.getText().matches("[0-9]+(.[0-9]+)?")) ||  BuildableSettings.VALID_CHARACTER_TYPES.contains(valueField.getText())) {
				//Produce the resource string from the text fields...
				String resourceToAdd = nameField.getText() + ": " + valueField.getText();
				//Add the newly defined resource to the property lists of the buildables and to the resource list (on top left of the window).
				for (JList<String> list : lists) {
					DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();
					listModel.addElement(resourceToAdd);
				} 
			}
			else {
				JOptionPane.showMessageDialog(
						null,
						Translations.t("gui_err_name_value_not_valid"),
						Translations.t("gui_err_title"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
