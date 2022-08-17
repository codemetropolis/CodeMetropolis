package codemetropolis.toolchain.gui.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import codemetropolis.toolchain.gui.components.CMTextField;

/**
 * Listener class to handle file and directory browsing.
 *
 * @author Abel Szkalisity {@literal <SZAVAET.SZE>}
 */
public class BrowseListener implements ActionListener {

  private JFileChooser fileChooser;
  private CMTextField fileNameTextField;

  /**
   * Constructs a {@link BrowseListener} instance with the given parameters.
   *
   * @param fileNameTextField The {@link CMTextField} instance that will contain the path for the selected file.
   * @param fileSelectionMode The file selection mode for the {@link JFileChooser}. See
   *   {@link JFileChooser#setFileSelectionMode(int)} for details.
   * @param filter Optional. If provided, it will be used for the {@link JFileChooser} to filter the visible entities.
   */
  public BrowseListener(CMTextField fileNameTextField, int fileSelectionMode, FileFilter filter) {
    this.fileNameTextField = fileNameTextField;

    this.fileChooser = new JFileChooser();
    this.fileChooser.setFileSelectionMode(fileSelectionMode);
    if (filter != null) {
      this.fileChooser.setFileFilter(filter);
    }
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      fileNameTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
      fileNameTextField.requestFocus();
    }
  }

}
