package codemetropolis.toolchain.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultCaret;

import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMTextArea;
import codemetropolis.toolchain.gui.utils.Translations;

/**
 * A dialog window for showing displaying the standard output of the executors.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class ExecutionDialog extends JDialog {

  private static final long serialVersionUID = 1L;

  private CMTextArea textArea;
  private CMButton close;

  /**
   * Creates the dialog window and starts the reader process.
   *
   * @param parent The parent frame. Useful in case we might want to turn this into a modal window.
   * @param out The {@link PipedOutputStream} used for the executors that we need to read.
   */
  public ExecutionDialog(JFrame parent, PipedOutputStream out) {
    super(parent, Translations.t("gui_exec_title"));

    JPanel panel = createDialogPanel();
    addTextArea(panel);
    addCloseButton(panel);

    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    this.setContentPane(panel);
    this.pack();
    this.setLocationRelativeTo(null);

    startReaderThread(out);
  }

  /**
   * Creates the base panel for this dialog.
   *
   * @return The assembled panel.
   */
  private JPanel createDialogPanel() {
    JPanel panel = new JPanel(null);
    panel.setLayout(null);
    panel.setBounds(0, 0, 400, 300);

    Dimension size = new Dimension(400, 300);
    panel.setMinimumSize(size);
    panel.setPreferredSize(size);
    panel.setMaximumSize(size);

    return panel;
  }

  /**
   * Creates and adds an uneditable {@link CMTextArea} into which the executors' outputs will be fed.
   *
   * @param panel The dialog panel.
   */
  private void addTextArea(JPanel panel) {
    textArea = new CMTextArea();
    textArea.setEditable(false);

    // Automatically scroll to the bottom
    DefaultCaret caret = (DefaultCaret) textArea.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    JScrollPane pane = new JScrollPane(textArea);
    pane.setBounds(10, 10, 380, 240);

    panel.add(pane);
  }

  /**
   * Creates and adds a close button for this dialog that is disabled by default. It will be enabled when the world
   * generation is finished.
   *
   * @param panel The dialog panel.
   */
  private void addCloseButton(JPanel panel) {
    close = new CMButton(Translations.t("gui_b_close"), 140, 260, 120, 30);
    close.setEnabled(false);
    close.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });

    panel.add(close);
  }

  /**
   * Starts a {@link SwingWorker} that will read from the specified {@link PipedOutputStream} by feeding it into a
   * {@link PipedInputStream}. It appends the lines it reads to the textArea on the dialog frame.
   *
   * @param out The {@link PipedOutputStream} used by the executors.
   */
  private void startReaderThread(PipedOutputStream out) {
    try {
      PipedInputStream in = new PipedInputStream(out);
      new SwingWorker<Void, Integer>() {

        @Override
        protected Void doInBackground() throws Exception {
          String line;
          BufferedReader reader = new BufferedReader(new InputStreamReader(in));
          while ((line = reader.readLine()) != null) {
            textArea.append(line);
            textArea.append("\n");
          }

          reader.close();
          in.close();
          return null;
        }

        @Override
        protected void done() {
          close.setEnabled(true);
        };

      }.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
