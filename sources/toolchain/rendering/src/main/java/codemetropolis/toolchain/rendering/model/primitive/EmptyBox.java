package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;
import java.util.LinkedList;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.pattern.Pattern;
import codemetropolis.toolchain.rendering.util.Orientation;

public class EmptyBox implements Primitive {

	private LinkedList<SolidBox> sides = new LinkedList<SolidBox>( );

	private Point nearSideWitdth;
	private Point farSideWitdth;
	private Point basePoint;
	private Point size;
	
	public EmptyBox(Point basePoint, Point size, Pattern bottomFill, Pattern topFill, Pattern sideFill, Pattern stroke, Point nearSideWitdth, Point farSideWitdth ) {
		super();
		this.nearSideWitdth = nearSideWitdth;
		this.farSideWitdth = farSideWitdth;
		this.basePoint = basePoint;
		this.size = size;
		
		sides.add(
				new SolidBox(
					new Point( basePoint.getX(), basePoint.getY(), basePoint.getZ() ),
					new Point( nearSideWitdth.getX(), size.getY(), size.getZ() ),
					sideFill,
					stroke,
					Orientation.NearX )
				);
		sides.add(
				new SolidBox(
					new Point( basePoint.getX() + size.getX()-1, basePoint.getY(), basePoint.getZ() ),
					new Point( farSideWitdth.getX(), size.getY(), size.getZ() ),
					sideFill,
					stroke,
					Orientation.FarX )
				);

		sides.add(
				new SolidBox(
					new Point( basePoint.getX(), basePoint.getY(), basePoint.getZ() ),
					new Point( size.getX(), size.getY(), nearSideWitdth.getZ() ),
					sideFill,
					stroke,
					Orientation.NearZ )
				);
		sides.add(
				new SolidBox(
					new Point( basePoint.getX(), basePoint.getY(), basePoint.getZ() + size.getZ() - 1 ),
					new Point( size.getX(), size.getY(), farSideWitdth.getZ() ),
					sideFill,
					stroke,
					Orientation.FarZ )
				);

		sides.add(
				new SolidBox(
					new Point( basePoint.getX(), basePoint.getY(), basePoint.getZ() ),
					new Point( size.getX(), nearSideWitdth.getY(), size.getZ() ),
					bottomFill,
					stroke,
					Orientation.NearY )
				);
		sides.add(
				new SolidBox(
					new Point( basePoint.getX(), basePoint.getY() + size.getY() - 1, basePoint.getZ() ),
					new Point( size.getX(), farSideWitdth.getY(), size.getZ() ),
					topFill,
					stroke,
					Orientation.FarY )
				);
	}
	
	public int toCSVFile(File directory) {
		int count = 0;
		for(SolidBox s : sides) {
			count += s.toCSVFile(directory);
		}
		return count;
	}
	
	public Point getNearSideWitdth() {
		return nearSideWitdth;
	}
	public Point getFarSideWitdth() {
		return farSideWitdth;
	}
	public Point getBasePoint() {
		return basePoint;
	}
	public Point getSize() {
		return size;
	}

	public int getNumberOfBlocks() {
		int result = 0;
		for(SolidBox s : sides)
			result += s.getNumberOfBlocks();
		return result;
	}
	
}
