package codemetropolis.toolchain.placing.exceptions;

public class NonExistentLayoutException extends PlacingException {

	private static final long serialVersionUID = -1943976722667754745L;

	public NonExistentLayoutException() {
		super();
	}

	public NonExistentLayoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NonExistentLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonExistentLayoutException(String message) {
		super(message);
	}

	public NonExistentLayoutException(Throwable cause) {
		super(cause);
	}

}
