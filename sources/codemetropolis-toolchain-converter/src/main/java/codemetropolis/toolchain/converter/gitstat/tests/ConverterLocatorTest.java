package codemetropolis.toolchain.converter.gitstat.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.converter.control.ConverterLoader;
import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolchain.converter.gitstat.GitStatConverter;
import codemetropolis.toolchain.converter.sonarqube.SonarQubeConverter;
import codemetropolis.toolchain.converter.sourcemeter.GraphConverter;

public class ConverterLocatorTest {
	
	@Test
	public void sourcemeterTest() {
		CdfConverter converter = ConverterLoader.load(ConverterType.SOURCEMETER, new HashMap<String, String>());
		assertEquals(GraphConverter.class, converter.getClass());
	}
	
	@Test
	public void sonarQubeTest() {
		CdfConverter converter = ConverterLoader.load(ConverterType.SONARQUBE, new HashMap<String, String>());
		assertEquals(SonarQubeConverter.class, converter.getClass());
	}
	
	@Test
	public void gitStatTest() {
		CdfConverter converter = ConverterLoader.load(ConverterType.GITSTATS, new HashMap<String, String>());
		assertEquals(GitStatConverter.class, converter.getClass());
	}
}
