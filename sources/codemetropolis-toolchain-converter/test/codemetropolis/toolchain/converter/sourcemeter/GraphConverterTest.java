package codemetropolis.toolchain.converter.sourcemeter;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphConverterTest {

    private static GraphConverter graphConverter;
    private static Map<String, String> params;

    @BeforeClass
    public static void setUp() throws Exception {
        params = new HashMap<String, String>();
        params.put("relationFile", "test/codemetropolis/toolchain/converter/relations/test.limml");

        graphConverter = new GraphConverter(params);

    }

    @Test
    public void createElements() throws Exception {
    }

    @Test
    public void createElements1() throws Exception {
    }

}