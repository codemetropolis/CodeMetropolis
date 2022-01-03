package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JTextArea;

/**
 * Custom textArea class for setting custom defaults for the JTextAreas we use.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMTextArea extends JTextArea {

  private static final long serialVersionUID = 1L;

  private static final Font TEXT_AREA_FONT = new Font("Source Sans Pro", Font.PLAIN, 14);

  /**
   * Constructs a {@link CMTextArea} instance.
   */
  public CMTextArea() {
    setFont(TEXT_AREA_FONT);
  }

  /**
   * Constructs a {@link CMTextArea} instance.
   *
   * @param x The x position on the ui.
   * @param y The y position on the ui.
   * @param width The width of the element.
   * @param height The height of the element.
   */
  public CMTextArea(int x, int y, int width, int height) {
    this();

    setBounds(x, y, width, height);
  }

}
