package codemetropolis.toolchain.converter.pmd;

import java.io.File;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import codemetropolis.toolchain.commons.cdf.CdfElement;

public class createElementsRecursivelyTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String filePath = new File("").getAbsolutePath();
		PmdConverter c=new PmdConverter(null);
		Document doc=c.loadXml(filePath+"//pom.xml");
		boolean good=true;
		Node n=doc;
		CdfElement root=c.createElementsRecursively(doc);
		 Assert.assertTrue(root!=null);
		
		
		
		Assert.assertTrue(good);
		
	}

}
