package codemetropolis.toolchain.gui.utils;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import codemetropolis.toolchain.gui.GUIController;

/**
 * {@link SwingWorker} sublclass for executing the CodeMetropolis toolchain.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class ExecutionWorker extends SwingWorker<Void, Integer> {

  private boolean successful = false;

  private JButton start;
  private GUIController controller;
  private PipedOutputStream out;

  /**
   * Instantiates the {@link ExecutionWorker}.
   *
   * @param start The button used to start the execution. This reference is required because at the start and at the end
   *   of the execution it will be disabled and re-enabled, respectively.
   * @param controller The controller instance that will do the actual execution.
   * @param out The {@link PipedOutputStream} that will be used by the executors.
   */
  public ExecutionWorker(JButton start, GUIController controller, PipedOutputStream out) {
    this.start = start;
    this.controller = controller;
    this.out = out;
  }

  @Override
  protected Void doInBackground() throws Exception {
    start.setEnabled(false);
    controller.execute(new PrintStream(out));
    successful = true;
    return null;
  }

  @Override
  protected void done() {
    try {
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    start.setEnabled(true);
    if (successful) {
      JOptionPane.showMessageDialog(null, Translations.t("gui_info_world_gen_successful"),
        Translations.t("gui_info_finished"), JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(null, Translations.t("gui_err_world_gen_failed"),
        Translations.t("gui_err_title"), JOptionPane.ERROR_MESSAGE);
    }
    super.done();
  }

}
