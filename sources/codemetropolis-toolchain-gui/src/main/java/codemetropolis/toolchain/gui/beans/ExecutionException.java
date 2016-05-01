package codemetropolis.toolchain.gui.beans;

/**
 * Exception class for handling exceptions that occur during an execution.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class ExecutionException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * @see Exception#Exception()
   */
  public ExecutionException() {

  }

  /**
   * @see Exception#Exception(String)
   */
  public ExecutionException(String message) {
    super(message);
  }

  /**
   * @see Exception#Exception(Throwable)
   */
  public ExecutionException(Throwable cause) {
    super(cause);
  }

  /**
   * @see Exception#Exception(String, Throwable)
   */
  public ExecutionException(String message, Throwable cause) {
    super(message, cause);
  }

}
