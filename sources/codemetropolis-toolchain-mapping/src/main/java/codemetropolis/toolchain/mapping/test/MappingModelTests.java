package codemetropolis.toolchain.mapping.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import codemetropolis.toolchain.mapping.control.LimitController;
import codemetropolis.toolchain.mapping.exceptions.MappingReaderException;
import codemetropolis.toolchain.mapping.model.Binding;
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
	
	@Test
	public void testGetVariableIdShouldReturnNull() {

		String expected = null;
		
		Binding binding = new Binding();
		binding.from = "";
		
		String result = binding.getVariableId();
		
		assertEquals(result, expected);
	}
	
	@Test
	public void tesLimitControllerAddIsCorrect() {
		
		Limit limit = new Limit();
		limit.add(10);
		
		MultiKeyMap<String, Limit> expectedLimits = new MultiKeyMap<>();
		expectedLimits.put("sourceName", "sourceFrom", limit);
		
		LimitController limitController = new LimitController();

		limitController.add("sourceName", "sourceFrom", 10);
		
		Limit resultLimit = limitController.getLimit("sourceName", "sourceFrom");
		Limit expectedLimit = expectedLimits.get("sourceName", "sourceFrom");
		
		assertTrue(EqualsBuilder.reflectionEquals(expectedLimit,resultLimit));
	}
	
	@Test
	public void tesLimitControllerAddIsCorrect2() {
		
		Limit limit = new Limit();
		limit.add(10);
		
		MultiKeyMap<String, Limit> expectedLimits = new MultiKeyMap<>();
		expectedLimits.put("sourceName", "sourceFrom", limit);
		
		LimitController limitController = new LimitController();

		limitController.add("sourceName", "sourceFrom", 10);
		
		Limit resultLimit = limitController.getLimit("sourceName", "sourceFrom");
		Limit expectedLimit = expectedLimits.get("sourceName", "sourceFrom");
		
		assertEquals(expectedLimit.getValueSetSize(), resultLimit.getValueSetSize());
	}
	
}
