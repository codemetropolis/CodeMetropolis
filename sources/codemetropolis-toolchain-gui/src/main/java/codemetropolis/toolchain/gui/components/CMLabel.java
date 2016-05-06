package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * Custom label class for setting custom defaults for the JLabels we use.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMLabel extends JLabel {

  private static final long serialVersionUID = 1L;

  private static final Font LABEL_FONT = new Font("Source Sans Pro", Font.PLAIN, 16);

  /**
   * Constructs a {@link CMLabel} instance.
   *
   * @param label The text for this label.
   */
  public CMLabel(String label) {
    super(label);

    setFont(LABEL_FONT);
  }

  /**
   * Constructs a {@link CMLabel} instance, and sets its position and size.
   *
   * @param label The text for this label.
   * @param x The x position on the ui.
   * @param y The y position on the ui.
   * @param width The width of the element.
   * @param height The height of the element.
   */
  public CMLabel(String label, int x, int y, int width, int height) {
    this(label);

    setBounds(x, y, width, height);
  }

}
