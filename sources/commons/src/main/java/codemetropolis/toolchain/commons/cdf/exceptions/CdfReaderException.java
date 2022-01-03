package codemetropolis.toolchain.commons.cdf.exceptions;

import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class CdfReaderException extends CodeMetropolisException {

	private static final long serialVersionUID = -8880241135777702738L;

	public CdfReaderException() {
		super();
	}

	public CdfReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CdfReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public CdfReaderException(String message) {
		super(message);
	}

	public CdfReaderException(Throwable cause) {
		super(cause);
	}

}
