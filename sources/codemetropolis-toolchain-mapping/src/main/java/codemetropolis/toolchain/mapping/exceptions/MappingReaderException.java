package codemetropolis.toolchain.mapping.exceptions;

public class MappingReaderException extends MappingException {
	
	private static final long serialVersionUID = -4234316064798036413L;

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
