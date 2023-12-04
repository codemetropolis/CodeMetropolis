package codemetropolis.toolchain.converter.pmd;
import java.io.File;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

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
