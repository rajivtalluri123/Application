package com.hmhco.api.grading.service.assignment;

import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.views.assignment.StudentSessionStatus;
import io.hmheng.grading.learnosity.domain.StudentSession;
import io.hmheng.grading.streams.config.ProduceAssesmentStatusStreamConfiguration;
import io.hmheng.grading.streams.kinesis.KinesisStreamDataService;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;
import java.time.Clock;
import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nandipatim on 6/29/17.
 */
@Service
public class StudentSessionDetailServiceImpl implements StudentSessionDetailService{

  @Autowired
  private KinesisStreamDataService kinesisStreamDataService;

  @Autowired
  private ProduceAssesmentStatusStreamConfiguration produceAssesmentStatusStreamConfiguration;

  @Override
  public KinesisPutRecordResult pushStatusToAssignmentService(StudentSessionEntity studentSession , Boolean pushAssignmentStatus){

    if(studentSession == null){
      throw new EntityNotFoundException("Student Session cannot be Null to Push Student Assignment Status.");
    }

    StudentSessionStatus studentSessionStatus = createStudentSessionStatusFromStudentSession(studentSession);

    KinesisPutRecordResult putRecordResult = (pushAssignmentStatus) ? kinesisStreamDataService.pushToStream(studentSessionStatus
        , produceAssesmentStatusStreamConfiguration) : new KinesisPutRecordResult();

    return putRecordResult;
  }

  private StudentSessionStatus createStudentSessionStatusFromStudentSession(StudentSessionEntity studentSession){

    StudentSessionStatus studentSessionStatus = new StudentSessionStatus();
    studentSessionStatus.setActivityRefId(studentSession.getActivityRefId());
    studentSessionStatus.setSessionId(studentSession.getSessionRefId());
    studentSessionStatus.setUserId(studentSession.getStudentPersonalRefId());
    studentSessionStatus.setEvent(studentSession.getStatus().getStatusPushToAssignmnet());
    studentSessionStatus.setTime(LocalDateTime.now(Clock.systemUTC()));

    return studentSessionStatus;
  }
}
