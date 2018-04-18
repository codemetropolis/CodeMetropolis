package codemetropolis.toolchain.gui.beans;

/**
 * Exception class for handling exceptions that occur because of
 * the bad format of the configuration file containing the buildable attributes desired to display.
 */
public class BadConfigFileFomatException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @see Exception#Exception()
	 */
	public BadConfigFileFomatException() {
		
	}
	
	/**
	 * @see Exception#Exception(String)
	 */
	public BadConfigFileFomatException(String message) {
		super(message);
	}
	
	/**
	 * @see Exception#Exception(Throwable)
	 */
	public BadConfigFileFomatException(Throwable cause) {
	    super(cause);
	}
	
	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public BadConfigFileFomatException(String message, Throwable cause) {
	    super(message, cause);
	}
	
}