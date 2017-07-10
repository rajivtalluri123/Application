package com.hmhcho.api.grading.controller;

import com.hmhcho.api.grading.testutils.AuthenticateAs;
import com.hmhcho.api.grading.testutils.StudentSessionViewBuilder;
import com.hmhco.api.grading.service.StudentSessionService;
import com.hmhco.api.grading.service.action.SaveStudentSessionRequest;
import com.hmhco.api.grading.service.action.SaveStudentSessionResponse;
import com.hmhco.api.grading.views.StudentActivitySessionView;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;

import java.util.UUID;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by srikanthk on 4/28/17.
 */
public class ActivityControllerTest  extends RestControllerTest{


    private MockHttpSession session = new MockHttpSession();


    @Autowired
    private StudentSessionService studentSessionService;
    @Test
    @AuthenticateAs(AuthenticateAs.AuthType.TRUSTED_API)
    public void testStudentSession() throws Exception{

        UUID sessionId = UUID.randomUUID();
        UUID activityID = UUID.randomUUID();

        StudentSessionViewBuilder studentSessionView = new StudentSessionViewBuilder();

        StudentActivitySessionView studentActivitySessionView = studentSessionView.withAll(activityID).build().getStudentActivitySessionView();
        when(studentSessionService.getOrCreateStudentSession(any(SaveStudentSessionRequest.class)))
                .thenReturn(new SaveStudentSessionResponse(studentActivitySessionView));


        mockMvc.perform(post("/v1/activities/{sessionId}/init",sessionId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(studentActivitySessionView)))
                .andDo(print())
                .andExpect(status().isCreated()
                );


    }

}
