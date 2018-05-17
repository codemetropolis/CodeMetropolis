package codemetropolis.toolchain.commons.cmxml.comparators;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;

/** Test class for {@link Tunnel}, to test size comparator.
 * 
 * @author Csuvik Viktor {@literal D1YZL5}
 * @version %I%
 *
 */


public class BuildableSizeComparatorTest {

	@Test
	public void testCompare() {
		BuildableSizeComparator c = new BuildableSizeComparator();
		Buildable b1 = new Buildable("UNIQUE_ID_1", "SAMPLE_TUNNEL_1", Type.TUNNEL, new Point(0, 0, 0), new Point(10, 10, 10));
		Buildable b2 = new Buildable("UNIQUE_ID_2", "SAMPLE_TUNNEL_2", Type.TUNNEL, new Point(0, 0, 0), new Point(10, 10, 10));
		assertTrue(c.compare(b1, b2) == 0);
		
		Buildable b3 = new Buildable("UNIQUE_ID_3", "SAMPLE_TUNNEL_3", Type.TUNNEL, new Point(0, 0, 0), new Point(15, 15, 15));
		assertTrue(c.compare(b1, b3) != 0);
		
		Buildable b4 = new Buildable("UNIQUE_ID_4", "SAMPLE_GARDEN_1", Type.GARDEN, new Point(0, 0, 0), new Point(10, 10, 10));
		assertTrue(c.compare(b1, b4) == 0);
	}
	
}
