package codemetropolis.toolchain.commons.util;

public class Time {
	
	private long milliseconds;
	
	public Time(long milliseconds) {
		this.milliseconds = milliseconds;
	}
	
	public int getHours() {
		return (int) (milliseconds / (1000 * 60 * 60));
	}
	
	public int getMinutes() {
		return (int) ((milliseconds / (1000 * 60)) % 60);
	}
	
	public int getSeconds() {
		return (int) (milliseconds / 1000) % 60 ;
	}

}
