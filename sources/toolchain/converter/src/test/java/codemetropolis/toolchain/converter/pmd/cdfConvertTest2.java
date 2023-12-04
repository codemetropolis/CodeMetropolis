package codemetropolis.toolchain.converter.pmd;

import java.io.File;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import codemetropolis.toolchain.commons.cdf.CdfElement;

public class cdfConvertTest2 {

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
		NodeList nl=n.getChildNodes();
		for(int i=0;i<nl.getLength();i++){
			
			CdfElement elem=c.cdfConvert(nl.item(i));
			if(nl.item(i).getNodeName()=="#document"&&!elem.getName().equals("#document")) good=false;
			if(nl.item(i).getNodeName()=="pmd"&&!elem.getName().equals("pmd")) good=false;
			if(nl.item(i).getNodeName()=="violation"&&!elem.getType().equals("violation")) good=false;
		}
		
		Assert.assertTrue(good);
	}

}
