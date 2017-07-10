package com.hmhcho.api.grading.controller;

import com.hmhcho.api.grading.testutils.TestUtils;
import com.hmhco.api.grading.GradingServiceApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Created by srikanthk on 4/28/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GradingServiceApplication.class)
//@TestPropertySource(locations = { "classpath:env-profiles/application-testcase.properties", "classpath:flyway/flyway-testcase.properties" })
@IntegrationTest("test.mockServices=true")
@WebAppConfiguration
public class RestControllerTest {

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;


    @Before
    public final void before(){

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }


    protected String randomRefId() { return TestUtils.randomRefId();
    }

    protected String randomString(){return TestUtils.randomString();
    }

    protected void checkFieldsNotPresent(ResultActions result, String pathPrefix, List<String> fields) throws Exception {
        TestUtils.checkFieldsNotPresent(result, pathPrefix, fields);
    }

    protected void checkFieldsNotPresent(ResultActions result, String pathPrefix, String... fields) throws Exception {
        TestUtils.checkFieldsNotPresent(result, pathPrefix, fields);
    }

    protected void checkFieldsExist(ResultActions result, String pathPrefix, List<String> fields) throws Exception {
        TestUtils.checkFieldsExist(result, pathPrefix, fields);
    }

    protected void checkFieldsExist(ResultActions result, String pathPrefix, String... fields) throws Exception {
        TestUtils.checkFieldsExist(result, pathPrefix, fields);
    }

}
