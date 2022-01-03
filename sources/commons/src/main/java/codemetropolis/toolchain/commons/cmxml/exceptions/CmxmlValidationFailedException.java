package codemetropolis.toolchain.commons.cmxml.exceptions;

public class CmxmlValidationFailedException extends Exception {
	
	private static final long serialVersionUID = -5620457353841489798L;

	public CmxmlValidationFailedException() {
		super();
	}

	public CmxmlValidationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CmxmlValidationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CmxmlValidationFailedException(String message) {
		super(message);
	}

	public CmxmlValidationFailedException(Throwable cause) {
		super(cause);
	}

}
