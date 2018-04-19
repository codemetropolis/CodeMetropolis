package codemetropolis.toolchain.gui;

import javax.swing.JDialog;

import codemetropolis.toolchain.gui.beans.QuantizationInformation;
import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMComboBox;
import codemetropolis.toolchain.gui.components.CMTextField;

public class QuantizationSetterDialog extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	MappingFileEditorDialog parent;

	CMTextField gardenTexField;
	CMTextField cellarTexField;
	CMTextField floorTexField;

	CMButton gardenButton;
	CMButton cellarButton;
	CMButton floorButton;

	CMComboBox<QuantizationInformation> gardenComboBox;
	CMComboBox<QuantizationInformation> cellarComboBox;
	CMComboBox<QuantizationInformation> floorComboBox;

	public QuantizationSetterDialog(MappingFileEditorDialog parent) {
		this.parent = parent;
	}
}
