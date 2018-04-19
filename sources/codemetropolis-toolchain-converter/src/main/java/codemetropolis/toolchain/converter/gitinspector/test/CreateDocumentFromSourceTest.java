package codemetropolis.toolchain.converter.gitinspector.test;

import static org.junit.Assert.*;

import org.junit.Test;

import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;

public class CreateDocumentFromSourceTest {
    private static String XML_SRC_PATH = "src\\main\\java\\codemetropolis\\toolchain\\converter\\gitinspector\\test\\GitInspectorOutput.xml";
    private static String INVALID_FORMAT_PATH = "src\\main\\java\\codemetropolis\\toolchain\\converter\\gitinspector\\test\\";

    @Test
    public void invalidPath() {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        try {
            conv.createDocumentFromSource("");
            fail("This test should fail because the resource path is invalid.");
        } catch (CodeMetropolisException e) {
        } catch (Exception e) {
            fail("the wrong type of exception returned.");
        }
    }

    @Test
    public void invalidResource() {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        try {
            conv.createDocumentFromSource(INVALID_FORMAT_PATH);
            fail("This test should fail because the target resource is a library.");
        } catch (CodeMetropolisException e) {
        } catch (Exception e) {
            fail("the wrong type of exception returned.");
        }
    }

    @Test
    public void validPath() {
        GitInspectorConverter conv = TestHelper.newGitInspectorConverter();
        try {
            conv.createDocumentFromSource(XML_SRC_PATH);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
