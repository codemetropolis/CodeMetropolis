package codemetropolis.toolchain.gui.utils;

import java.io.File;

import javax.swing.JOptionPane;

import codemetropolis.toolchain.gui.beans.ExecutionOptions;

/**
 * Utility class for globally used functions.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class GuiUtils {

  /**
   * Attempts to find the location for the root folder of Minecraft, if it is installed.
   *
   * @return The path for the root folder, if found.
   */
  public static String findMinecraftRoot() {
    String expectedLocation = getMinecraftExpectedLocation();

    File minecraftRoot = new File(expectedLocation);
    if (minecraftRoot.exists() && minecraftRoot.isDirectory()) {
      return minecraftRoot.getAbsolutePath();
    } else {
      return null;
    }
  }

  /**
   * Assembles a path based on the host operating system, which is expected to be the root folder for Minecraft if it is
   * installed.
   *
   * @return The path to the expected location.
   */
  private static String getMinecraftExpectedLocation() {
    String os = System.getProperty("os.name").toLowerCase();

    String location = "";
    if (os.indexOf("win") > -1) {
      location = System.getenv("appdata") + File.separator + ".minecraft";
    } else if (os.indexOf("nix") > -1 || os.indexOf("nux") > -1 || os.indexOf("aix") > -1) {
      location = '~' + File.separator + ".minecraft";
    } else if (os.indexOf("mac") > -1) {
      location = '~' + File.separator + "Library" + File.separator + "Application Support" + File.separator
        + "minecraft";
    }

    return location;
  }

  /**
   * Validates the options set in the {@code executionOptions}. Most importantly checks if the files and directories
   * selected are readable/writable.
   *
   * @param executionOptions The {@link ExecutionOptions} instance to validate.
   * @return True, if the options are valid, false otherwise.
   */
  public static boolean validateOptions(ExecutionOptions executionOptions) {
    File mappingXml = executionOptions.getMappingXml();
    File minecraftRoot = executionOptions.getMinecraftRoot();

    if (executionOptions.getProjectName() == null || executionOptions.getProjectName().isEmpty()) {
      showError(Translations.t("gui_err_invalid_project_name"));
    } else if (mappingXml == null || !mappingXml.exists() || !mappingXml.isFile() || !mappingXml.canRead()) {
      showError(Translations.t("gui_err_invalid_mapping_xml"));
    } else if (minecraftRoot == null || !minecraftRoot.exists() || !minecraftRoot.isDirectory()
        || !minecraftRoot.canRead() || !minecraftRoot.canWrite()) {
      showError(Translations.t("gui_err_invalid_mc_root"));
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
    JOptionPane.showMessageDialog(null, message, Translations.t("gui_err_title"), JOptionPane.ERROR_MESSAGE);
  }

}
