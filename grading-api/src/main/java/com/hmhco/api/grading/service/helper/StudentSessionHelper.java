package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.controller.exception.PreConditionRequiredException;
import com.hmhco.api.grading.dao.readonly.ActivityStudentItemViewRepository;
import com.hmhco.api.grading.dao.readwrite.StudentSessionEntityRepository;
import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;
import com.hmhco.api.grading.mapper.EntityMapper;
import com.hmhco.api.grading.mapper.entitymapper.ActivityStudentItemMapper;
import com.hmhco.api.grading.mapper.entitymapper.StudentItemViewMapper;
import com.hmhco.api.grading.mapper.entitymapper.StudentSessionGetViewMapper;
import com.hmhco.api.grading.mapper.viewmapper.StudentSessionViewMapper;
import com.hmhco.api.grading.service.action.SaveStudentSessionRequest;
import com.hmhco.api.grading.utils.StudentSessionRequestType;
import com.hmhco.api.grading.views.StudentActivitySessionView;
import com.hmhco.api.grading.views.StudentItemView;
import com.hmhco.api.grading.views.StudentSessionView;
import com.hmhco.api.grading.views.getresponse.ScoringCompleteResponse;
import com.hmhco.api.grading.views.getresponse.StudentItemGetView;
import com.hmhco.api.grading.views.getresponse.StudentSessionGetView;
import io.hmheng.grading.utils.StudentAssignmentStatus;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class StudentSessionHelper {

  @Autowired
  private StudentSessionEntityRepository studentSessionEntityRepository;

  @Autowired
  private ActivityStudentItemViewRepository activityStudentItemViewRepository;

  @Autowired
  private EntityMapper entityMapper;

  @Autowired
  private StudentItemViewMapper studentItemViewMapper;

  @Autowired
  private StudentSessionViewMapper studentSessionViewMapper;

  @Autowired
  private StudentSessionGetViewMapper studentSessionGetViewMapper;

  @Autowired
  private ActivityStudentItemMapper activityStudentItemMapper;

  @Autowired
  private StudentItemHelper studentItemHelper;

  public StudentSessionEntity getOrCreateStudentSessionEntity(SaveStudentSessionRequest request) {

    StudentSessionEntity studentSessionEntity = studentSessionEntityRepository.findBySessionRefId(request.getSessionId());

    if(StudentSessionRequestType.INIT == request.getRequestType()) {
      StudentActivitySessionView activitySessionView = request.getStudentActivitySessionView();
      if(studentSessionEntity == null) {
        activitySessionView.setSessionRefId(request.getSessionId());
        studentSessionEntity = entityMapper.convert(activitySessionView);
        List<StudentItemView> studentItemViews =  activitySessionView.getStudentItems();
        studentSessionEntity.setStudentItems(studentItemViewMapper.convert(studentItemViews));
        request.setIsNewSessionEnity(Boolean.TRUE);
      } else {
        StudentSessionEntity studentSessionEntityConverted = entityMapper.convert(activitySessionView);
        request.setIsNewSessionEnity(Boolean.FALSE);

        if(studentSessionEntityConverted.getSessionRefId()!= null)
          studentSessionEntity.setSessionRefId(studentSessionEntityConverted.getSessionRefId());

        if(studentSessionEntityConverted.getStudentPersonalRefId()!= null)
          studentSessionEntity.setStudentPersonalRefId(studentSessionEntityConverted.getStudentPersonalRefId());

        if(studentSessionEntityConverted.getUserAgent() != null)
          studentSessionEntity.setUserAgent(studentSessionEntityConverted.getUserAgent());

        if(studentSessionEntityConverted.getDateCompleted() != null)
          studentSessionEntity.setDateCompleted(studentSessionEntityConverted.getDateCompleted());

        if(studentSessionEntityConverted.getDateStarted() != null)
          studentSessionEntity.setDateStarted(studentSessionEntityConverted.getDateStarted());

        if(studentSessionEntityConverted.getNumAttempted() != null)
          studentSessionEntity.setNumAttempted(studentSessionEntityConverted.getNumAttempted());

        if(studentSessionEntityConverted.getScore() != null)
          studentSessionEntity.setScore(studentSessionEntityConverted.getScore());

        if(studentSessionEntityConverted.getSessionDuration() != null)
          studentSessionEntity.setSessionDuration(studentSessionEntityConverted.getSessionDuration());

        if(studentSessionEntityConverted.getStatus() != null)
          studentSessionEntity.setStatus(studentSessionEntityConverted.getStatus());

      }
    }else if(StudentSessionRequestType.SCORES == request.getRequestType()) {
      StudentSessionView studentSessionView = request.getStudentSessionView();
      if(studentSessionEntity == null) {
        request.setIsNewSessionEnity(Boolean.TRUE);
        studentSessionView.setSessionRefId(request.getSessionId());
        studentSessionEntity = studentSessionViewMapper.convert(studentSessionView);
        //TODO when integrate with Lernosity will be change , Commenting for UI to use it.
        // throw new EntityNotFoundException();
      } else {
        request.setIsNewSessionEnity(Boolean.FALSE);
        StudentSessionEntity studentSessionEntityConverted = studentSessionViewMapper.convert(studentSessionView);


        if(studentSessionEntityConverted.getSessionRefId()!= null)
          studentSessionEntity.setSessionRefId(studentSessionEntityConverted.getSessionRefId());

        if(studentSessionEntityConverted.getStudentPersonalRefId()!= null)
          studentSessionEntity.setStudentPersonalRefId(studentSessionEntityConverted.getStudentPersonalRefId());

        if(studentSessionEntityConverted.getUserAgent() != null)
          studentSessionEntity.setUserAgent(studentSessionEntityConverted.getUserAgent());

        if(studentSessionEntityConverted.getDateCompleted() != null)
          studentSessionEntity.setDateCompleted(studentSessionEntityConverted.getDateCompleted());

        if(studentSessionEntityConverted.getDateStarted() != null)
          studentSessionEntity.setDateStarted(studentSessionEntityConverted.getDateStarted());

        if(studentSessionEntityConverted.getNumAttempted() != null)
          studentSessionEntity.setNumAttempted(studentSessionEntityConverted.getNumAttempted());

        if(studentSessionEntityConverted.getScore() != null)
          studentSessionEntity.setScore(studentSessionEntityConverted.getScore());

        if(studentSessionEntityConverted.getSessionDuration() != null)
          studentSessionEntity.setSessionDuration(studentSessionEntityConverted.getSessionDuration());

        if(studentSessionEntityConverted.getStatus() != null)
          studentSessionEntity.setStatus(studentSessionEntityConverted.getStatus());
      }
    }

    return studentSessionEntity;
  }

  public StudentSessionEntity findBySessionRefId(UUID sessionId){

    if(sessionId == null){
      throw new EntityNotFoundException("Session Id should be null.");
    }

    StudentSessionEntity studentSessionEntity = studentSessionEntityRepository.findBySessionRefId(sessionId);

    if(studentSessionEntity == null){
      throw new EntityNotFoundException("Session Id "+sessionId+" not found");
    }

    return  studentSessionEntity;
  }

  public Boolean checkForScoringCompletedAndUpdateStatus(UUID sessionId , StudentSessionEntity studentSessionEntity
      , ScoringCompleteResponse scoringCompleteResponse) {
      return  checkForScoringCompletedAndUpdateStatus(sessionId , studentSessionEntity , scoringCompleteResponse , null);
  }

  public Boolean checkForScoringCompletedAndUpdateStatus(UUID sessionId , StudentSessionEntity studentSessionEntity
      , ScoringCompleteResponse scoringCompleteResponse , List<ActivityStudentItemViewEntity>  itemViewEntities){


    if(studentSessionEntity == null){
      studentSessionEntity = findBySessionRefId(sessionId);
    }

    if(itemViewEntities == null){
      itemViewEntities = activityStudentItemViewRepository.findBySessionId(sessionId);
    }


    if(CollectionUtils.isEmpty(itemViewEntities)){
      throw new PreConditionRequiredException("Session Id "+sessionId+" Do not have Any items");
    }


    List<StudentItemGetView> studentItemGetViews = activityStudentItemMapper.convert(itemViewEntities);

    Boolean isScoringCompleted = studentItemHelper.isScoringCompleted(studentItemGetViews);

    if (isScoringCompleted) {
      studentSessionEntity = updateStudentStatus(studentSessionEntity
          , StudentAssignmentStatus.SCORING_COMPLETED);
    }

    StudentSessionGetView studentSessionView = studentSessionGetViewMapper.convert(studentSessionEntity);

    studentSessionView.setItems(studentItemGetViews);
    scoringCompleteResponse.setActivityRefId(studentSessionEntity.getActivityRefId());
    scoringCompleteResponse.setStudentSession(studentSessionView);

    return isScoringCompleted;
  }

  public StudentSessionEntity updateStudentStatus(StudentSessionEntity studentSession , StudentAssignmentStatus assignmentStatus){

    if(studentSession == null){
      throw new EntityNotFoundException("Student Entity cannot be null");
    }

    if(studentSession.getStatus() != assignmentStatus){
      studentSession.setScoringStreamSeq(null);
      studentSession.setDatePushedToScoring(null);
      studentSession.setStatusStreamSeq(null);
      studentSession.setDatePushedToStatus(null);
      studentSession.setStatus(assignmentStatus);
      studentSession = studentSessionEntityRepository.saveAndFlush(studentSession);
    }

    return studentSession;
  }
}
