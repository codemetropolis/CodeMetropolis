package codemetropolis.toolchain.rendering.model.building;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.control.WorldBuilder;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;


/** Test class for {@link Tunnel} for basic functions.
 * 
 * @author Csuvik Viktor {@literal D1YZL5}
 * @version %I%
 *
 */


public class TunnelTest {

	private static Buildable parent;
	private static Tunnel tunnel;
	private static int TUNNEL_HEIGHT = 3;
	private static int TUNNEL_WIDTH = 3;
	
	@BeforeClass
	public static void setUpBeforeClass() { 
		
		Buildable b = new Buildable( 
				"UNIQUE_ID",
				"SAMPLE_TUNNEL",
				Buildable.Type.TUNNEL,
				new Point(0, 0, 0),
				new Point(10, TUNNEL_HEIGHT, TUNNEL_WIDTH));
		
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
			tunnel = new Tunnel( b );
		} catch (RenderingException e) {
			fail("Shouldn't throw exception.");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testTunnelPosition() {
		assertEquals(tunnel.getPosition().getX(), 0);
		assertEquals(tunnel.getPosition().getY(), 0);
		assertEquals(tunnel.getPosition().getZ(), 0);
	}
	
	@Test
	public void testTunnelSize() {
		assertEquals(tunnel.getSize().getX(), 11);
		assertEquals(tunnel.getSize().getY(), 3);
		assertEquals(tunnel.getSize().getZ(), 3);
	}
	
	@Test
	public void testGetTarget() {
		Buildable target = tunnel.getTarget(parent, "UNIQUE_ID");
		assertSame(target, tunnel.innerBuildable);
		
		target = tunnel.getTarget(parent, "NON_EXISTING_ID");
		assertNull(target);
		
		target = tunnel.getTarget(parent, parent.getId());
		assertSame(target, parent);
	}
	
	@Test
	public void testCalculateHeight() {
		assertTrue(tunnel.calculateHeight(tunnel.innerBuildable) <= WorldBuilder.TUNNEL_LEVEL - TUNNEL_HEIGHT);
	}
	
	@Test
	public void testCalculatStepPosition() {
		assertTrue( tunnel.calculateStepPosition(true).getX() == tunnel.innerBuildable.getPositionX() ||
					tunnel.calculateStepPosition(true).getZ() == tunnel.innerBuildable.getPositionZ());
	}
	
	@Test
	public void testLampPositionY() {
		SolidBox lampLine = tunnel.getLighting().get(0);
		
		int actual = lampLine.getBasePoint().getY();
		int expected = tunnel.level - 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPrepareLightingReturnsValidList() {
		List<SolidBox> lighting = tunnel.prepareLighting();
		
		assertNotNull(lighting);
		
		int expectedSize = 2;
		int actualSize = lighting.size();
		assertEquals(expectedSize, actualSize);
	}
	
	@Test
	public void testPrepareLightingReturnsElementsNotNull() {
		List<SolidBox> lighting = tunnel.prepareLighting();
		
		SolidBox lampLine = lighting.get(0);
		assertNotNull(lampLine);
		
		SolidBox redstoneBlockLine = lighting.get(1);
		assertNotNull(redstoneBlockLine);
	}
	
	@Test
	public void testPrepareLightningReturnsRightLampline() {
		List<SolidBox> lighting = tunnel.prepareLighting();
		SolidBox lampLine = lighting.get(0);
		
		int actualPositionY = lampLine.getBasePoint().getY();
		int expectedPositionY = tunnel.level - 1;
		assertEquals(expectedPositionY, actualPositionY);
		
		int actualNBlocks = lampLine.getNumberOfBlocks();
		int expectedNBlocks = Math.abs((tunnel.getSize().getX()-2) * (tunnel.getSize().getY()-2) * (tunnel.getSize().getZ()-2));
		assertEquals(expectedNBlocks, actualNBlocks);
	}
	
	@Test
	public void testPrepareLightningReturnsRightRedstoneLine() {
		List<SolidBox> lighting = tunnel.prepareLighting();
		SolidBox redstoneLine = lighting.get(1);
		
		int actualPositionY = redstoneLine.getBasePoint().getY();
		int expectedPositionY = tunnel.level - 2;
		assertEquals(expectedPositionY, actualPositionY);
		
		int actualNBlocks = redstoneLine.getNumberOfBlocks();
		int expectedNBlocks = Math.abs((tunnel.getSize().getX()-2) * (tunnel.getSize().getY()-2) * (tunnel.getSize().getZ()-2));
		assertEquals(expectedNBlocks, actualNBlocks);
	}
	
}

