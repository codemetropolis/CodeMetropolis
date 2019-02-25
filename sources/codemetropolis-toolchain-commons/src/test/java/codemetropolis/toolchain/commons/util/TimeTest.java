package codemetropolis.toolchain.commons.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Time}. Testing functions on different times.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class TimeTest {

	/**
	 * Test for zero time.
	 */
	@Test
	public void testZero() {
		Time time = new Time(0);

		Assert.assertEquals(0, time.getHours());
		Assert.assertEquals(0, time.getMinutes());
		Assert.assertEquals(0, time.getSeconds());
	}

	/**
	 * Test for 999ms.
	 */
	@Test
	public void testBarelyLessThanOneSecond() {
		Time time = new Time(999);

		Assert.assertEquals(0, time.getHours());
		Assert.assertEquals(0, time.getMinutes());
		Assert.assertEquals(0, time.getSeconds());
	}

	/**
	 * Test for 1s.
	 */
	@Test
	public void testOneSecond() {
		Time time = new Time(1000);

		Assert.assertEquals(0, time.getHours());
		Assert.assertEquals(0, time.getMinutes());
		Assert.assertEquals(1, time.getSeconds());
	}

	/**
	 * Test for 30s.
	 */
	@Test
	public void testHalfMinute() {
		Time time = new Time(30000);

		Assert.assertEquals(0, time.getHours());
		Assert.assertEquals(0, time.getMinutes());
		Assert.assertEquals(30, time.getSeconds());
	}

	/**
	 * Test for 59s 999ms.
	 */
	@Test
	public void testFiftyNineSeconds() {
		Time time = new Time(59999);

		Assert.assertEquals(0, time.getHours());
		Assert.assertEquals(0, time.getMinutes());
		Assert.assertEquals(59, time.getSeconds());
	}

	/**
	 * Test for 60s. It checks if it turns into 1m instead of 60s.
	 */
	@Test
	public void testSixtySecond() {
		Time time = new Time(60000);

		Assert.assertEquals(0, time.getHours());
		Assert.assertEquals(1, time.getMinutes());
		Assert.assertEquals(0, time.getSeconds());
	}

	/**
	 * Test for 30m.
	 */
	@Test
	public void testHalfHour() {
		Time time = new Time(1800000);

		Assert.assertEquals(0, time.getHours());
		Assert.assertEquals(30, time.getMinutes());
		Assert.assertEquals(0, time.getSeconds());
	}

	/**
	 * Test for 59m 59s.
	 */
	@Test
	public void testFiftyNineMinutes() {
		Time time = new Time(3599999);

		Assert.assertEquals(0, time.getHours());
		Assert.assertEquals(59, time.getMinutes());
		Assert.assertEquals(59, time.getSeconds());
	}

	/**
	 * Test for 60m. It checks if it turns into 1h instead of 60m.
	 */
	@Test
	public void testSixtyMinutes() {
		Time time = new Time(3600000);

		Assert.assertEquals(1, time.getHours());
		Assert.assertEquals(0, time.getMinutes());
		Assert.assertEquals(0, time.getSeconds());
	}

	/**
	 * Test for 1h 1m.
	 */
	@Test
	public void testOneHourOneMinute() {
		Time time = new Time(3660000);

		Assert.assertEquals(1, time.getHours());
		Assert.assertEquals(1, time.getMinutes());
		Assert.assertEquals(0, time.getSeconds());
	}

	/**
	 * Test for 1h 1m 1s.
	 */
	@Test
	public void testOneHourOneMinuteOneSecond() {
		Time time = new Time(3661000);

		Assert.assertEquals(1, time.getHours());
		Assert.assertEquals(1, time.getMinutes());
		Assert.assertEquals(1, time.getSeconds());
	}

	/**
	 * Test for 22h 33m 20s.
	 */
	@Test
	public void testTwentyHoursThrityTwoMinutesTwentySeconds() {
		Time time = new Time(81200000);

		Assert.assertEquals(22, time.getHours());
		Assert.assertEquals(33, time.getMinutes());
		Assert.assertEquals(20, time.getSeconds());
	}

	/**
	 * Test for 4h 4s.
	 */
	@Test
	public void testFourHoursFourSeconds() {
		Time time = new Time(14404000);

		Assert.assertEquals(4, time.getHours());
		Assert.assertEquals(0, time.getMinutes());
		Assert.assertEquals(4, time.getSeconds());
	}

	/**
	 * Test for 2d 2h 7m 3s 627ms.
	 */
	@Test
	public void testTwoDaysTwoHoursSevenMinutesThreeSeconds() {
		Time time = new Time(180423627);

		Assert.assertEquals(50, time.getHours());
		Assert.assertEquals(7, time.getMinutes());
		Assert.assertEquals(3, time.getSeconds());
	}

}
