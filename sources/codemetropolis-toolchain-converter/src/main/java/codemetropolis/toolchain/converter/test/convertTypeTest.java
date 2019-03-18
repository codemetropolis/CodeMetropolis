package codemetropolis.toolchain.converter.test;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Test;

import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.converter.pmd.PmdConverter;
import junit.framework.Assert;


public class convertTypeTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String str="asd";
		String num="2";
		PmdConverter c=new PmdConverter(null);
		
		CdfProperty.Type p=c.convertType(str);
		CdfProperty.Type r=c.convertType(num);
		
		Assert.assertTrue(p==CdfProperty.Type.STRING);
		
		Assert.assertTrue(r==CdfProperty.Type.INT);
		
			
			
		}
	
		
	}


