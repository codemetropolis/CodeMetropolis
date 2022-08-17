package codemetropolis.toolchain.gui.utils;

import static org.junit.jupiter.api.Assertions.*;

import codemetropolis.toolchain.gui.CodeMetropolisGUI;
import codemetropolis.toolchain.gui.GUIController;
import org.junit.jupiter.api.Test;

public class CodeMetropolisGUITest {

    CodeMetropolisGUI codeMetropolisGUI = new CodeMetropolisGUI();
    @Test
    public void testGetNewestVersion(){
        assertEquals("1.4",codeMetropolisGUI.getNewestVersion("https://github.com/codemetropolis/CodeMetropolis/releases/latest"));
        assertEquals("1.4",codeMetropolisGUI.getNewestVersion("https://github.com/codemetropolis/CodeMetropolis/releases/tag/1.4"));
        assertEquals("1.3",codeMetropolisGUI.getNewestVersion("https://github.com/codemetropolis/CodeMetropolis/releases/tag/1.3"));
        assertEquals("1.3.2",codeMetropolisGUI.getNewestVersion("https://github.com/codemetropolis/CodeMetropolis/releases/tag/1.3.2-alpha"));
        assertEquals("1.3.1",codeMetropolisGUI.getNewestVersion("https://github.com/codemetropolis/CodeMetropolis/releases/tag/1.3.1-alpha"));
    }

    @Test
    public void testCheckDot(){
        assertEquals("1.4.0",codeMetropolisGUI.checkDot("1.4"));
        assertEquals("1.4.0",codeMetropolisGUI.checkDot("1.4.0"));
        assertEquals("1.4.",codeMetropolisGUI.checkDot("1.4."));
        assertEquals("14.0",codeMetropolisGUI.checkDot("14"));
        assertEquals(".14.0",codeMetropolisGUI.checkDot(".14"));
        assertEquals("..0",codeMetropolisGUI.checkDot("."));
        assertEquals("..",codeMetropolisGUI.checkDot(".."));
        assertEquals(".0",codeMetropolisGUI.checkDot(""));
    }

    @Test
    public void testValidator(){
        assertTrue(codeMetropolisGUI.validator("1.4.2"));
        assertTrue(codeMetropolisGUI.validator("12222.1251"));
        assertTrue(codeMetropolisGUI.validator("1"));
        assertFalse(codeMetropolisGUI.validator(""));
        assertFalse(codeMetropolisGUI.validator("1."));
        assertFalse(codeMetropolisGUI.validator(".1"));
        assertFalse(codeMetropolisGUI.validator("."));
        assertFalse(codeMetropolisGUI.validator("1.34.d3"));
        assertFalse(codeMetropolisGUI.validator("xxx"));
        assertFalse(codeMetropolisGUI.validator("1.4.5.0"));
    }

    @Test
    public void testCompareVersion(){
        assertTrue(codeMetropolisGUI.compareVersion("1", "2"));
        assertTrue(codeMetropolisGUI.compareVersion("1.0", "1.1"));
        assertTrue(codeMetropolisGUI.compareVersion("1.0.0", "1.0.1"));
        assertTrue(codeMetropolisGUI.compareVersion("0.0.2", "0.0.4"));
        assertFalse(codeMetropolisGUI.compareVersion("1.4.2", "1.4.2"));
        assertFalse(codeMetropolisGUI.compareVersion("1.4.2", "0.0.0"));
        assertFalse(codeMetropolisGUI.compareVersion("1", "0"));
        assertFalse(codeMetropolisGUI.compareVersion("1.1", "1.0"));
        assertFalse(codeMetropolisGUI.compareVersion("1.1.4", "1.1.3"));
        assertFalse(codeMetropolisGUI.compareVersion("1.", "1."));
        assertFalse(codeMetropolisGUI.compareVersion("1", ""));
        assertFalse(codeMetropolisGUI.compareVersion(".1", ".1."));
        assertFalse(codeMetropolisGUI.compareVersion("XXX", "1A.B"));
        assertFalse(codeMetropolisGUI.compareVersion("", ""));
    }
}
