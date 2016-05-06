package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JPasswordField;

/**
 * Custom passwordField class for setting custom defaults for the JPasswordFields we use.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMPasswordField extends JPasswordField {

  private static final long serialVersionUID = 1L;

  private static final Font PASSWORD_FIELD_FONT = new Font("Source Sans Pro", Font.PLAIN, 14);

  /**
   * Constructs a {@link CMPasswordField} instance.
   */
  public CMPasswordField() {
    setEchoChar('*');
    setFont(PASSWORD_FIELD_FONT);
  }

  /**
   * Constructs a {@link CMPasswordField} instance, and sets its position and size.
   *
   * @param x The x position on the ui.
   * @param y The y position on the ui.
   * @param width The width of the element.
   * @param height The height of the element.
   */
  public CMPasswordField(int x, int y, int width, int height) {
    this();

    setBounds(x, y, width, height);
  }

}
