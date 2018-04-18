package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import org.junit.Test;

import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class DownscalePossibleLargeNumericValueTest {

	private static String[] squareNumbers = {"0", "1", "4", "16", "81", "100", "256", "3481", "7569", "16129", "205234276"};
	private static String[] nonSquareNumbers = {"0", "2", "7", "20", "82", "107", "270", "3490", "7583", "16142", "205234385"};
	private static String[] expectedResults = {"0", "1", "2", "4", "9", "10", "16", "59", "87", "127", "14326"};
	private static String[] negativeNumbers = {"-0", "-2", "-7", "-20", "-82", "-107", "-270", "-3490", "-7583", "-16142", "-205234385"};
	private static String[] nonNumbers = {"", "A", "AB", "CodeMetropolis", "GitConverter", "1234asdfg234"};

	@Test
	public void testWithSuqareNumbers() {
		for (int i = 0; i < squareNumbers.length; ++i) {
			String result = GitInspectorConverter.downscalePossibleLargeNumericValue(squareNumbers[i]);
			assertEquals(expectedResults[i], result);
		}
	}

	@Test
	public void testWithNonSuqareNumbers() {
		for (int i = 1; i < squareNumbers.length; ++i) {
			String result = GitInspectorConverter.downscalePossibleLargeNumericValue(nonSquareNumbers[i]);
			assertEquals(expectedResults[i], result);
		}
	}

	@Test
	public void testWithNegativeNumbers() {
		for (int i = 0; i < negativeNumbers.length; ++i) {
			String result = GitInspectorConverter.downscalePossibleLargeNumericValue(negativeNumbers[i]);
			assertEquals("0", result);
		}
	}

	@Test
	public void testWithNonNumbers() {
		for (int i = 0; i < negativeNumbers.length; ++i) {
			try {
				GitInspectorConverter.downscalePossibleLargeNumericValue(nonNumbers[i]);
				fail(nonNumbers[i]);
			} catch (Exception e) {}
		}
	}
}
