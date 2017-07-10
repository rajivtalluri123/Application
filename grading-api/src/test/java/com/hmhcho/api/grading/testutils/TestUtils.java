package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.mapper.DelegatingEntityMapperImpl;
import com.hmhco.api.grading.mapper.EntityMapper;
import com.hmhco.api.grading.mapper.EntityMapperConfiguration;
import org.apache.commons.lang.RandomStringUtils;
import org.hamcrest.Matcher;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by srikanthk on 4/28/17.
 */
public class TestUtils {

    public static EntityMapper configureEntityMapper() {
        DelegatingEntityMapperImpl entityMapper = new DelegatingEntityMapperImpl();
        ReflectionTestUtils.setField(entityMapper, "genericEntityMapper", new EntityMapperConfiguration().genericEntityMapper());
        return entityMapper;
    }


    public static UUID anyUUID() {
        return any(UUID.class);
    }


    public static String randomRefId() {
        return UUID.randomUUID().toString();
    }

    public static String randomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public static void checkFieldsNotPresent(ResultActions result, String pathPrefix, List<String> fields) throws Exception {
        for (String field : fields) {
            result.andExpect(jsonPath(pathPrefix + "." + field).doesNotExist());
        }
    }

    public static void checkFieldsNotPresent(ResultActions result, String pathPrefix, String... fields) throws Exception {
        checkFieldsNotPresent(result, pathPrefix, Arrays.asList(fields));
    }

    public static void checkFieldsExist(ResultActions result, String pathPrefix, List<String> fields) throws Exception {
        for (String field : fields) {
            result.andExpect(jsonPath(pathPrefix + "." + field).exists());
        }
    }

    public static void checkFieldsExist(ResultActions result, String pathPrefix, String... fields) throws Exception {
        checkFieldsExist(result, pathPrefix, Arrays.asList(fields));
    }

    public static void assertEqual(ResultActions result, String path, Matcher match) throws Exception{
        result.andExpect(jsonPath(path, match));
    }

}
