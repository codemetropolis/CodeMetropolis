package codemetropolis.toolchain.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main {

  private static final String SOURCE_SANS = "fonts/SourceSansPro";
  private static final int TTF = Font.TRUETYPE_FONT;

  public static void main(String[] args) {
    // Try to apply system look and feel
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      // Failed to set system look and feel. Continue regardless.
    }

    // Load source sans fonts
    try {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-Black.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-BlackItalic.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-Bold.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-BoldItalic.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-ExtraLight.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-ExtraLightItalic.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-Italic.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-Light.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-LightItalic.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-Regular.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-Semibold.ttf")));
      ge.registerFont(Font.createFont(TTF, ClassLoader.getSystemResourceAsStream(SOURCE_SANS + "-SemiboldItalic.ttf")));
    } catch (FontFormatException | IOException e) {
    	e.printStackTrace();
      // Failed to load font files. Using defaults instead.
    }

    // Instantiate GUI
    GUIController controller = new GUIController();
    CodeMetropolisGUI gui = new CodeMetropolisGUI(controller);
    gui.setVisible(true);
  }   

}
