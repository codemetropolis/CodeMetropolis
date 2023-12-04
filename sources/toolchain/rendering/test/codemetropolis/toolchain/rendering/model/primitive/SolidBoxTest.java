package codemetropolis.toolchain.rendering.model.primitive;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.util.Orientation;

public class SolidBoxTest {

	SolidBox solidBox;
	
	@Test
	public void testGetNumberOfBlocks() {
		solidBox = new SolidBox(
				new Point(0, 0, 0),
				new Point(2, 2, 2),
				new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:air" ), } } } ),
				new RepeationPattern( new BasicBlock[][][]{ { { new BasicBlock( "minecraft:air" ), } } } ),
				Orientation.NearX);
		int expected = 8;
		int actual = solidBox.getNumberOfBlocks();
		assertEquals(expected, actual);
	}
	
}

