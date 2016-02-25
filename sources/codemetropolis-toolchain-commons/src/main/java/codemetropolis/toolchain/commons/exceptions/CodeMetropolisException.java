package codemetropolis.toolchain.commons.exceptions;

public class CodeMetropolisException extends Exception {

	private static final long serialVersionUID = -7985709871539500625L;

	public CodeMetropolisException() {
		super();
	}

	public CodeMetropolisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CodeMetropolisException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeMetropolisException(String message) {
		super(message);
	}

	public CodeMetropolisException(Throwable cause) {
		super(cause);
	}
	
}
