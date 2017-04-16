package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.Pattern;
import codemetropolis.toolchain.rendering.util.Orientation;

public class SimpleBox extends SolidBox {

	public SimpleBox(Point basePoint, Point size, Pattern fill, Orientation orientation) {
		super(basePoint, size, fill, null, orientation);
	}
	
	@Override
	public int toCSVFile(File directory) {
		for ( int x = 0; x < size.getX(); x++ ) {
			for ( int y = 0; y < size.getY(); y++ ) {
				for ( int z = 0; z < size.getZ(); z++ ) {
					new Boxel(
						new BasicBlock( fill.applyTo( new Point( x, y, z ), this::flipPattern ) ),
						new Point( basePoint.getX() + x, basePoint.getY() + y, basePoint.getZ() + z ))
					.toCSVFile(directory);
				}
			}
		}
		return getNumberOfBlocks();
	}
	
}
