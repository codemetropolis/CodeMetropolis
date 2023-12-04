package codemetropolis.toolchain.converter.pmd;

import java.io.File;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import codemetropolis.toolchain.commons.cdf.CdfElement;

public class addPropertiesTest {

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
		CdfElement elem=new CdfElement();
		c.addProperties(elem, n);
		if(!n.hasAttributes()){
			if(!elem.getProperties().isEmpty())
				good=false;
		}
		else
		{
		int n1=elem.getProperties().size();
		int n2=n.getAttributes().getLength();	
		if(n1!=n2) good=false;
		}
		Assert.assertTrue(good);
		
		
	}

}
