package codemetropolis.toolchain.gui.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * {@link SwingWorker} subclass that reads from a specified {@link PipedOutputStream} and feeds it into a
 * {@link JTextArea}.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class StreamReaderWorker extends SwingWorker<Void, Integer> {

  private JButton close;
  private JTextArea textArea;
  private PipedInputStream in;

  /**
   * Instantiates the worker.
   *
   * @param close A {@link JButton} that will be enabled when the stream closes.
   * @param textArea The {@link JTextArea} the stream will be fed into.
   * @param out The {@link PipedOutputStream} the data will be read from by piping it into a {@link PipedInputStream}.
   * @throws IOException if any I/O error occurs.
   */
  public StreamReaderWorker(JButton close, JTextArea textArea, PipedOutputStream out) throws IOException {
    this.close = close;
    this.textArea = textArea;
    this.in = new PipedInputStream(out);
  }

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

}
