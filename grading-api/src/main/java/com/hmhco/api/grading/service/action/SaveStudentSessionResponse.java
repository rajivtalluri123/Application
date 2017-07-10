package com.hmhco.api.grading.service.action;

import com.hmhco.api.grading.views.StudentActivitySessionView;
import com.hmhco.api.grading.views.StudentSessionView;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by srikanthk on 4/26/17.
 */
@Data
@NoArgsConstructor
public class SaveStudentSessionResponse {

    StudentActivitySessionView studentActivitySessionView;

    StudentSessionView studentSessionView;

    public SaveStudentSessionResponse(StudentActivitySessionView studentActivitySessionView){
        this.studentActivitySessionView = studentActivitySessionView;

    }

    public SaveStudentSessionResponse(StudentSessionView studentSessionView){
        this.studentSessionView = studentSessionView;
    }
}
