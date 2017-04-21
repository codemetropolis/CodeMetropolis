package codemetropolis.toolchain.mapping.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Limit}. Testing if after using {@code add} functions getter methods will work properly.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class LimitTest {
	
	private static final double DELTA = 1e-15;

	/**
	 * Check if size is correct when no values are added.
	 */
	@Test
	public void testGetValueTestSizeDefault() {
		Limit limit = new Limit();
		Assert.assertEquals(limit.getValueSetSize(), 0);
	}
	
	/**
	 * Check if size is correct when one positive value is added.
	 */
	@Test
	public void testGetValueTestSizeOne() {
		Limit limit = new Limit();
		limit.add(5);
		Assert.assertEquals(limit.getValueSetSize(), 1);
	}
	
	/**
	 * Check if size is correct when one negative value is added.
	 */
	@Test
	public void testGetValueTestSizeOneNegative() {
		Limit limit = new Limit();
		limit.add(-63234.2);
		Assert.assertEquals(limit.getValueSetSize(), 1);
	}
	
	/**
	 * Check if size is correct when two values are added.
	 */
	@Test
	public void testGetValueTestSizeTwo() {
		Limit limit = new Limit();
		limit.add(5342);
		limit.add(-6.2);
		Assert.assertEquals(limit.getValueSetSize(), 2);
	}
	
	/**
	 * Check if size is correct when more values are added.
	 */
	@Test
	public void testGetValueTestSizeMore() {
		Limit limit = new Limit();
		for(int i = -9; i < 30; i++) {
			limit.add(i);
		}
		Assert.assertEquals(limit.getValueSetSize(), 39);
	}
	
	/**
	 * Check if max value is correct when no values are added.
	 */
	@Test
	public void testGetMaxDefault() {
		Limit limit = new Limit();
		Assert.assertEquals(limit.getMax(), Double.NEGATIVE_INFINITY, DELTA);
	}	
	
	/**
	 * Check if max value is correct when one positive value is added.
	 */
	@Test
	public void testGetMaxOne() {
		Limit limit = new Limit();
		limit.add(5);
		Assert.assertEquals(limit.getMax(), 5, DELTA);
	}
	
	/**
	 * Check if max value is correct when one negative value is added.
	 */
	@Test
	public void testGetMaxOneNegative() {
		Limit limit = new Limit();
		limit.add(-63234.2);
		Assert.assertEquals(limit.getMax(), -63234.2, DELTA);
	}
	
	/**
	 * Check if max value is correct when two values are added.
	 */
	@Test
	public void testGetMaxTwo() {
		Limit limit = new Limit();
		limit.add(5342);
		limit.add(-6.2);
		Assert.assertEquals(limit.getMax(), 5342, DELTA);
	}
	
	/**
	 * Check if max value is correct when more values are added.
	 */
	@Test
	public void testGetMaxMore() {
		Limit limit = new Limit();
		for(int i = -9; i < 30; i++) {
			limit.add(i);
		}
		Assert.assertEquals(limit.getMax(), 29, DELTA);
	}
	
	/**
	 * Check if min value is correct when no values are added.
	 */
	@Test
	public void testGetMinDefault() {
		Limit limit = new Limit();
		Assert.assertEquals(limit.getMin(), Double.POSITIVE_INFINITY, DELTA);
	}	
	
	/**
	 * Check if min value is correct when one positive value is added.
	 */
	@Test
	public void testGetMinOne() {
		Limit limit = new Limit();
		limit.add(5);
		Assert.assertEquals(limit.getMin(), 5, DELTA);
	}
	
	/**
	 * Check if min value is correct when one negative value is added.
	 */
	@Test
	public void testGetMinOneNegative() {
		Limit limit = new Limit();
		limit.add(-63234.2);
		Assert.assertEquals(limit.getMin(), -63234.2, DELTA);
	}
	
	/**
	 * Check if min value is correct when two values are added.
	 */
	@Test
	public void testGetMinTwo() {
		Limit limit = new Limit();
		limit.add(5342);
		limit.add(-6.2);
		Assert.assertEquals(limit.getMin(), -6.2, DELTA);
	}
	
	/**
	 * Check if min value is correct when more values are added.
	 */
	@Test
	public void testGetMinMore() {
		Limit limit = new Limit();
		for(int i = -9; i < 30; i++) {
			limit.add(i);
		}
		Assert.assertEquals(limit.getMin(), -9, DELTA);
	}
}
