package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.Pattern;
import codemetropolis.toolchain.rendering.util.Orientation;

/**
 * A simpler version of {@link SolidBox}. It gets rid of the unnecessary stroke.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class SimpleBox extends SolidBox {

	public SimpleBox(Point basePoint, Point size, Pattern fill, Orientation orientation) {
		super(basePoint, size, fill, null, orientation);
	}

	/**
	 * Same as the one in {@link SolidBox}, just without stroke.
 	 * 
	 * {@inheritDoc}
	 */
	@Override
	public int toCSVFile(File directory) {
		for (int x = 0; x < size.getX(); x++) {
			for (int y = 0; y < size.getY(); y++) {
				for (int z = 0; z < size.getZ(); z++) {
					new Boxel(
						new BasicBlock(fill.applyTo(new Point(x, y, z), this::flipPattern)),
						new Point(basePoint.getX() + x, basePoint.getY() + y, basePoint.getZ() + z))
							.toCSVFile(directory);
				}
			}
		}
		
		return getNumberOfBlocks();
	}

}
