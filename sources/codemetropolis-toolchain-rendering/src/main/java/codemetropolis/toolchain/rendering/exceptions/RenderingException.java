package codemetropolis.toolchain.rendering.exceptions;

public class RenderingException extends Exception {

	private static final long serialVersionUID = 7171725393210241725L;

	public RenderingException() {
		super();
	}

	public RenderingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RenderingException(String message, Throwable cause) {
		super(message, cause);
	}

	public RenderingException(String message) {
		super(message);
	}

	public RenderingException(Throwable cause) {
		super(cause);
	}
	
}
