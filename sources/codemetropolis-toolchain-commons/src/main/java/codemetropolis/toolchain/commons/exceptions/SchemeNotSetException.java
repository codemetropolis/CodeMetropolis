package codemetropolis.toolchain.commons.exceptions;

public class SchemeNotSetException extends Exception {

	private static final long serialVersionUID = 4423340810837013461L;

	public SchemeNotSetException() {
		super();
	}

	public SchemeNotSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SchemeNotSetException(String message, Throwable cause) {
		super(message, cause);
	}

	public SchemeNotSetException(String message) {
		super(message);
	}

	public SchemeNotSetException(Throwable cause) {
		super(cause);
	}
	
}
