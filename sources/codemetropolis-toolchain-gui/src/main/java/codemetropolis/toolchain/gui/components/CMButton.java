package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JButton;

/**
 * Custom button class for setting custom defaults for the JButtons we use.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMButton extends JButton {

  private static final long serialVersionUID = 1L;

  private static final Font BUTTON_FONT = new Font("Source Sans Pro", Font.PLAIN, 16);

  /**
   * Constructs a {@link CMButton} instance.
   *
   * @param label The label for this button.
   */
  public CMButton(String label) {
    super(label);

    setFont(BUTTON_FONT);
  }

  /**
   * Constructs a {@link CMButton} instance, and sets its position and size.
   *
   * @param label The label for this button.
   * @param x The x position on the ui.
   * @param y The y position on the ui.
   * @param width The width of the element.
   * @param height The height of the element.
   */
  public CMButton(String label, int x, int y, int width, int height) {
    this(label);

    setBounds(x, y, width, height);
  }

}
