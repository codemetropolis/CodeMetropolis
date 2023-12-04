package codemetropolis.toolchain.converter.gitstat;

import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import codemetropolis.toolchain.commons.cdf.CdfProperty;

public class Test_PropertyCollector_collect {
	
	@Test
	public void collectValid() {
		List<CdfProperty> list = PropertyCollector.collect(".\\src\\test\\java\\codemetropolis\\toolchain\\converter\\gitstat\\test_resource");
		assertNotNull(list);
	}
	/*
	@Test
	public void collectInvalid() {
		List<CdfProperty> list = PropertyCollector.collect("12345__2132124*");
		assertNotNull(list);
	}
	*/
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