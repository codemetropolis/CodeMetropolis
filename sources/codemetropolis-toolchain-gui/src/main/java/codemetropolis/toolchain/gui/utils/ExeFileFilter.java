package codemetropolis.toolchain.gui.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * {@link FileFilter} implementation for only showing directories and executables.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class ExeFileFilter extends FileFilter {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(File file) {
    return file.isDirectory() || file.getName().endsWith(".exe");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return Translations.t("gui_filter_exe");
  }

}
