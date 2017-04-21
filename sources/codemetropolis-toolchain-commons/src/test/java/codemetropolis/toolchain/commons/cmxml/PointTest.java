package codemetropolis.toolchain.commons.cmxml;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Point}. Testing functions on different positions.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class PointTest {

	/**
	 * Check if given points equality is determined correctly.
	 */
	@Test
	public void testEquals() {
		Assert.assertTrue(new Point(0, 0, 0).equals(new Point(0, 0, 0)));
	}

	/**
	 * Check if given points inequality is determined correctly.
	 */
	@Test
	public void testNonEquals() {
		Assert.assertFalse(new Point(0, 0, 0).equals(new Point(0, 2, 0)));
	}

	/**
	 * Check if the point won't be translated when there is no translation in origo.
	 */
	@Test
	public void testZeroTranslateFromZeroAll() {
		Point testPoint = new Point(0, 0, 0);

		Assert.assertEquals(new Point(0, 0, 0), testPoint.translate(new Point(0, 0, 0)));
	}

	/**
	 * Check if negative point will be in the right point after translation in all directions.
	 */
	@Test
	public void testTranslateFromNegativeAll() {
		Point testPoint = new Point(-8, -10, -9);

		Assert.assertEquals(new Point(-12, -5, -10), testPoint.translate(new Point(-4, 5, -1)));
	}

	/**
	 * Check if positive point will be in the right point after translation in all directions.
	 */
	@Test
	public void testTranslateFromPositiveAll() {
		Point testPoint = new Point(8, 10, 9);

		Assert.assertEquals(new Point(4, 15, 8), testPoint.translate(new Point(-4, 5, -1)));
	}

	/**
	 * Check if negative point will be in the right point after translation in one direction.
	 */
	@Test
	public void testTranslateFromNegativeOne() {
		Point testPoint = new Point(-8, -10, -9);

		Assert.assertEquals(new Point(-12, -10, -9), testPoint.translate(new Point(-4, 0, 0)));
	}

	/**
	 * Check if negative point will be in the right point after translation in two directions.
	 */
	@Test
	public void testTranslateFromNegativeTwo() {
		Point testPoint = new Point(-8, -10, -9);

		Assert.assertEquals(new Point(-12, -10, -4), testPoint.translate(new Point(-4, 0, 5)));
	}

	/**
	 * Check if positive point will be in the right point after translation in one direction.
	 */
	@Test
	public void testPositiveTranslateFromPositiveOne() {
		Point testPoint = new Point(8, 10, 9);

		Assert.assertEquals(new Point(12, 10, 9), testPoint.translate(new Point(4, 0, 0)));
	}

	/**
	 * Check if positive point will be in the right point after translation in two direction.
	 */
	@Test
	public void testPositiveTranslateFromPositiveTwo() {
		Point testPoint = new Point(8, 10, 9);

		Assert.assertEquals(new Point(12, 15, 9), testPoint.translate(new Point(4, 5, 0)));
	}

}
