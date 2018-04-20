package codemetropolis.toolchain.mapping.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;
import codemetropolis.toolchain.mapping.model.Limit;
import codemetropolis.toolchain.mapping.model.Mapping;

public class MappingModelTests {

	@Test
	public void testAdd() {
		
		Limit limit = new Limit();
		int valueSetSizeExpected = limit.getValueSetSize()+2;
		limit.add(10);
		
		assertNotEquals(limit.getValueSetSize(), valueSetSizeExpected);
	}
	
	@Test
	public void testAdd2() {
		
		Limit limit = new Limit();
		int valueSetSizeExpected = limit.getValueSetSize()+1;
		limit.add(10);
		
		assertEquals(limit.getValueSetSize(), valueSetSizeExpected);
	}
	
	@Test
	public void testReadFromXMLShouldThrowException() {
		
		boolean isThrown = false;
		
		Mapping mapping = new Mapping();
		try {
			mapping.readFromXML("text.xml");
		} catch (MappingReaderException e) {
			isThrown = true;
		} catch (FileNotFoundException e) {
			isThrown = true;
		}
		
		assertTrue(isThrown);
	}
}
