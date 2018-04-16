package codemetropolis.toolchain.commons.cmxml;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable.Type;

/** Test class for {@link Tunnel} to test basic functions.
 * 
 * @author Csuvik Viktor {@literal D1YZL5}
 * @version %I%
 *
 */

public class BuildableTest {

	private static Buildable b1;
	private static Buildable b2;
	
	@BeforeClass
	public static void setUpBeforeClass() { 
		b1 = new Buildable("UNIQUE_ID_1", "SAMPLE_TUNNEL_1", Type.TUNNEL, new Point(0, 0, 0), new Point(10, 5, 2));
		b2 = new Buildable("UNIQUE_ID_2", "SAMPLE_TUNNEL_2", Type.TUNNEL, new Point(0, 0, 0), new Point(2, 5, 10));
		
		b1.addChild(b2);
	}
	
	
	@Test
	public void testIsOverlapping() {

		assertTrue(b1.isOverlapping(b2));
		assertTrue(b1.isOverlapping(b2.getPositionX(), b2.getPositionZ()));
		
		b2.setPositionX(11);
		assertFalse(b1.isOverlapping(b2));
		assertFalse(b1.isOverlapping(b2.getPositionX(), b2.getPositionZ()));
	}
	
	@Test
	public void testGetDescendants() {
		List<Buildable> buildables1 = b1.getDescendants();
		
		List<Buildable> buildables2 = new ArrayList<Buildable>();
		buildables2.add(b2);
		
		assertArrayEquals(buildables1.toArray(), buildables2.toArray());
	}
	
	@Test
	public void testGetAncestors() {
		List<Buildable> buildables = new ArrayList<Buildable>();
		buildables.add(b1);
		
		assertArrayEquals(buildables.toArray(), b2.getAncestors());
	}
	
	@Test
	public void testEquals() {
		assertTrue(b1.equals(b1));
		assertTrue(b2.equals(b2));
		
		assertFalse(b1.equals(b2));
		assertFalse(b2.equals(b1));
		
		assertFalse(b1.equals(null));
		
		
		Buildable b3 = new Buildable("UNIQUE_ID_1", "SAMPLE_TUNNEL_1", Type.TUNNEL, new Point(0, 0, 0), new Point(10, 5, 2));
		assertTrue(b1.equals(b3));
		
		b3.setName("SOMETHING_ELSE");
		assertFalse(b1.equals(b3));
		
		b3.setName("SAMPLE_TUNNEL_1");
		b3.setType(Type.GARDEN);
		assertFalse(b3.equals(b1));
	}
}
