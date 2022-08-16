package codemetropolis.toolchain.rendering.util;

import org.apache.commons.lang3.RandomUtils;

public class Chance {
	
	private float percentage;
	public Chance(float percentage) {
		this.percentage = percentage;
	}
	
	public boolean kick() {
		int randomlyGeneratedNumber = RandomUtils.nextInt(0, 100);
		return (float)randomlyGeneratedNumber <= this.percentage;
	}

}
