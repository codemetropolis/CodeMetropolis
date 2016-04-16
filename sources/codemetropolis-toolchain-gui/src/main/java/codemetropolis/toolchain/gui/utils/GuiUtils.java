package codemetropolis.toolchain.gui.utils;

import java.io.File;

import javax.swing.JOptionPane;

import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * Utility class for globally used functions.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class GuiUtils {

  public static String findMinecraftRoot() {
    String os = System.getProperty("os.name").toLowerCase();

    String location = "";
    if (os.indexOf("win") > -1) {
      location = System.getenv("appdata") + File.separator + ".minecraft";
    } else if (os.indexOf("nix") > -1 || os.indexOf("nux") > -1 || os.indexOf("aix") > -1) {
      location = '~' + File.separator + ".minecraft";
    } else if (os.indexOf("mac") > -1) {
      location = '~' + File.separator + "Library" + File.separator + "Application Support" + File.separator
          + "minecraft";
    } else {
      return null;
    }

    File minecraftRoot = new File(location);
    if (minecraftRoot.exists() && minecraftRoot.isDirectory()) {
      return minecraftRoot.getAbsolutePath();
    } else {
      return null;
    }
  }

  /**
   * Validates the options set in the {@code executionOptions}. Most importantly checks if the files and directories
   * selected are readable/writable.
   *
   * @param executionOptions The {@link ExecutionOptions} instance to validate.
   */
  public static boolean validateOptions(ExecutionOptions executionOptions) {
    File mappingXml = executionOptions.getMappingXml();
    File minecraftRoot = executionOptions.getMinecraftRoot();

    if (executionOptions.getProjectName() == null || executionOptions.getProjectName().isEmpty()) {
      showError("Invalid project name!");
    } else if (mappingXml == null || !mappingXml.exists() || !mappingXml.isFile() || !mappingXml.canRead()) {
      showError("Invalid mapping xml file!");
    } else if (minecraftRoot == null || !minecraftRoot.exists() || !minecraftRoot.isDirectory()
        || !minecraftRoot.canRead() || !minecraftRoot.canWrite()) {
      showError("Invalid minecraft root!");
    } else {
      return true;
    }

    return false;
  }

  /**
   * Shows an error dialog with the specified {@code message}.
   *
   * @param message The error message to show.
   */
  public static void showError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

}
