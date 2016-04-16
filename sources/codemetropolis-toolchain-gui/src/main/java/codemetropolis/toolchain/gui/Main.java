package codemetropolis.toolchain.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main class for the CodeMetropolis' GUI module. Initializes some globals, like fonts and look-and-feel, and
 * instaniates the main gui window.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class Main {

  private static final int TTF = Font.TRUETYPE_FONT;
  private static final String SOURCE_SANS = "fonts/SourceSansPro-";
  private static final String[] FONT_VARIATIONS = { "Black", "BlackItalic", "Bold", "BoldItalic", "ExtraLight",
    "ExtraLightItalic", "Italic", "Light", "LightItalic", "Regular", "Semibold", "SemiboldItalic" };

  /**
   * The CodeMetropolis GUI application entry point.
   * 
   * @param args The command line arguments. Since this a GUI application, it is currently unused.
   */
  public static void main(String[] args) {
    setSystemLookAndFeel();
    loadSourceSansProFonts();

    // Instantiate the GUI
    GUIController controller = new GUIController();
    CodeMetropolisGUI gui = new CodeMetropolisGUI(controller);
    gui.setVisible(true);
  }

  /**
   * Attempts to set the lookAndFeel used by the Swing components to be the system's lookAndFeel.
   */
  private static final void setSystemLookAndFeel() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      // Failed to set system look and feel. Continue regardless.
    }
  }

  /**
   * Attempts to load the SourceSans ttf font files from the classpath.
   */
  private static final void loadSourceSansProFonts() {
    try {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      for (String variation : FONT_VARIATIONS) {
        ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + variation + ".ttf")));
      }
    } catch (FontFormatException | IOException e) {
      // Failed to load font files. Using defaults instead.
    }
  }

}
