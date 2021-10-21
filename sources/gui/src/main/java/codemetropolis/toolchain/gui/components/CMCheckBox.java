package codemetropolis.toolchain.gui.components;

import javax.swing.JCheckBox;

/**
 * Custom checkbox class for setting custom defaults for the JCheckBoxes we use.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMCheckBox extends JCheckBox {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a {@link CMButton} instance.
   */
  public CMCheckBox() {
    setOpaque(false);
  }

  /**
   * Constructs a {@link CMCheckBox} instance, and sets its position and size.
   *
   * @param x The x position on the ui.
   * @param y The y position on the ui.
   * @param width The width of the element.
   * @param height The height of the element.
   */
  public CMCheckBox(int x, int y, int width, int height) {
    this();

    setBounds(x, y, width, height);
  }

}
