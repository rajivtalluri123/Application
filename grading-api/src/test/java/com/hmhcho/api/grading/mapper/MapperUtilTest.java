package com.hmhcho.api.grading.mapper;

import com.hmhco.api.grading.controller.utils.MapperUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tallurir on 7/13/17.
 */
public class MapperUtilTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private MapperUtil mapperUtil = new MapperUtil();

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void mapToObjectsForNullTest(){
        Object mappedObject = mapperUtil.transformToObject(null);
        Assert.assertNotNull(mappedObject);
        Assert.assertEquals("",mappedObject);
    }

    @Test
    public void mapToObjectsForEmptyTest(){
        Object mappedObject = mapperUtil.transformToObject("");
        Assert.assertNotNull(mappedObject);
        Assert.assertEquals("",mappedObject);
    }

    @Test
    public void mapToObjectsForStringTest(){
        Object mappedObject = mapperUtil.transformToObject("String value");
        Assert.assertNotNull(mappedObject);
        Assert.assertEquals("String value",mappedObject);
    }

    @Test
    public void mapToObjectsForJsonTest(){
        Object mappedObject = mapperUtil.transformToObject("{\"key1\":\"value1\"}");
        Assert.assertNotNull(mappedObject);
        Assert.assertTrue(mappedObject instanceof Map);
        Map<String, String> responseMap = (Map<String, String>) mappedObject;
        Assert.assertEquals("value1",responseMap.get("key1"));
    }

    @Test
    public void mapToObjetsForJsonListTest(){
        Object mappedObject = mapperUtil.transformToObject("[{\"key2\":\"value2\"}, {\"key3\":\"value3\"}]");
        Assert.assertNotNull(mappedObject);
        Assert.assertTrue(mappedObject instanceof List);
        List<Map<String, String>> responseList = (List<Map<String, String>>) mappedObject;
        Assert.assertTrue(responseList.get(0) instanceof Map);
        Assert.assertEquals("value2",responseList.get(0).get("key2"));
        Assert.assertEquals("value3", responseList.get(1).get("key3"));

    }

    @Test
    public void mapToStringForNullTest(){
        String mappedString = mapperUtil.transformToString(null);
        Assert.assertNotNull(mappedString);
        Assert.assertEquals("",mappedString);
    }

    @Test
    public void mapToStringForEmptyTest(){
        String mappedString = mapperUtil.transformToString("");
        Assert.assertNotNull( mappedString);
        Assert.assertEquals("", mappedString);
    }

    @Test
    public void mapToStringForStringTest(){
        String mappedString = mapperUtil.transformToString("String_Value");
        Assert.assertNotNull(mappedString);
        Assert.assertEquals("String_Value", mappedString);
    }

    @Test
    public void mapToStringForJsonTest(){
       Map<String,String> responseMap = new HashMap<>();
       responseMap.put("key","value");
       String mappedString = mapperUtil.transformToString(responseMap);
       Assert.assertNotNull(mappedString);
       Assert.assertTrue(mappedString.contains("key"));

    }

    @Test
    public void mapToStringForJsonListTest(){
        List<Map<String, String>> jsonList = new ArrayList<>();
        Map<String, String> responseMap1 = new HashMap<>();
        Map<String, String> responseMap2 = new HashMap<>();
        responseMap1.put("key1","value1");
        responseMap2.put("key2","value2");
        jsonList.add(responseMap1);
        jsonList.add(responseMap2);
        String mappedString = mapperUtil.transformToString(jsonList);
        Assert.assertTrue(mappedString.contains("key1"));
        Assert.assertTrue(mappedString.contains("key2"));


    }

}
