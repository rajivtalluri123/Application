package io.hmheng.grading.streams.grading;


import io.hmheng.grading.learnosity.domain.StudentSession;
import io.hmheng.grading.streams.grading.domain.StudentActivitySession;

import java.util.UUID;

/**
 * Created by nandipatim on 5/31/17.
 */
public interface GradingService {
  //Grading method to post scores from lernosity.

  void postToGrading(StudentActivitySession studentActivitySession, UUID sessionId);

  StudentActivitySession convertToGradingStructure(StudentSession studentSession);

}
