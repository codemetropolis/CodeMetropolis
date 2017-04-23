package codemetropolis.toolchain.rendering.util;

public class Block {
	protected short id;
	protected short data;
	protected String ign;
	protected String humanReadableName;
	protected short hazardous;
	
	public short getId() {
		return id;
	}
	public short getData() {
		return data;
	}
	public String getIgn() {
		return ign;
	}
	public String getHumanReadableName() {
		return humanReadableName;
	}
	public short getHazardous() {
		return hazardous;
	}
	
	public Block(short id, short data, String ign, String human, short hazard){
		this.id = id;
		this.data = data;
		this.ign = ign;
		this.humanReadableName = human;
		this.hazardous = hazard;
	}
}
