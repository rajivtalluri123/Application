package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.service.action.SaveStudentSessionRequest;
import com.hmhco.api.grading.views.*;

import io.hmheng.grading.utils.StudentAssignmentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by srikanthk on 4/28/17.
 */
public class StudentSessionViewBuilder {

  private UUID sessionId;

  private StudentActivitySessionView studentActivitySessionView = new StudentActivitySessionView();

  private ActivityItemScoreVIewBuilder activityitemviewScore = new ActivityItemScoreVIewBuilder();

  private StudentItemViewBuilder studentItemViewBuilder = new StudentItemViewBuilder();



  private UUID generateSessionId(){
    return UUID.randomUUID();
  }

  public SaveStudentSessionRequest build(){
    sessionId = generateSessionId();
    studentActivitySessionView.setSessionRefId(sessionId);
    SaveStudentSessionRequest saveStudentSessionRequest = new SaveStudentSessionRequest(sessionId, studentActivitySessionView);
    return saveStudentSessionRequest;

  }

public StudentSessionViewBuilder withAll(UUID activityRefID){

    studentActivitySessionView.setActivityRefId(activityRefID);
    studentActivitySessionView.setDateCompleted(LocalDateTime.now());
    studentActivitySessionView.setDateStarted(LocalDateTime.now());
    studentActivitySessionView.setMaxScore(100);
    studentActivitySessionView.setMaxTime(100);
    studentActivitySessionView.setNumAttempted(100);
    studentActivitySessionView.setNumQuestions(100);
    studentActivitySessionView.setScore(100);
    studentActivitySessionView.setStaffPersonalRefId(UUID.randomUUID());
    studentActivitySessionView.setStatus(StudentAssignmentStatus.READY_FOR_SCORING);
    studentActivitySessionView.setUserAgent("UserAgent");
    studentActivitySessionView.setActivityItems(activityitemviewScore.buildList(10));
    studentActivitySessionView.setStudentItems(studentItemViewBuilder.buildViewList(10));

    return this;

}















}
