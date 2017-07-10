package io.hmheng.grading.streams.grading.learnosity;

import io.hmheng.grading.learnosity.domain.StudentSession;
import io.hmheng.grading.streams.grading.GradingService;
import io.hmheng.grading.streams.scoring.ScoringService;
import io.hmheng.grading.streams.scoring.domain.Event;
import io.hmheng.grading.util.enums.TestType;
import io.hmheng.grading.utils.StudentAssignmentStatus;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 3/8/17.
 */
@Slf4j
@Component
public class LearnosityStudentSessionDataProcessor implements Consumer<StudentSession> {

  @Autowired
  private GradingService gradingService;

  @Autowired
  private ScoringService scoringService;

  @Override
  public void accept(StudentSession studentSession) {

    log.info("Processing Student Session Scores from Learnosity: {}", studentSession);
    if(studentSession != null) {

      UUID activityId = studentSession.getActivityId();

      Event event = scoringService.getEventForActivityRefid(activityId);

      if(event != null){

        TestType testType = event.getTestType();

        Boolean isGradingRequired = studentSession.getIsGradingRequired();

        if(isGradingRequired == null){
          isGradingRequired = Boolean.TRUE;
        }


        if(TestType.PROGRAM == testType && event.isManualScoringRequired() && isGradingRequired) {

          gradingService.postToGrading(gradingService.convertToGradingStructure(studentSession) , studentSession.getSessionId());
        }
      }
    }
  }
}