package codemetropolis.toolchain.placing.exceptions;

public class LayoutException extends PlacingException {
	
	private static final long serialVersionUID = -1326865817509151611L;

	public LayoutException() {
		super();
	}

	public LayoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public LayoutException(String message) {
		super(message);
	}

	public LayoutException(Throwable cause) {
		super(cause);
	}

}
