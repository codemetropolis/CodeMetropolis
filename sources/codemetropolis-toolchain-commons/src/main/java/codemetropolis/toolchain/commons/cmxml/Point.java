package codemetropolis.toolchain.commons.cmxml;

public class Point {
	
	private int x;
	private int y;
	private int z;
	
	public Point() {}
	
	public Point(int x, int z) {
		this(x, 0, z);
	}
	
	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point translate(Point offset) {
		return new Point(
			x + offset.getX(),
			y + offset.getY(),
			z + offset.getZ());
	}
	
	public int getX() {
		return x;
	}
	
	protected void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	protected void setY(int y) {
		this.y = y;
	}
	
	public int getZ() {
		return z;
	}
	
	protected void setZ(int z) {
		this.z = z;
	}
	
}
