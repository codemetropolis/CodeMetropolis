package codemetropolis.toolchain.mapping.exceptions;

public class MissingResourceException extends MappingException {

	private static final long serialVersionUID = 7706267293435539734L;

	public MissingResourceException() {
		super();
	}

	public MissingResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MissingResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingResourceException(String message) {
		super(message);
	}

	public MissingResourceException(Throwable cause) {
		super(cause);
	}

}
