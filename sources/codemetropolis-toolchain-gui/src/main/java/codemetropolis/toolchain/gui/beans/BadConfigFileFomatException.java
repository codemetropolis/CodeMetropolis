package codemetropolis.toolchain.gui.beans;

public class BadConfigFileFomatException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BadConfigFileFomatException() {
		
	}
	
	public BadConfigFileFomatException(String message) {
		super(message);
	}
	
	public BadConfigFileFomatException(Throwable cause) {
	    super(cause);
	}
	
	public BadConfigFileFomatException(String message, Throwable cause) {
	    super(message, cause);
	}

}