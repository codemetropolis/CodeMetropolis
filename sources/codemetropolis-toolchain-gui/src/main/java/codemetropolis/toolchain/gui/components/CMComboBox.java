package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JComboBox;

/**
 * Custom combobox class for setting custom defaults for the JComboBoxes we use.
 *
 * @param <T> The type parameter for the {@link JComboBox}.
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMComboBox<T> extends JComboBox<T> {

  private static final long serialVersionUID = 1L;

  private static final Font COMBO_BOX_FONT = new Font("Source Sans Pro", Font.PLAIN, 16);

  /**
   * Constructs a {@link CMComboBox} instance.
   *
   * @param items The desired contents of this combobox.
   */
  public CMComboBox(T[] items) {
    super(items);

    setFont(COMBO_BOX_FONT);
  }

  /**
   * Constructs a {@link CMComboBox} instance, and sets its position and size.
   *
   * @param items The desired contents of this combobox.
   * @param x The x position on the ui.
   * @param y The y position on the ui.
   * @param width The width of the element.
   * @param height The height of the element.
   */
  public CMComboBox(T[] items, int x, int y, int width, int height) {
    this(items);

    setBounds(x, y, width, height);
  }

}
