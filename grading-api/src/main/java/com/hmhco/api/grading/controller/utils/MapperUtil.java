package com.hmhco.api.grading.controller.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import com.hmhco.api.grading.mapper.DelegatingEntityMapperImpl;

import java.io.IOException;

/**
 * Created by tallurir on 7/10/17.
 */

@Component
public class MapperUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapperUtil.class);

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
            logger.error("the Json String is not a Json. It's a String. Returning the same");
            obj = val;
        }

        return obj;
    }
    public static String transformToString(Object value){
        if(value== null || value.equals("")){
            return "";
        }
        if(value instanceof String)
            return (String)value;

        String valueStr = null;
        try {
            valueStr = new ObjectMapper().writeValueAsString(value);
        } catch (IOException e) {
            logger.error("the object is nether String nor Json Type. Unable to parse, returning empty String.");
           valueStr = "";
        }
        return valueStr;
    }
}
