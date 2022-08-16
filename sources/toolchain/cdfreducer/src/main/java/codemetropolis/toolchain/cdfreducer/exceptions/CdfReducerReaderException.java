package codemetropolis.toolchain.cdfreducer.exceptions;

public class CdfReducerReaderException extends CdfReducerException {

	private static final long serialVersionUID = -4234316064798036413L;

	public CdfReducerReaderException() {
		super();
	}

	public CdfReducerReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CdfReducerReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public CdfReducerReaderException(String message) {
		super(message);
	}

	public CdfReducerReaderException(Throwable cause) {
		super(cause);
	}

}
