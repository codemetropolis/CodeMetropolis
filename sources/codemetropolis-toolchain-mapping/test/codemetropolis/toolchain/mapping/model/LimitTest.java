package codemetropolis.toolchain.mapping.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class LimitTest {
	
	public Limit limit;

	@Before
	public void setUp() throws Exception {
		limit = new Limit();
	}
	
	@Test
	public void testAdd() {
		limit.add(2.4);
		limit.add(6.78);
		limit.add(100.1);
		limit.add(500);
		limit.add(0.000001);
		
		int actualValueSetSize = limit.getValueSetSize();
		int expectedValueSetSize = 5;
		assertEquals(expectedValueSetSize, actualValueSetSize);
		
		double actualMax = limit.getMax();
		double expectedMax = 500;
		assertEquals(expectedMax, actualMax, 0);
		
		double actualMin = limit.getMin();
		double expectedMin = 0.000001;
		assertEquals(expectedMin, actualMin, 0);
	}
}
