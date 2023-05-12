package codemetropolis.toolchain.converter.gitstat;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GetPropertyPreNameFromFileNameTest {

	@Test
	void testGetPropertyPreNameFromFileName1() {

		String result1 = GitStatDatParser.getPropertyPreNameFromFileName("commits_by_year");
		assertEquals("commits_by_year_", result1);
	}
	@Test
	void testGetPropertyPreNameFromFileName2() {
		String result2 = GitStatDatParser.getPropertyPreNameFromFileName("commits_by_year_month");
		System.out.println(result2);
		assertEquals("commits_by_year_month_", result2);
	}
	@Test
	void testGetPropertyPreNameFromFileName3() {
		String result3 = GitStatDatParser.getPropertyPreNameFromFileName("day_of_week");
		assertEquals("day_of_week_", result3);
	}
	@Test
	void testGetPropertyPreNameFromFileName4() {
		String result4 = GitStatDatParser.getPropertyPreNameFromFileName("domains");
		assertEquals("domains_", result4);
	}
	@Test
	void testGetPropertyPreNameFromFileName5() {
		String result5 = GitStatDatParser.getPropertyPreNameFromFileName("files_by_date");
		assertEquals("files_by_date_", result5);
	}
	@Test
	void testGetPropertyPreNameFromFileName6() {
		String result6 = GitStatDatParser.getPropertyPreNameFromFileName("hour_of_day");
		assertEquals("hour_of_day_", result6);
	}
	@Test
	void testGetPropertyPreNameFromFileName7() {
		String result7 = GitStatDatParser.getPropertyPreNameFromFileName("lines_of_code");
		assertEquals("lines_of_code_", result7);
	}
	@Test
	void testGetPropertyPreNameFromFileName8() {
		String result8 = GitStatDatParser.getPropertyPreNameFromFileName("month_of_year");
		assertEquals("month_of_year_", result8);
	}
	@Test
	void testGetPropertyPreNameFromFileName9() {
		String result9 = GitStatDatParser.getPropertyPreNameFromFileName("");
		assertEquals("_", result9);
	}

}