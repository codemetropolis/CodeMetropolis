package codemetropolis.toolchain.converter;

import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTest {

    @Test
    public void checkWithoutParam() {
        String[] args = {"java", "-jar", "converter-1.4.0.jar"};
        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            assertTrue(Main.checkWithoutParam(options));
        }
        catch (CmdLineException | IllegalArgumentException e) {
            return;
        }
    }

    @Test
    public void checkWithParam() {
        String[] args = {"java", "-jar", "converter-1.4.0.jar", "-t", "sourcemeter", "-i", "codemetropolis-toolchain-commons.graph"};
        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            assertFalse(Main.checkWithoutParam(options));
        } catch (CmdLineException | IllegalArgumentException e) {
            return;
        }
    }

    @Test
    public void checkTypeOnly() {
        String[] args = {"java", "-jar", "converter-1.4.0.jar", "-t", "sourcemeter"};
        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            assertTrue(Main.checkWithoutParam(options));
        } catch (CmdLineException | IllegalArgumentException e) {
            return;
        }
    }

    @Test
    public void checkSourceOnly() {
        String[] args = {"java", "-jar", "converter-1.4.0.jar", "-i", "codemetropolis-toolchain-commons.graph"};
        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            assertTrue(Main.checkWithoutParam(options));
        } catch (CmdLineException | IllegalArgumentException e) {
            return;
        }
    }

}
