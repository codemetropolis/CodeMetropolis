package codemetropolis.toolchain.commons.exceptions;

public class InvalidSchemeException extends Exception {

	private static final long serialVersionUID = -2900728001816624441L;

	public InvalidSchemeException() {
		super();
	}

	public InvalidSchemeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidSchemeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSchemeException(String message) {
		super(message);
	}

	public InvalidSchemeException(Throwable cause) {
		super(cause);
	}
	
}
