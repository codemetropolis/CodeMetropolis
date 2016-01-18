package codemetropolis.toolchain.rendering.events;

import java.util.EventListener;

public interface ProgressEventListener extends EventListener {
	public void onNextState(ProgressEvent event);
}
