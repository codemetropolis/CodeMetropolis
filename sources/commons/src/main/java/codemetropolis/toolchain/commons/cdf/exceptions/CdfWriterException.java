package codemetropolis.toolchain.commons.cdf.exceptions;

public class CdfWriterException extends Exception {

	private static final long serialVersionUID = 5295776370234269200L;

	public CdfWriterException() {
		super();
	}

	public CdfWriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CdfWriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public CdfWriterException(String message) {
		super(message);
	}

	public CdfWriterException(Throwable cause) {
		super(cause);
	}

}
