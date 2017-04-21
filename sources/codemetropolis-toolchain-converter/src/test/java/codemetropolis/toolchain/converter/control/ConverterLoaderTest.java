package codemetropolis.toolchain.converter.control;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

import codemetropolis.toolchain.converter.sonarqube.SonarQubeConverter;
import codemetropolis.toolchain.converter.sourcemeter.GraphConverter;

/**
 * Test class for {@link ConvertLoader} for properly handling {@link ConverterType}.
 * 
 * @author Abigel Mester {@literal <MEAWABT.SZE>}
 */
public class ConverterLoaderTest {
	
	/**
	 * Checks if {@link GraphConverter} was created properly.
	 */
	@Test
	public void testLoadGraphConverter() {
		Map<String, String> params = new HashMap<String, String>();
		Assert.assertEquals(
				ConverterLoader.load(ConverterType.SOURCEMETER, params).getClass(), GraphConverter.class);
	}
	
	/**
	 * Checks if {@link SonarQubeConverter} was created properly.
	 */
	@Test
	public void testLoadSonarQubeConverter() {
		Map<String, String> params = new HashMap<String, String>();
		Assert.assertEquals(
				ConverterLoader.load(ConverterType.SONARQUBE, params).getClass(), SonarQubeConverter.class);
	}

}
