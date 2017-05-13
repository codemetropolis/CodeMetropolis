package codemetropolis.toolchain.converter.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.converter.pmd.PmdConverter;

public class wrongSourceTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try{
		String filePath = new File("").getAbsolutePath();
		PmdConverter c=new PmdConverter(null);
		Document doc=c.loadXml(filePath+"//pm.xml");
		boolean good=true;
		Node n=doc;
		CdfElement root=c.createElementsRecursively(doc);
		Assert.assertTrue(false);
		}
		catch (NullPointerException e){
			Assert.assertTrue(true);
		}
		 
	}

}
