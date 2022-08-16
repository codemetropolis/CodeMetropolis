package codemetropolis.toolchain.cdfreducer.exceptions;

public class CdfReducerWriterException extends CdfReducerException {

	private static final long serialVersionUID = -3708470145539789698L;

	public CdfReducerWriterException() {
		super();
	}

	public CdfReducerWriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CdfReducerWriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public CdfReducerWriterException(String message) {
		super(message);
	}

	public CdfReducerWriterException(Throwable cause) {
		super(cause);
	}

}
