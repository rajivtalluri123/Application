package com.hmhco.api.grading.service.assignment;

import com.hmhco.api.grading.entities.StudentSessionEntity;
import io.hmheng.grading.learnosity.domain.StudentSession;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;

/**
 * Created by nandipatim on 6/29/17.
 */
public interface StudentSessionDetailService {

  KinesisPutRecordResult pushStatusToAssignmentService(StudentSessionEntity studentSession , Boolean pushAssignmentStatus);
}
