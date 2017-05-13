package codemetropolis.toolchain.converter.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import codemetropolis.toolchain.converter.pmd.PmdConverter;

public class loadXmlTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		String filePath = new File("").getAbsolutePath();
		PmdConverter c=new PmdConverter(null);
		Document doc=c.loadXml(filePath+"//pom.xml");
		Assert.assertTrue(doc!=null);
			
		
		
	}

}
