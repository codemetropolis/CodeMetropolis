package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JLabel;

public class CMLabel extends JLabel {

  private static final long serialVersionUID = 1L;

  private static final Font LABEL_FONT = new Font("Source Sans Pro", Font.PLAIN, 16);

  public CMLabel(String label) {
    super(label);

    setFont(LABEL_FONT);
  }

}
