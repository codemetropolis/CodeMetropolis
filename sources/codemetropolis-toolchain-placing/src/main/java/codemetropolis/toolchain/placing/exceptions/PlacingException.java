package codemetropolis.toolchain.placing.exceptions;

import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class PlacingException extends CodeMetropolisException {

	private static final long serialVersionUID = -4821414301180897025L;

	public PlacingException() {
		super();
	}

	public PlacingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PlacingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlacingException(String message) {
		super(message);
	}

	public PlacingException(Throwable cause) {
		super(cause);
	}

}
