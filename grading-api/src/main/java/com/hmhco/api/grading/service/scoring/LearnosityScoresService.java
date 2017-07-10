package com.hmhco.api.grading.service.scoring;

import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;
import java.util.List;

/**
 * Created by nandipatim on 6/26/17.
 */
public interface LearnosityScoresService {

  KinesisPutRecordResult createLearnosityStudentSession(StudentSessionEntity studentSessionEntity
      ,List<ActivityStudentItemViewEntity> itemViewEntities, Boolean pushScores);
}
