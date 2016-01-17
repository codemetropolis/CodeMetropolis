package codemetropolis.toolchain.commons.cmxml.exceptions;

public class CmxmlWriterException extends Exception {

	private static final long serialVersionUID = 5295776370234269200L;

	public CmxmlWriterException() {
		super();
	}

	public CmxmlWriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CmxmlWriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public CmxmlWriterException(String message) {
		super(message);
	}

	public CmxmlWriterException(Throwable cause) {
		super(cause);
	}

}
