package codemetropolis.toolchain.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.conversions.QuantizationConversion;
import codemetropolis.toolchain.gui.utils.BuildableSettings;
import codemetropolis.toolchain.gui.utils.Translations;

/**
 * Class for setting the levels and their corresponding values of a quantization conversion.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 * @author Tamas Keri {@literal <KETWAAT.SZE>}
 */
public class QuantizationDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel;
	private CMTextField addLevelField;
	private CMButton addLevelButton;
	private CMButton clearLevelButton;
	private CMButton okButton;
	private JTable levelsTable;
	private DefaultTableModel levelsTableModel;
	
	private static String[] header = new String[] {
			"Level", "Value"
	};
	
	/**
	 * Its value can be set as "int(0 to 5)" or "string". In the first case numbers between 1-5 will be accepted. In the other case the possible values
	 * of the "character" and "external_character" buildable attributes (stone, wood, ...).
	 */
	private String levelType;
	
	/**
	 * Reference of the conversion whose levels will be set in this dialog.
	 */
	private QuantizationConversion conversionToSet;
	
	/**
	 * Instantiates the quantization setter dialog.
	 * @param conversionToSet The {@link QuantizationConversion} object, whose levels are going to be set.
	 * @param levelType The type of the accepted level values. It can be an integer with range 0-5 or a string which must be a valid "(external_)character" value.
	 */
	public QuantizationDialog(MappingFileEditorDialog parent, QuantizationConversion conversionToSet, String levelType) {
		this.conversionToSet = conversionToSet;		
		if(levelType.equals("int(0 to 5)") || levelType.equals("string")) {
			this.levelType = levelType;
		}
		else {
			this.levelType = "string";
		}
		
		this.setTitle(Translations.t("gui_set_quant_parameters_title"));
		
		setContentPanel();
		
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setModal(true);
		this.setVisible(true);
	}
	
	/**
	 * Sets the main (content) panel of the dialog.
	 */
	private void setContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		Dimension size = new Dimension(350, 250);
		contentPanel.setMinimumSize(size);
	    contentPanel.setPreferredSize(size);
	    contentPanel.setMaximumSize(size);
	    
		setTable();
		setTextField();
		setButtons();
		addActionListeners();
		
		this.add(contentPanel);
	}
	
	/**
	 * Sets the table of the dialog storing the level values.
	 */
	private void setTable() {
		levelsTable = new JTable();
		
		levelsTableModel = new DefaultTableModel();
		levelsTableModel.setColumnIdentifiers(header);
		
		levelsTable.setModel(levelsTableModel);
		
		levelsTable.setBounds(10, 50, 200, 150);
		
		JScrollPane scrollPane = new JScrollPane(levelsTable);
		scrollPane.setBounds(10, 50, 200, 150);
		
		contentPanel.add(scrollPane);
	}
	
	/**
	 * Sets the input text field of the dialog.
	 */
	private void setTextField() {
		addLevelField = new CMTextField(10, 10, 100, 30);
		contentPanel.add(addLevelField);
	}
	
	/**
	 * Sets the buttons of the dialog.
	 */
	private void setButtons() {
		addLevelButton = new CMButton(Translations.t("gui_b_add_level"), 120, 10, 100, 30);
		addLevelButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 13));
		clearLevelButton = new CMButton(Translations.t("gui_b_clear_levels"), 230, 10, 100, 30);
		clearLevelButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 13));
		okButton = new CMButton(Translations.t("gui_b_ok"), 10, 210, 100, 30);
		okButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 13));
		contentPanel.add(addLevelButton);
		contentPanel.add(clearLevelButton);
		contentPanel.add(okButton);
	}
	
	/**
	 * Adds action listeners to the dialog buttons.
	 */
	private void addActionListeners() {
		QuantizationDialog self = this;
		
		addLevelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String levelValue = addLevelField.getText();
				int levelIndex = levelsTable.getRowCount();
				if (validateLevelValue(levelValue)) {
					levelsTableModel.addRow(new Object[] { levelIndex, levelValue });
				}
				else {
					JOptionPane.showMessageDialog(
							null,
							Translations.t("gui_err_invalid_level_value"),
							Translations.t("gui_err_title"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		clearLevelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				levelsTableModel.setRowCount(0);
			}
			
		});
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int rowCount = levelsTable.getRowCount();
				if(rowCount == 0) {
					JOptionPane.showMessageDialog(
							null,
							Translations.t("gui_err_no_level_declared"),
							Translations.t("gui_err_title"),
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					String[] levelValues = new String[rowCount];
					for(int i = 0; i < rowCount; i++) {
						levelValues[i] = (String) levelsTable.getValueAt(i, 1);
					}
					List<String> quantizationLevelValues = Arrays.asList(levelValues);
					conversionToSet.setLevels(quantizationLevelValues);
					self.setVisible(false);
				}
			}
			
		});
	}
	
	/**
	 * Validates, if the entered value for the current level is valid or not.
	 * @param levelValue The entered value for the current level.
	 * @return The entered value is valid or not.
	 */
	private boolean validateLevelValue(String levelValue) {
		boolean valueIsOK = false;
		if(levelType.equals("int(0 to 5)")) {
			if(levelValue.matches("[0-5]")) {
				valueIsOK = true;
			}
		}
		if(levelType.equals("string")) {
			if(BuildableSettings.VALID_CHARACTER_TYPES.contains(levelValue)) {
				valueIsOK = true;
			}
		}
		return valueIsOK;
	}	
}