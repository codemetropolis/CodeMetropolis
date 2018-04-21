package codemetropolis.toolchain.mapping.conversions;

import java.util.List;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import codemetropolis.toolchain.mapping.conversions.Conversion;
import codemetropolis.toolchain.mapping.conversions.SwitchConversion;
import codemetropolis.toolchain.mapping.model.Limit;
import codemetropolis.toolchain.mapping.model.Parameter;

public class TestSwitchConversion {

    public static Conversion conversion;

    @BeforeClass
    public static void setUpBeforeClass() {

        conversion = new SwitchConversion();

        conversion.addParameters(new Parameter("default", "1.5"), new Parameter("name2", "2"), new Parameter("name3", "3.4"));
    }

    @Test
    public void testGetParametersValue() {
        boolean test = false;
        List<Parameter> parameterList = conversion.getParameters();

        for (Parameter p : parameterList) {
            if ("1.5".equals(p.getValue())) {
                test = true;
            }
        }

        assertTrue(test);

    }

    @Test
    public void testClearParametersContains() {
        conversion.clearParameters();

        Parameter param = new Parameter("TEST", "123");

        conversion.addParameter(param);

        assertTrue(conversion.getParameters().contains(param));
    }

    @Test
    public void testApply() {
        String val = (String)conversion.apply("0.2", new Limit());

        assertTrue("1.5".equals(val));
    }
}
