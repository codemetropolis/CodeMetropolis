package codemetropolis.toolchain.converter.gitinspector.test;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class TestHelper {
    private static String XML_SRC_PATH = "src\\main\\java\\codemetropolis\\toolchain\\converter\\gitinspector\\test\\GitInspectorOutput.xml";

	public static boolean equals(CdfProperty p1, CdfProperty p2) {
		return p1.getName().equals(p2.getName()) &&
			p1.getValue().equals(p2.getValue()) &&
			p1.getType().equals(p2.getType());
	}

	public static boolean equals(CdfElement e1, CdfElement e2) {
		boolean equals = e1.getName().equals(e2.getName()) &&
						e1.getType().equals(e2.getType());

		List<CdfProperty> p1 = e1.getProperties();
		List<CdfProperty> p2 = e2.getProperties();
		for (int i = 0; i < Math.min(p1.size(), p2.size()); ++i) {
			equals = equals && equals(p1.get(i), p2.get(i));
		}

		List<CdfElement> elements1 = e1.getChildElements();
		List<CdfElement> elements2 = e2.getChildElements();
		for (int i = 0; i < Math.min(elements1.size(), elements2.size()); ++i) {
			equals = equals && equals(elements1.get(i), elements2.get(i));
		}
		return equals;
	}

	public static GitInspectorConverter newGitInspectorConverter() {
	       Map<String, String> params = null;
	       return new GitInspectorConverter(params);
	}

	public static Document newDocument() throws Exception {
	    GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        Document doc = conv.createDocumentFromSource(XML_SRC_PATH);
        return doc;
	}
}
