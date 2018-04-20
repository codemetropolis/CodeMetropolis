package codemetropolis.toolchain.gui;

import java.awt.Dimension;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JPanel;

import codemetropolis.toolchain.gui.beans.QuantizationInformation;
import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMComboBox;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.utils.BuildableSettings;
import codemetropolis.toolchain.gui.utils.Translations;

/**
 * This class is define the view of
 * quantization dialog.
 *
 * @author Tamas Keri {@literal <KETWAAT.SZE>}
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class QuantizationSetterDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	MappingFileEditorDialog parent;

	CMTextField gardenTextField;
	CMTextField cellarTextField;
	CMTextField floorTextField;

	CMButton gardenButton;
	CMButton cellarButton;
	CMButton floorButton;

	CMComboBox<QuantizationInformation> gardenComboBox;
	CMComboBox<QuantizationInformation> cellarComboBox;
	CMComboBox<QuantizationInformation> floorComboBox;

	public QuantizationSetterDialog(MappingFileEditorDialog parent) {
		this.parent = parent;
		JPanel panel = createBasePanel();
		addCellarComponents(panel);
		addFloorComponents(panel);
		addGardenComponents(panel);
		addSaveComponents(panel);
		
		this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	    this.setContentPane(panel);
	    this.pack();
	    this.setLocationRelativeTo(parent);
	}
	
	private JPanel createBasePanel() {
		JPanel panel = new JPanel();
	    panel.setLayout(null);
	    panel.setBounds(0, 0, 300, 500);

	    Dimension size = new Dimension(300, 500);
	    panel.setMinimumSize(size);
	    panel.setPreferredSize(size);
	    panel.setMaximumSize(size);

	    return panel;
	}
	
	private void addCellarComponents(JPanel panel) {
		CMLabel forCellarLabel = new CMLabel(Translations.t("gui_buildable_cellar"), 0, 0, 120, 30);
		cellarComboBox = new CMComboBox<QuantizationInformation>(fillComboBox("CELLAR"), 0, 40, 250, 30);
		cellarTextField = new CMTextField(0, 80, 160, 30);
		cellarButton = new CMButton(Translations.t("gui_b_add_level_value"), 170, 120, 80, 30);
		panel.add(forCellarLabel);
		panel.add(cellarComboBox);
		panel.add(cellarTextField);
		panel.add(cellarButton);
	}
	
	private void addFloorComponents(JPanel panel) {
		CMLabel forFloorLabel = new CMLabel(Translations.t("gui_buildable_floor"), 0, 180, 120, 30);
		floorComboBox = new CMComboBox<QuantizationInformation>(fillComboBox("FLOOR"), 0, 220, 250, 30);
		floorTextField = new CMTextField(0, 260, 160, 30);
		floorButton = new CMButton(Translations.t("gui_b_add_level_value"), 170, 300, 80, 30);
		panel.add(forFloorLabel);
		panel.add(floorComboBox);
		panel.add(floorTextField);
		panel.add(floorButton);
	}
	
	private void addGardenComponents(JPanel panel) {
		CMLabel forGardenLabel = new CMLabel(Translations.t("gui_buildable_garden"), 0, 340, 120, 30);
		gardenComboBox = new CMComboBox<QuantizationInformation>(fillComboBox("GARDEN"), 0, 380, 250, 30);
		gardenTextField = new CMTextField(0, 80, 160, 30);
		gardenButton = new CMButton(Translations.t("gui_b_add_level_value"), 170, 420, 80, 30);
		panel.add(forGardenLabel);
		panel.add(gardenComboBox);
		panel.add(gardenTextField);
		panel.add(gardenButton);
	}
	
	private void addSaveComponents(JPanel panel) {
		CMButton saveButton = new CMButton(Translations.t("gui_b_save_settings"), 0, 460, 100, 30);
		panel.add(saveButton);
	}
	
	private QuantizationInformation[] fillComboBox(String buildableType) {		
		Set<String> acceptedTypes = BuildableSettings.DEFAULT_SETTINGS.keySet();
		String inputType = buildableType.toUpperCase();
		if (acceptedTypes.contains(inputType)) {
			Set<QuantizationInformation> set = null;
			if(inputType.equals("CELLAR")) {
				set = MappingFileEditorDialog.cellarQuant.keySet();
			}
			if(inputType.equals("FLOOR")) {
				set = MappingFileEditorDialog.floorQuant.keySet();
			}
			if(inputType.equals("GARDEN")) {
				set = MappingFileEditorDialog.gardenQuant.keySet();
			}			 
			QuantizationInformation[] boxItems = new QuantizationInformation[set.size()];
			boxItems = set.toArray(boxItems);
			return boxItems;
		}
		else return null;
	}
}
