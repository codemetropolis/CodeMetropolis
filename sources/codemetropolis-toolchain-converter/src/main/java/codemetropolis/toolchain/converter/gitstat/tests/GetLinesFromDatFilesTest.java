package codemetropolis.toolchain.converter.gitstat.tests;

import static org.junit.Assert.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import codemetropolis.toolchain.converter.gitstat.GitStatDatParser;

public class GetLinesFromDatFilesTest {

	@Test
	public void testGetLinesFromDatFiles() throws IOException {
		
		
		String elso = ".\\src\\main\\java\\codemetropolis\\toolchain\\converter\\gitstat\\tests\\test_resource\\TestDatParser\\a.dat_elso 34";
		String masodik = ".\\src\\main\\java\\codemetropolis\\toolchain\\converter\\gitstat\\tests\\test_resource\\TestDatParser\\a.dat_masodik 56";
		String harmadik = ".\\src\\main\\java\\codemetropolis\\toolchain\\converter\\gitstat\\tests\\test_resource\\TestDatParser\\b.dat_harmadik 65";
		String negyedik = ".\\src\\main\\java\\codemetropolis\\toolchain\\converter\\gitstat\\tests\\test_resource\\TestDatParser\\b.dat_negyedik 18";
		List<String> result = new ArrayList<String>();
		result.add(elso);
		result.add(masodik);
		result.add(harmadik);
		result.add(negyedik);
		
		List<String> method = GitStatDatParser.getLinesFromDatFiles(".\\src\\main\\java\\codemetropolis\\toolchain\\converter\\gitstat\\tests\\test_resource\\TestDatParser");
		for (String one : method)
			System.out.println(one);
				
		assertNotEquals(null, method);
		assertEquals(method, result);
				
	}

}
