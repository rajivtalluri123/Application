package com.hmhco.api.grading.controller.utils;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by tallurir on 7/10/17.
 */
public class MapperUtil {

    public static Object transformToObject(String value){
        Object obj = "";
        if(value==null){
            return "";
        }
        String val = value.trim();
        if(val.equals("")){
            return "";
        }

        try {
            obj = new ObjectMapper().readValue(val, Object.class);
        } catch (Exception e) {
            obj = val;
        }

        return obj;
    }
	
	public static String transformToString(Object value){
		
		if(value == null){
			return "";
		}
	
		String valueStr = null;
		try {
			valueStr = new ObjectMapper().writeValueAsString(value);
		} catch (IOException e) {
			valueStr = "";
		}
		return valueStr;
    }
}
