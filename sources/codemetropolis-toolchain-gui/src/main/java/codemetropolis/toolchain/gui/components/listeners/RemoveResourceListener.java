package codemetropolis.toolchain.gui.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import codemetropolis.toolchain.gui.utils.Translations;

/**
 * Listener class for handling removing resources.
 * @author Viktor Meszaros {@link <MEVXAAT.SZE>}
 */
public class RemoveResourceListener implements ActionListener {

	private JList<String> resourcesList;
	private List<JList<String>> lists;
	private List<JTable> tables;
	
	/**
	 * Creates a @link {@link RemoveResourceListener} instance
	 */
	public RemoveResourceListener() {}
	
	/**
	 * Creates a @link {@link RemoveResourceListener} instance with the given parameters.
	 * @param resourcesList The {@link JList} containing the list of resources.
	 * @param lists The list of lists from which we want to delete the resource.
	 * @param tables The list of tables from which we want to delete the resource if it's already assigned to something.
	 */
	public RemoveResourceListener(JList<String> resourcesList, List<JList<String>> lists, List<JTable> tables) {
		this.resourcesList = resourcesList;
		this.lists = lists;
		this.tables = tables;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int indexToRemove = resourcesList.getSelectedIndex();
		if(indexToRemove == -1) {
			JOptionPane.showMessageDialog(
					null,
					Translations.t("gui_err_resources_empty_no_selected"),
					Translations.t("gui_err_title"),
					JOptionPane.ERROR_MESSAGE);
		}
		else {
			String resourceToRemove = resourcesList.getModel().getElementAt(indexToRemove);
			for(JList<String> list : lists) {
				DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();
				listModel.removeElement(resourceToRemove);
			}
			for(JTable table : tables) {
				int rows = table.getRowCount();
				int columns = table.getColumnCount();
				for(int i = 0; i < rows; i++) {
					for(int j = 0; j < columns; j++) {
						String cellValue = (String) table.getValueAt(i, j);
						if(resourceToRemove.equals(cellValue)) {
							table.setValueAt(null, i, j);
						}
					}
				}
			}
		}
	}
	
}
