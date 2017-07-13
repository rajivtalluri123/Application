package com.hmhcho.api.grading.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.hmhco.api.grading.controller.utils.MapperUtil;


public class MapperUtilTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	private MapperUtil mapperUtil = new MapperUtil();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void mappToObjectForNullTest() {
		Object mappedObject = mapperUtil.transformToObject(null);
		Assert.assertNotNull(mappedObject);
		Assert.assertEquals("", mappedObject);
	}
	
	@Test
	public void mappToObjectForEmptyTest() {
		Object mappedObject = mapperUtil.transformToObject("");
		Assert.assertNotNull(mappedObject);
		Assert.assertEquals("", mappedObject);
	}
	
	@Test
	public void mappToObjectForStringTest() {
		Object mappedObject = mapperUtil.transformToObject("String Value");
		Assert.assertNotNull(mappedObject);
		Assert.assertEquals("String Value", mappedObject);
	}
	
	@Test
	public void mappToObjectForJsonTest() {
		Object mappedObject = mapperUtil.transformToObject("{\"Key1\" : \"Value1\"}");
		Assert.assertNotNull(mappedObject);
		Assert.assertTrue(mappedObject instanceof Map);
		
		Map<String, String> responseMap = (Map<String, String>) mappedObject; 
		Assert.assertEquals("Value1", responseMap.get("Key1"));
	}
	
	@Test
	public void mappToObjectForJsonListTest() {
		Object mappedObject = mapperUtil.transformToObject("[{\"Key1\" : \"Value1\"}, {\"Key2\" : \"Value3\"}]");
		Assert.assertNotNull(mappedObject);
		Assert.assertTrue(mappedObject instanceof List);
		
		List<Map<String, String>> responseList = (List<Map<String, String>>) mappedObject;
		Assert.assertTrue(responseList.get(0) instanceof Map);
		Assert.assertEquals("Value1", responseList.get(0).get("Key1"));
	}
	
	@Test
	public void mappToStringForNullTest() {
		String mappedString = mapperUtil.transformToString(null);
		Assert.assertNotNull(mappedString);
		Assert.assertEquals("", mappedString);
	}

	@Test
	public void mappToStringForEmptyTest() {
		String mappedString = mapperUtil.transformToString("");
		Assert.assertNotNull(mappedString);
		Assert.assertEquals("", mappedString);
	}
	
	@Test
	public void mappToStringForStringTest() {
		String mappedString = mapperUtil.transformToString("STRING_VALUE");
		Assert.assertNotNull(mappedString);
		Assert.assertEquals("STRING_VALUE", mappedString);
	}
	
	@Test
	public void mappToStringForJsonTest() {
		
		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("Key1", "Value1");
		
		String mappedString = mapperUtil.transformToString(responseMap);
		Assert.assertNotNull(mappedString);
		
		Assert.assertTrue(mappedString.contains("Key1"));
	}

	@Test
	public void mappToStringForJsonListTest() {
		
		List<Map<String, String>> jsonList =  new ArrayList<>();
		Map<String, String> responseMap1 = new HashMap<>();
		responseMap1.put("Key1", "Value1");
		Map<String, String> responseMap2 = new HashMap<>();
		responseMap2.put("Key2", "Value2");
		jsonList.add(responseMap1);
		jsonList.add(responseMap2);
		
		String mappedString = mapperUtil.transformToString(jsonList);
		Assert.assertNotNull(mappedString);
		
		Assert.assertTrue(mappedString.contains("Key1"));
		Assert.assertTrue(mappedString.contains("Key2"));
	}


}
