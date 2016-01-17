package codemetropolis.toolchain.rendering.events;

import java.util.EventObject;

import codemetropolis.toolchain.commons.util.Time;
import codemetropolis.toolchain.rendering.control.BuildPhase;

public class ProgressEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	
	public final BuildPhase PHASE;
	public final long COUNT;
	public final long TOTAL;
	public final long TIME_ELAPSED_IN_MILLIS;

	public ProgressEvent(Object source, BuildPhase phase, long count, long total, long timeElapsedInMillis) {
		super(source);
		this.PHASE = phase;
		this.COUNT = count;
		this.TOTAL = total;
		this.TIME_ELAPSED_IN_MILLIS = timeElapsedInMillis;
	}
	
	public double getPercent() {
		return (double)COUNT / TOTAL * 100;
	}
	
	public long getTimeLeftInMillis() {
		return (long) ((double)TIME_ELAPSED_IN_MILLIS / COUNT * (TOTAL - COUNT));
	}
	
	public Time getTimeLeft() {
		return new Time(getTimeLeftInMillis());
	}

}
