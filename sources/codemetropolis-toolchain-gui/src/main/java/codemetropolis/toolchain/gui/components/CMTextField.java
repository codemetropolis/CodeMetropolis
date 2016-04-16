package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JTextField;

/**
 * Custom textField class for setting custom defaults for the JTextFields we use.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMTextField extends JTextField {

  private static final long serialVersionUID = 1L;

  private static final Font TEXT_FIELD_FONT = new Font("Source Sans Pro", Font.PLAIN, 14);

  /**
   * Constructs a {@link CMTextField} instance.
   */
  public CMTextField() {
    setFont(TEXT_FIELD_FONT);
  }

  /**
   * Constructs a {@link CMTextField} instance.
   *
   * @param x The x position on the ui.
   * @param y The y position on the ui.
   * @param width The width of the element.
   * @param height The height of the element.
   */
  public CMTextField(int x, int y, int width, int height) {
    this();

    setBounds(x, y, width, height);
  }

}
