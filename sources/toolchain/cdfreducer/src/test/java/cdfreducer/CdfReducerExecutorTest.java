package cdfreducer;

import codemetropolis.toolchain.cdfreducer.CdfReducerExecutor;
import codemetropolis.toolchain.cdfreducer.CdfReducerExecutorArgs;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class CdfReducerExecutorTest {

    private CdfReducerExecutor cdfReducerExecutor = new CdfReducerExecutor();

    @Test
    public void testExecute_succesful() throws IOException {
        /* GIVEN */
        CdfReducerExecutorArgs executorArgs = new CdfReducerExecutorArgs(
                "src/test/resources/converterToMapping.xml",
                "src/test/resources/results/executor_results/xml_teszt.xml",
                new HashMap<String, String>()
                {{
                    put("property-name-regex", "source_id");
                    put("property-value-regex","0");
                }});

        /* WHEN */
        Boolean success = cdfReducerExecutor.execute(executorArgs);

        /* THEN */
        assertTrue(success);
        Path path = Paths.get("src/test/resources/1-xml_teszt.xml");
        String original_file = Files.readAllLines(path).get(0);

        Path path2 = Paths.get("src/test/resources/results/executor_results/xml_teszt.xml");
        String not_original_file = Files.readAllLines(path2).get(0);

        assertEquals(original_file, not_original_file);

        System.out.println("Execute test was a success!");
    }

    @Test
    public void testExecute_no_input_file() throws IOException {
        /* GIVEN */
        CdfReducerExecutorArgs executorArgs = new CdfReducerExecutorArgs(
                "asdf.xml",
                "src/test/resources/results/executor_results/xml_teszt.xml",
                new HashMap<String, String>()
                {{
                    put("property-name-regex", "source_id");
                    put("property-value-regex","0");
                }});

        /* WHEN */
        Boolean success = cdfReducerExecutor.execute(executorArgs);

        /* THEN */
        assertFalse(success);
        System.out.println("Execute test without input file was a success!");
    }

    @Test
    public void testExecute_incorrect_xml() throws IOException {
        /* GIVEN */
        CdfReducerExecutorArgs executorArgs = new CdfReducerExecutorArgs(
                "src/test/resources/incorrect-xml.xml",
                "src/test/resources/results/executor_results/incorrect-xml_teszt.xml",
                new HashMap<String, String>()
                {{
                    put("property-name-regex", "source_id");
                    put("property-value-regex","0");
                }});

        /* WHEN */
        Boolean success = cdfReducerExecutor.execute(executorArgs);

        /* THEN */
        assertFalse(success);
        System.out.println("Execute test with incorrect xml file was a success!");
    }


}
