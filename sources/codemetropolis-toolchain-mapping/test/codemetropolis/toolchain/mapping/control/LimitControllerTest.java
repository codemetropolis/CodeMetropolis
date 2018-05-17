package codemetropolis.toolchain.mapping.control;

import org.junit.Test;

import codemetropolis.toolchain.mapping.model.Limit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;

public class LimitControllerTest {

	LimitController control;
	
	@Before
	public void setUp() {
		control = new LimitController();
	}
	
	@Test
	public void testAdd() {
		String sourceName = "name";
		String sourceFrom = "from";
		
		Limit nullLimit = control.getLimit(sourceName, sourceFrom);
		assertNull(nullLimit);
		
		control.add("name", "from", 143);
		Limit notNullLimit = control.getLimit(sourceName, sourceFrom);
		assertNotNull(notNullLimit);
		
		assertEquals(143, notNullLimit.getMax(), 0);
		assertEquals(143, notNullLimit.getMin(), 0);
	}
}
