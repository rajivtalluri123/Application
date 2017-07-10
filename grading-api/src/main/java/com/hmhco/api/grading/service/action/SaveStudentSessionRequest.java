package com.hmhco.api.grading.service.action;

import com.hmhco.api.grading.utils.StudentSessionRequestType;
import com.hmhco.api.grading.views.StudentActivitySessionView;

import com.hmhco.api.grading.views.StudentSessionView;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by srikanthk on 4/26/17.
 */

@Data
@NoArgsConstructor
public class SaveStudentSessionRequest {


    private UUID sessionId;

    private StudentActivitySessionView studentActivitySessionView;

    private StudentSessionView studentSessionView;

    private StudentSessionRequestType requestType;

    private Boolean isNewSessionEnity;

    public SaveStudentSessionRequest(UUID sessionId, StudentActivitySessionView studentActivitySessionView){
        this.sessionId = sessionId;
        this.studentActivitySessionView = studentActivitySessionView;
    }

    public SaveStudentSessionRequest(UUID sessionId, StudentSessionView studentSessionView){
        this.sessionId = sessionId;
        this.studentSessionView = studentSessionView;
    }

}
