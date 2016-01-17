package codemetropolis.toolchain.commons.cmxml.exceptions;

public class CmxmlReaderException extends Exception {

	private static final long serialVersionUID = -8880241135777702738L;

	public CmxmlReaderException() {
		super();
	}

	public CmxmlReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CmxmlReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public CmxmlReaderException(String message) {
		super(message);
	}

	public CmxmlReaderException(Throwable cause) {
		super(cause);
	}

}
