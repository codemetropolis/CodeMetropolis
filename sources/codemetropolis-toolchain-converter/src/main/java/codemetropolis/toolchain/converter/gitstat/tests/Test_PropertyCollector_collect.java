package codemetropolis.toolchain.converter.gitstat.tests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.gitstat.PropertyCollector;

public class Test_PropertyCollector_collect {
	
	@Test
	public void collectValid() {
		List<CdfProperty> list = PropertyCollector.collect(".\\src\\main\\java\\codemetropolis\\toolchain\\converter\\gitstat\\tests\\test_resource\\");
		assertNotNull(list);
	}
	
	@Test
	public void collectInvalid() {
		List<CdfProperty> list = PropertyCollector.collect("12345__2132124*");
		assertNotNull(list);
	}
	
	@Test
	public void collectNull() {
		List<CdfProperty> list = PropertyCollector.collect(null);
		assertNotNull(list);
	}
	
	@Test
	public void collectEmpty() {
		List<CdfProperty> list = PropertyCollector.collect("");
		assertNotNull(list);
	}
	
}
