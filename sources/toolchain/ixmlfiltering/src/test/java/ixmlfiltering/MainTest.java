package ixmlfiltering;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import java.io.*;

import codemetropolis.toolchain.ixmlfiltering.Main;
import codemetropolis.toolchain.ixmlfiltering.CommandLineOptions;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Test class for testing the Main class.
 *
 * @author Narad Márton N4P26J h879805
 */

public class MainTest {
	
	private String[] args = {};
	private Main instance = new Main();
	
	private CommandLineOptions optionsMock = mock(CommandLineOptions.class);
	private CmdLineParser parserMock = mock(CmdLineParser.class);
	
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private static final PrintStream originalOut = System.out;
	private static final PrintStream originalErr = System.err;
	
	
	@BeforeAll
	public static void setup() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	
	@Test
	public void showHelpTest() throws CmdLineException {
		try {
			doNothing().when(parserMock).parseArgument();
			when(optionsMock.showHelp()).thenReturn(true);
			when(optionsMock.getInputFile()).thenReturn("in");
			when(optionsMock.getOutputFile()).thenReturn("out");
			
			instance.main(args);
		} catch (Exception e) {
			assertEquals(CmdLineException.class, e.getClass());
		}
		
	}
	
	@Test
	public void missingInputTest() throws CmdLineException {
		try {
			doNothing().when(parserMock).parseArgument();
			when(optionsMock.showHelp()).thenReturn(false);
			when(optionsMock.getInputFile()).thenReturn(null);
			
			instance.main(args);
		} catch (Exception e) {
			assertEquals(CmdLineException.class, e.getClass());
		}
		
	}
	
	@AfterAll
	public static void restore() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

}
