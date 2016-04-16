package codemetropolis.toolhchain.gui.beans;

/**
 * Exception class for handling exceptions that occur during an execution.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class ExecutionException extends Exception {

  private static final long serialVersionUID = 1L;

  public ExecutionException() {

  }

  public ExecutionException(String message) {
    super(message);
  }

  public ExecutionException(Throwable cause) {
    super(cause);
  }

  public ExecutionException(String message, Throwable cause) {
    super(message, cause);
  }

}
