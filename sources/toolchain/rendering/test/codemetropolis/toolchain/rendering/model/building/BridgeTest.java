package codemetropolis.toolchain.rendering.model.building;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;

/** Test class for {@link Bridge} for basic functions.
 * 
 * @author Csuvik Viktor {@literal D1YZL5}
 * @version %I%
 *
 */


public class BridgeTest {

	private static Buildable parent;
	private static Bridge bridge;
	private static int BRIDGE_HEIGHT = 3;
	private static int BRIDGE_WIDTH = 3;
	
	@BeforeClass
	public static void setUpBeforeClass() { 

		Buildable b = new Buildable( 
				"UNIQUE_ID",
				"SAMPLE_BRIDGE",
				Buildable.Type.BRIDGE,
				new Point(0, 0, 0),
				new Point(10, BRIDGE_HEIGHT, BRIDGE_WIDTH));
		
		b.addAttribute(Linking.LINKING_ATTRIBUTE_TARGET, "TARGET_UNIQUE_ID");
		b.addAttribute(Linking.LINKING_ATTRIBUTE_STANDALONE, "false");
		b.addAttribute(Linking.LINKING_ATTRIBUTE_ORIENTATION, "south");
		
		
		parent = new Buildable( 
				"UNIQUE_ID2",
				"SAMPLE_PARENT",
				Buildable.Type.GARDEN,
				new Point(0, 0, 0),
				new Point(10, 10, 10) );
		
		parent.addChild(b);
		
		
		try {
			bridge = new Bridge( b );
		} catch (RenderingException e) {
			fail("Shouldn't throw exception.");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testCalculatStepPosition() {
		assertTrue( bridge.calculateStepPosition(true).getX() == bridge.innerBuildable.getPositionX() ||
					bridge.calculateStepPosition(true).getZ() == bridge.innerBuildable.getPositionZ());
		
		assertFalse( bridge.calculateStepPosition(true).getX() == bridge.innerBuildable.getPositionX() &&
				bridge.calculateStepPosition(true).getZ() == bridge.innerBuildable.getPositionZ());
	}
}
