package codemetropolis.toolchain.gui.components;

import java.awt.Font;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import codemetropolis.toolchain.mapping.MappingExecutor;

/**
 * Custom numeric input class for setting custom defaults for the JSpinners we use.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CMSpinner extends JSpinner {

  private static final long serialVersionUID = 1L;

  private static final Font SPINNER_FONT = new Font("Source Sans Pro", Font.PLAIN, 14);

  /**
   * Constructs a {@link CMSpinner} instance.
   */
  public CMSpinner() {
    super();

    setFont(SPINNER_FONT);
    setModel(new SpinnerNumberModel(1.0, MappingExecutor.MIN_SCALE, MappingExecutor.MAX_SCALE, 0.01));
  }

  /**
   * Constructs a {@link CMSpinner} instance, and sets its position and size.
   *
   * @param x The x position on the ui.
   * @param y The y position on the ui.
   * @param width The width of the element.
   * @param height The height of the element.
   */
  public CMSpinner(int x, int y, int width, int height) {
    this();

    setBounds(x, y, width, height);
  }

}
