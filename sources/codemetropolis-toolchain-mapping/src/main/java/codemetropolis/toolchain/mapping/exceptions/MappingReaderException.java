package codemetropolis.toolchain.mapping.exceptions;

public class MappingReaderException extends Exception {

	private static final long serialVersionUID = -8880241135777702738L;

	public MappingReaderException() {
		super();
	}

	public MappingReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MappingReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public MappingReaderException(String message) {
		super(message);
	}

	public MappingReaderException(Throwable cause) {
		super(cause);
	}

}
