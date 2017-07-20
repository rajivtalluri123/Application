package com.hmhco.api.grading.service;

import com.hmhco.api.grading.service.action.SaveResponseRequest;
import com.hmhco.api.grading.service.action.SaveStudentSessionRequest;
import com.hmhco.api.grading.service.action.SaveStudentSessionResponse;
import com.hmhco.api.grading.views.ActivityView;
import com.hmhco.api.grading.views.SessionStatusView;
import com.hmhco.api.grading.views.getresponse.ScoringCompleteResponse;
import com.hmhco.api.grading.views.getresponse.StudentItemGetView;

import com.hmhco.api.grading.views.request.ResponseView;
import com.hmhco.api.grading.views.request.StudentStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface StudentSessionService {

 SaveStudentSessionResponse getOrCreateStudentSession(SaveStudentSessionRequest request);

 ResponseView createOrUpdateItemReponse(SaveResponseRequest saveResponseRequest);

 StudentItemGetView getStudentItems(UUID sessionID, String itemReference);

 SessionStatusView updateSessionStatus(UUID sessionId, StudentStatusRequest studentStatusRequest);

 ScoringCompleteResponse scoringCompleted(UUID sessionId);

 ActivityView getActivityDetails(UUID activityRefId);

 void pushScoresToScoring(UUID sessionId);

 void pushStatusToAssignment(UUID sessionId);

 Page<StudentItemGetView> getStudentActivityItems(Pageable pageable, UUID sessionId, boolean onlyManualScored, boolean includeQuestionsAndScores);
 
 void setSessionLevelScores(ScoringCompleteResponse scoringCompleteResponse);

}
