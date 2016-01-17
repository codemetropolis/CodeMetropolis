package codemetropolis.toolchain.placing.exceptions;

public class NonExistentLayoutException extends Exception {

	private static final long serialVersionUID = -8880241135777702738L;

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
