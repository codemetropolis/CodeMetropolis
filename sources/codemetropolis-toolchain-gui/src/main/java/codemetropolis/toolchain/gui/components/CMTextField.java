package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JTextField;

public class CMTextField extends JTextField {

  private static final long serialVersionUID = 1L;

  private static final Font TEXT_FIELD_FONT = new Font("Source Sans Pro", Font.PLAIN, 14);

  public CMTextField() {
    setFont(TEXT_FIELD_FONT);
  }

}
