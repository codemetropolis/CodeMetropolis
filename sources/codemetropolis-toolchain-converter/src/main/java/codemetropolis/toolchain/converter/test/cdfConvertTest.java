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

public class cdfConvertTest {

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
		CdfElement elem=c.cdfConvert(n);
		
		if(doc.getNodeName()=="#document"&&!elem.getName().equals("#document")) good=false;
		if(doc.getNodeName()=="pmd"&&!elem.getName().equals("pmd")) good=false;
		if(doc.getNodeName()=="violation"&&!elem.getType().equals("violation")) good=false;
		Assert.assertTrue(good);
		
	}

	

}
