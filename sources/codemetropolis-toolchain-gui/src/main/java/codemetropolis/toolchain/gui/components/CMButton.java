package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JButton;

public class CMButton extends JButton {

  private static final long serialVersionUID = 1L;

  private static final Font BUTTON_FONT = new Font("Source Sans Pro", Font.PLAIN, 16);

  public CMButton(String label) {
    super(label);

    setFont(BUTTON_FONT);
  }

}
