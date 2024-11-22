package codemetropolis.toolchain.converter;

import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestMain {

    @Test
    public void testNoArguments() {
        String[] args = {};
        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            assertFalse(options.showHelp());
            assertNull(options.getType());
            assertNull(options.getSource());
        } catch (CmdLineException | IllegalArgumentException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testWithValidArguments() {
        String[] args = {"-t", "sourcemeter", "-i", "codemetropolis-toolchain-commons.graph"};
        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            assertTrue(options.getType().equals("sourcemeter"));
            assertTrue(options.getSource().equals("codemetropolis-toolchain-commons.graph"));
        } catch (CmdLineException | IllegalArgumentException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testTypeOnly() {
        String[] args = {"-t", "sourcemeter"};
        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            assertTrue(options.getType().equals("sourcemeter"));
            assertNull(options.getSource());
        } catch (CmdLineException | IllegalArgumentException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSourceOnly() {
        String[] args = {"-i", "codemetropolis-toolchain-commons.graph"};
        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            assertNull(options.getType());
            assertTrue(options.getSource().equals("codemetropolis-toolchain-commons.graph"));
        } catch (CmdLineException | IllegalArgumentException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
}