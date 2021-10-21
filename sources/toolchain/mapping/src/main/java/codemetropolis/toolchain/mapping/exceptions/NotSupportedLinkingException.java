package codemetropolis.toolchain.mapping.exceptions;

public class NotSupportedLinkingException extends MappingException {

	private static final long serialVersionUID = 6947796707002682357L;

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
