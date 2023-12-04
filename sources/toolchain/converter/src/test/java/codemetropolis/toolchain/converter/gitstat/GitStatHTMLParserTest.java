package codemetropolis.toolchain.converter.gitstat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
import java.util.List;
import codemetropolis.toolchain.commons.cdf.CdfProperty;

class GitStatHTMLParserTest {

	
	void testGetPropertiesFromHTMLFiles() {
		List<CdfProperty> props;
		
		CdfProperty first = new CdfProperty("First", "egy",	CdfProperty.Type.STRING);
		CdfProperty second = new CdfProperty("Second", "Masodik2 masodik2", CdfProperty.Type.STRING);
		CdfProperty third = new CdfProperty("t h i r d", "Harmadik?? 2.3",	CdfProperty.Type.STRING);
		CdfProperty fourth = new CdfProperty("fourth", "", CdfProperty.Type.STRING);

		List<CdfProperty> guardProperties = new ArrayList<CdfProperty>();

		guardProperties.add(first);
		guardProperties.add(second);
		guardProperties.add(third);
		guardProperties.add(fourth);

		props = GitStatHTMLParser.getPropertiesFromHTMLFiles(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource\\TestDatParser");

		assertNotEquals(null, props);
		for (CdfProperty prop : props) {
			assertEquals(prop.getName(), guardProperties.get(props.indexOf(prop)).getName());
			assertEquals(prop.getValue(), guardProperties.get(props.indexOf(prop)).getValue());
		}
	}

	
	void testGetLinesFromHTMLFiles() {		
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		expectedResult.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		expectedResult.add("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		expectedResult.add("<body>");
		expectedResult.add("		<h1>GitStats - RF2</h1>");
		expectedResult.add("		<div class=\"nav\">");
		expectedResult.add("			<ul>");
		expectedResult.add("				<li><a href=\"index.html\">General</a></li>");
		expectedResult.add("				<li><a href=\"activity.html\">Activity</a></li>");
		expectedResult.add("				<li><a href=\"authors.html\">Authors</a></li>");
		expectedResult.add("				<li><a href=\"files.html\">Files</a></li>");
		expectedResult.add("				<li><a href=\"lines.html\">Lines</a></li>");
		expectedResult.add("				<li><a href=\"tags.html\">Tags</a></li>");
		expectedResult.add("			</ul>");
		expectedResult.add("		</div>");
		expectedResult.add("		<dl>");
		expectedResult.add("			<dt>First</dt>");
		expectedResult.add("			<dd>egy</dd>");
		expectedResult.add("			<dt>Second</dt>");
		expectedResult.add("			<dd>Masodik2 masodik2</dd>");
		expectedResult.add("			<dt>t h i r d</dt>");
		expectedResult.add("			<dd>Harmadik?? 2.3</dd>");
		expectedResult.add("			<dt>fourth</dt>");
		expectedResult.add("			<dd></dd>");
		expectedResult.add("			</dl></body>");
		expectedResult.add("</html> ");
		
		List<String> result = GitStatHTMLParser.getLinesFromHTMLFiles(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource\\TestDatParser");
		
				
		assertNotEquals(null, result);
		assertEquals(result, expectedResult);
	}


	void testIsNumber() {
		assertEquals(true, GitStatHTMLParser.isNumber("232674627"));
		assertEquals(true, GitStatHTMLParser.isNumber("0"));
		assertEquals(true, GitStatHTMLParser.isNumber("-25646416"));
		assertEquals(false, GitStatHTMLParser.isNumber("543apple32. Birne782!"));
		assertEquals(false, GitStatHTMLParser.isNumber("55546,545"));
		assertEquals(false, GitStatHTMLParser.isNumber("45.46546"));
	}

}