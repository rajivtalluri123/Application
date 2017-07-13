package com.hmhco.api.grading.controller.utils;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hmhco.api.grading.mapper.DelegatingEntityMapperImpl;

/**
 * Created by tallurir on 7/10/17.
 */
@Component
public class MapperUtil {

	private static final Logger logger = LoggerFactory.getLogger(MapperUtil.class);

	public Object transformToObject(String value) {
		Object obj = "";
		if (value == null) {
			return "";
		}
		String val = value.trim();
		if (val.equals("")) {
			return "";
		}

		try {
			obj = new ObjectMapper().readValue(val, Object.class);
		} catch (Exception e) {
			logger.error("The Json string is a not a Json. Its a String. Returning the same");
			obj = val;
		}

		return obj;
	}

	public String transformToString(Object value) {

		if (value == null || value.equals("")) {
			return "";
		}
		
		if (value instanceof String) {
			return (String)value;
		}

		String valueStr = null;
		try {
			valueStr = new ObjectMapper().writeValueAsString(value);
		} catch (IOException e) {
			logger.error("The given Object is of neither Json nor String Type. Unable to parse, returing Empty string");
			valueStr = "";
		}
		return valueStr;
	}
}
