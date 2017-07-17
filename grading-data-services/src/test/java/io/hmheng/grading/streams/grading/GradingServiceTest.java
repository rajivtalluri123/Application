package io.hmheng.grading.streams.grading;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


public class GradingServiceTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	private GradingServiceImpl mapperUtil = new GradingServiceImpl();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void retrieveValueFromMapKeyExistTest() {
		Map<String, Object> responsesMap = new HashMap<>();
		Object valueObject = new Object();
		responsesMap.put("Value", valueObject);
		Object mappedObject = mapperUtil.retrieveValueFromMap(responsesMap, "Value");
		Assert.assertNotNull(mappedObject);
		Assert.assertEquals(valueObject, mappedObject);
	}
	
	@Test
	public void retrieveValueFromMapKeyNotExistTest() {
		Map<String, String> responsesMap = new HashMap<>();
		Object mappedObject = mapperUtil.retrieveValueFromMap(responsesMap, "Value");
		Assert.assertNull(mappedObject);
	}

	@Test
	public void retrieveValueFromMapKeyNullTest() {
		Map<String, String> responsesMap = new HashMap<>();
		responsesMap.put("Value", "VALUE_TEXT");
		Object mappedObject = mapperUtil.retrieveValueFromMap(responsesMap, null);
		Assert.assertNull(mappedObject);
	}
	
	@Test
	public void retrieveValueFromMapObjectNullTest() {
		Object mappedObject = mapperUtil.retrieveValueFromMap(null, "value");
		Assert.assertNull(mappedObject);
	}
	
	@Test
	public void retrieveValueFromMapObjectNotMapTest() {
		Object mappedObject = mapperUtil.retrieveValueFromMap(new Object(), "value");
		Assert.assertNull(mappedObject);
	}
	
	@Test
	public void retrieveValueFromMapBothNullTest() {
		Object mappedObject = mapperUtil.retrieveValueFromMap(null, null);
		Assert.assertNull(mappedObject);
	}

	
}
