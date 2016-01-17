package codemetropolis.toolchain.mapping.exceptions;

public class NotSupportedLinkingException extends Exception {

	private static final long serialVersionUID = -8880241135777702738L;

	public NotSupportedLinkingException() {
		super();
	}

	public NotSupportedLinkingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotSupportedLinkingException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSupportedLinkingException(String message) {
		super(message);
	}

	public NotSupportedLinkingException(Throwable cause) {
		super(cause);
	}

}
