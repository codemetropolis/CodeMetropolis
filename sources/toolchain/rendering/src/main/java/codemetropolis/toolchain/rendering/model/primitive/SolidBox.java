package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.Paintable;
import codemetropolis.toolchain.rendering.model.pattern.Pattern;
import codemetropolis.toolchain.rendering.util.Orientation;

public class SolidBox extends Paintable implements Primitive {

	public Point basePoint;
	public Point size;
	public Orientation orientation;
	
	public SolidBox(Point basePoint, Point size, Pattern fill, Pattern stroke, Orientation orientation) {
		super(fill, stroke);
		this.basePoint = basePoint;
		this.size = size;
		this.orientation = orientation;
	}
	
	protected Point flipPattern(Point original, Point size) {
		switch ( orientation ) {
			case NearX:
				return original;
			case NearY:
				return new Point( original.getY(), original.getX(), original.getZ() );
			case NearZ:
				return new Point( original.getZ(), original.getY(), original.getX() );
			case FarX:
				return new Point( ( size.getX() - 1 ) - ( original.getX() % size.getX() ), original.getY(), original.getZ() );
			case FarY:
				return new Point( ( size.getY() - 1 ) - ( original.getY() % size.getY() ), original.getX(), original.getZ() );
			case FarZ:
				return new Point( ( size.getZ() - 1 ) - ( original.getZ() % size.getZ() ), original.getY(), original.getX() );
			default:
				return null;
		}
	}
	
	public int toCSVFile(File directory) {
		for ( int x = 0; x < size.getX(); x++ ) {
			for ( int y = 0; y < size.getY(); y++ ) {
				for ( int z = 0; z < size.getZ(); z++ ) {
					if ( (( x == 0 || x == size.getX() - 1 ) && ( y == 0 || y == size.getY() - 1 )) ||
					     (( x == 0 || x == size.getX() - 1 ) && ( z == 0 || z == size.getZ() - 1 )) ||
					     (( y == 0 || y == size.getY() - 1 ) && ( z == 0 || z == size.getZ() - 1 )) )
					{
						new Boxel(
							new BasicBlock( stroke.applyTo( new Point( x, y, z ), this::flipPattern ) ),
							new Point( basePoint.getX() + x, basePoint.getY() + y, basePoint.getZ() + z ))
						.toCSVFile(directory);
					} else {
						new Boxel(
							new BasicBlock( fill.applyTo( new Point( x, y, z ), this::flipPattern ) ),
							new Point( basePoint.getX() + x, basePoint.getY() + y, basePoint.getZ() + z ))
						.toCSVFile(directory);
					}
				}
			}
		}
		return getNumberOfBlocks();
	}

	public Point getBasePoint() {
		return basePoint;
	}

	public Point getSize() {
		return size;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public int getNumberOfBlocks() {
		return size.getX() * size.getY() * size.getZ();
	}

}
