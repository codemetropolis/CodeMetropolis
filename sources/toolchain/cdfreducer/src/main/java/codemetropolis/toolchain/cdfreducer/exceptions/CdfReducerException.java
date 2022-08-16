package codemetropolis.toolchain.cdfreducer.exceptions;

import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class CdfReducerException extends CodeMetropolisException {

	private static final long serialVersionUID = -266528333447472817L;

	public CdfReducerException() {
		super();
	}

	public CdfReducerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CdfReducerException(String message, Throwable cause) {
		super(message, cause);
	}

	public CdfReducerException(String message) {
		super(message);
	}

	public CdfReducerException(Throwable cause) {
		super(cause);
	}

}
