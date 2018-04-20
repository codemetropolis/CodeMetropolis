package codemetropolis.toolchain.commons.util;

import junit.framework.TestCase;

public class TimeTest extends TestCase {

	Time testObj;
	
	public void setup() {
		testObj = null;
	}
	
	public void testGetHours() {
		testObj = new Time(0);
		assertEquals(0, testObj.getHours());
		
		testObj = new Time(3599999); // 0 hrs 59 mins 59 seconds, 999 milliseconds
		assertEquals(0, testObj.getHours());
		
		testObj = new Time(86400001); // 24 hrs 1 milliseconds
		assertEquals(24, testObj.getHours());
	}
	
}
