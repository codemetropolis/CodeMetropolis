package codemetropolis.toolchain.placing.layout.pack;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.placing.exceptions.LayoutException;
import codemetropolis.toolchain.placing.exceptions.NonExistentLayoutException;
import codemetropolis.toolchain.placing.layout.Layout;



/** Test class for {@link PackLayout} for testing tunnel and bridge packing
 * 
 * @author Csuvik Viktor {@literal D1YZL5}
 * @version %I%
 *
 */

public class PackLayoutTest {

	private static Layout layout;
	private static BuildableTree buildables;
	private static Buildable bridge;
	private static Buildable tunnel;
	private static Buildable parent;
	
	private static final int GROUND_LEVEL = 60;
	private static final Point initailParentPosition = new Point(0, 0, 0);
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			layout = Layout.parse("PACK");
		} catch (NonExistentLayoutException e) {
			fail("Shouldn't throw exception.");
			e.printStackTrace();
		}
		
		bridge = new Buildable( 
				"UNIQUE_ID1",
				"SAMPLE_BRIDGE",
				Buildable.Type.BRIDGE,
				new Point(0, 0, 0),
				new Point(10, 3, 4));
		
		tunnel = new Buildable( 
				"UNIQUE_ID2",
				"SAMPLE_TUNNEL",
				Buildable.Type.TUNNEL,
				new Point(0, 10, 0),
				new Point(10, 3, 4));
		
		parent = new Buildable( 
				"UNIQUE_ID2",
				"SAMPLE_PARENT",
				Buildable.Type.GARDEN,
				new Point(0, 0, 0),
				new Point(10, 10, 10) );
		
		parent.addChild(bridge);
		parent.addChild(tunnel);
		
		buildables = new BuildableTree(parent);
	}
	
	
	@Test
	public void testApply() {
		try {
			layout.apply(buildables);
		} catch (LayoutException e) {
			fail("Shouldn't throw exception.");
			e.printStackTrace();
		}
		
		assertEquals(parent.getPositionX(), initailParentPosition.getX());
		assertEquals(parent.getPositionZ(), initailParentPosition.getZ());
		assertEquals(parent.getPositionZ(), initailParentPosition.getZ() + GROUND_LEVEL);
	}

}
