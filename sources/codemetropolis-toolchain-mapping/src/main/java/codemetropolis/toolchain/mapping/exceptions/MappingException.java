package codemetropolis.toolchain.mapping.exceptions;

import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class MappingException extends CodeMetropolisException {

	private static final long serialVersionUID = -266528062447472817L;

	public MappingException() {
		super();
	}

	public MappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MappingException(String message, Throwable cause) {
		super(message, cause);
	}

	public MappingException(String message) {
		super(message);
	}

	public MappingException(Throwable cause) {
		super(cause);
	}

}
