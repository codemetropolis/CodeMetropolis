package codemetropolis.toolchain.mapping.conversions;


import java.util.List;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import codemetropolis.toolchain.mapping.conversions.Conversion;
import codemetropolis.toolchain.mapping.conversions.MultiplyConversion;
import codemetropolis.toolchain.mapping.model.Limit;
import codemetropolis.toolchain.mapping.model.Parameter;

public class MultiplyConversionTest {

    public static Conversion conversion;

    @BeforeClass
    public static void setUpBeforeClass() {

        conversion = new MultiplyConversion();

        conversion.addParameters(new Parameter("name1", "1.5"), new Parameter("name2", "2"), new Parameter("name3", "3.4"));
    }

    @Test
    public void testGetParameters() {
        boolean test = false;
        List<Parameter> parameterList = conversion.getParameters();

        for (Parameter p : parameterList) {
            if ("name1".equals(p.getName())) {
                test = true;
            }
        }

        assertTrue(test);

    }

    @Test
    public void testClearParameters() {
        conversion.clearParameters();
        assertTrue(conversion.getParameters().isEmpty());
    }


    @Test
    public void testApply() {
        double val = (double)conversion.apply("0.2", new Limit());

        assertTrue(0.2 == val);
    }
}