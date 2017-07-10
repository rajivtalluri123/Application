package com.hmhco.api.grading.service.scoring;

import com.hmhco.api.grading.controller.exception.PreConditionRequiredException;
import com.hmhco.api.grading.dao.readonly.ActivityStudentItemViewRepository;
import com.hmhco.api.grading.entities.ActivityEntity;
import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;
import com.hmhco.api.grading.service.helper.StudentItemHelper;
import io.hmheng.grading.learnosity.domain.Metadata;
import io.hmheng.grading.learnosity.domain.StudentSession;
import io.hmheng.grading.streams.config.ProduceAssesmentScoresStreamConfiguration;
import io.hmheng.grading.streams.kinesis.KinesisStreamDataService;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 6/26/17.
 */
@Service
public class LearnosityScoresServiceImpl implements LearnosityScoresService {

  @Autowired
  private KinesisStreamDataService kinesisStreamDataService;

  @Autowired
  private ProduceAssesmentScoresStreamConfiguration produceAssesmentScoresStreamConfiguration;

  @Autowired
  private ActivityStudentItemViewRepository activityStudentItemViewRepository;

  @Autowired
  private StudentItemHelper studentItemHelper;

  @Override
  public KinesisPutRecordResult createLearnosityStudentSession(StudentSessionEntity studentSessionEntity
     ,List<ActivityStudentItemViewEntity> itemViewEntities, Boolean pushScores){

    if(itemViewEntities == null){
      itemViewEntities = activityStudentItemViewRepository.findBySessionId(studentSessionEntity.getSessionRefId());
    }

    if(CollectionUtils.isEmpty(itemViewEntities)){
      throw new PreConditionRequiredException("Session Id "+studentSessionEntity.getSessionRefId()+" Do not have Any items");
    }

    StudentSession studentSession = new StudentSession();
    studentSession.setUserId(studentSessionEntity.getStudentPersonalRefId());
    studentSession.setSessionId(studentSessionEntity.getSessionRefId());
    studentSession.setActivityId(studentSessionEntity.getActivityRefId());
    studentSession.setStatus(studentSessionEntity.getStatus().getStatusPushToScoring());
    studentSession.setCompletedDate(studentSessionEntity.getDateCompleted());
    studentSession.setStartDate(studentSessionEntity.getDateStarted());
    studentSession.setIsGradingRequired(Boolean.FALSE);
    studentSession.setSessionDuration(studentSessionEntity.getSessionDuration());
    studentSession.setNumAttempted(studentSessionEntity.getNumAttempted());
    ActivityEntity activityEntity = studentSessionEntity.getActivityEntity();
    studentSession.setMaxScore(activityEntity.getMaxScore());
    studentSession.setNumQuestions(activityEntity.getNumQuestions());
    studentSession.setMetadata(populateMetaData(studentSessionEntity, activityEntity));

    StudentItemHelper.StudentItems studentItems = studentItemHelper.createResponses(
        studentSessionEntity, itemViewEntities);

    studentSession.setItems(studentItems.getItems());
    studentSession.setResponses(studentItems.getResponses());

    KinesisPutRecordResult putRecordResult = (pushScores) ? kinesisStreamDataService.pushToStream(studentSession
        , produceAssesmentScoresStreamConfiguration) : new KinesisPutRecordResult();

     return putRecordResult;
  }




  private Metadata populateMetaData(StudentSessionEntity studentSessionEntity, ActivityEntity activityEntity){

    Metadata metadata = new Metadata();
    metadata.setMaxTime(activityEntity.getMaxTime());
    metadata.setUserAgent(studentSessionEntity.getUserAgent());

    return metadata;
  }

}
