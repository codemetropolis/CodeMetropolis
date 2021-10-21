package codemetropolis.toolchain.rendering.exceptions;

public class TooLongRenderDurationException extends RenderingException {

	private static final long serialVersionUID = -7647174616882103244L;

	private int estimatedTime;
	private int maxTime;
	
	public TooLongRenderDurationException(int estimatedTime, int maxTime) {
		super();
		this.estimatedTime = estimatedTime;
		this.maxTime = maxTime;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}

	public int getMaxTime() {
		return maxTime;
	}
	
}
