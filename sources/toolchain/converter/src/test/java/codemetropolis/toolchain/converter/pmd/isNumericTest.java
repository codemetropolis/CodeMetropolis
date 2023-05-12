package codemetropolis.toolchain.converter.pmd;

import org.junit.*;
import org.junit.After;
import org.junit.Test;

public class isNumericTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String str="asd";
		String num="123";
		PmdConverter c=new PmdConverter(null);
		
		Assert.assertTrue(c.isNumeric(num));
		
		Assert.assertFalse(c.isNumeric(str));
		
		
		//fail("Not yet implemented");
	}

}
