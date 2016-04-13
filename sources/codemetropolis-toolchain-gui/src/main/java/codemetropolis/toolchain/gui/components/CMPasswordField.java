package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JPasswordField;

public class CMPasswordField extends JPasswordField {

  private static final long serialVersionUID = 1L;

  private static final Font PASSWORD_FIELD_FONT = new Font("Source Sans Pro", Font.PLAIN, 14);

  public CMPasswordField() {
    setFont(PASSWORD_FIELD_FONT);
  }

}
