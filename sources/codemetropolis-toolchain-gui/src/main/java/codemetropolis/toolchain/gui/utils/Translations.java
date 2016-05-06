package codemetropolis.toolchain.gui.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Translation service utility for the GUI translations. We use this instead of the
 * {@link codemetropolis.toolchain.commons.util.Resources} class so we didn't need to modify the
 * {@code resources.properties}, and could use a separate file for the GUI related labels.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class Translations {

  private static ResourceBundle translations = ResourceBundle.getBundle("translations");

  private Translations() { }

  /**
   * Attempts to find the translation for the given {@code key}.
   *
   * @param key The translation key.
   * @return The translation, if found, otherwise the {@code key} itself.
   */
  public static String t(String key) {
    try {
      return translations.getString(key);
    } catch (MissingResourceException e) {
      return key;
    }
  }

}
