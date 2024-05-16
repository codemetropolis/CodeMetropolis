package codemetropolis.toolchain.converter.sonarqube;

import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class SonarConnectException extends CodeMetropolisException {

	private static final long serialVersionUID = -5206994497081967248L;

	public SonarConnectException() {
		super();
	}

	public SonarConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SonarConnectException(String message, Throwable cause) {
		super(message, cause);
	}

	public SonarConnectException(String message) {
		super(message);
	}

	public SonarConnectException(Throwable cause) {
		super(cause);
	}

}
