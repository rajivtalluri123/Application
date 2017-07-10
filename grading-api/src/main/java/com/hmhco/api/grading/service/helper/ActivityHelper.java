package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.dao.readwrite.ActivityEntityRepository;
import com.hmhco.api.grading.entities.ActivityEntity;
import com.hmhco.api.grading.entities.ActivityItemScoreEntity;
import com.hmhco.api.grading.mapper.viewmapper.ActivityViewMapper;
import com.hmhco.api.grading.service.action.SaveStudentSessionRequest;
import com.hmhco.api.grading.utils.StudentSessionRequestType;
import com.hmhco.api.grading.views.ActivityItemScoreView;
import com.hmhco.api.grading.views.ActivityView;
import com.hmhco.api.grading.views.StudentActivitySessionView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class ActivityHelper {

  @Autowired
  private ActivityItemScoreHelper activityItemScoreHelper;

  @Autowired
  private ActivityEntityRepository activityEntityRepository;

  @Autowired
  private ActivityViewMapper activityViewMapper;

  public ActivityEntity getOrCreateActivity(SaveStudentSessionRequest request){

    UUID activityId = getActivityRefId(request);
    ActivityEntity activityEntity = activityEntityRepository.findOne(activityId);


    if(activityEntity == null) {
      if(StudentSessionRequestType.INIT == request.getRequestType()) {
        activityEntity = new ActivityEntity();

      }else if(StudentSessionRequestType.SCORES == request.getRequestType()) {
        if(request.getStudentSessionView().getAssignment() != null) {
          activityEntity = activityViewMapper.convert(request.getStudentSessionView().getAssignment());
          //activityEntity.setActivityItemScores(getOrCreateActivityItemScore(activityEntity, getActivityItems(request)));
          activityEntity.setActivityItemScores(activityItemScoreHelper.getOrCreateActivityItemScore(activityEntity, getActivityItems(request)));
        }
      }
    }else if(StudentSessionRequestType.SCORES == request.getRequestType()) {
      ActivityView activityView = request.getStudentSessionView().getAssignment();
      if(activityView != null) {
        List<ActivityItemScoreEntity> activityItemScoreEntities = activityEntity.getActivityItemScores();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(activityView, activityEntity);
        activityEntity.setActivityItemScores(activityItemScoreEntities);
        //activityEntity.setActivityItemScores(getOrCreateActivityItemScore(activityEntity, getActivityItems(request)));
        activityEntity.setActivityItemScores(activityItemScoreHelper.getOrCreateActivityItemScore(activityEntity, getActivityItems(request)));
      }
    }
    activityEntity.setActivityRefId(activityId);

    if(StudentSessionRequestType.INIT == request.getRequestType()){
      StudentActivitySessionView studentActivitySessionView = request.getStudentActivitySessionView();
      if(studentActivitySessionView.getStaffPersonalRefId() != null) {
        activityEntity.setStaffPersonalRefId(studentActivitySessionView.getStaffPersonalRefId());
      }
      if(studentActivitySessionView.getTeacherAssignmentRefId() != null) {
        activityEntity.setTeacherAssignmentRefId(studentActivitySessionView.getTeacherAssignmentRefId());
      }
      if(studentActivitySessionView.getNumQuestions() != null) {
        activityEntity.setNumQuestions(studentActivitySessionView.getNumQuestions());
      }
      if(studentActivitySessionView.getMaxScore() != null) {
        activityEntity.setMaxScore(studentActivitySessionView.getMaxScore());
      }
      if(studentActivitySessionView.getMaxTime() != null) {
        activityEntity.setMaxTime(studentActivitySessionView.getMaxTime());
      }
      //activityEntity.setActivityItemScores(getOrCreateActivityItemScore(activityEntity, getActivityItems(request)));
      activityEntity.setActivityItemScores(activityItemScoreHelper.getOrCreateActivityItemScore(activityEntity, getActivityItems(request)));
    }
    //TODO Logic to update all present Entities and add new Entities of Items

    return activityEntity;

  }

  private UUID getActivityRefId(SaveStudentSessionRequest request) {

    UUID activityId;

    if(StudentSessionRequestType.INIT == request.getRequestType()) {
      if(request.getStudentActivitySessionView() == null){
        throw new BadRequestException();
      }
      activityId = request.getStudentActivitySessionView().getActivityRefId();

    } else if(StudentSessionRequestType.SCORES == request.getRequestType()) {
      if (request.getStudentSessionView() == null) {
        throw new BadRequestException();
      }
      activityId = request.getStudentSessionView().getAssignment().getActivityRefId();
    }else
      throw new BadRequestException();

    return activityId;
  }

  private List<ActivityItemScoreView> getActivityItems(SaveStudentSessionRequest request) {

    if(StudentSessionRequestType.SCORES == request.getRequestType()){
      return request.getStudentSessionView().getAssignment().getActivityItemScores();
    }

    return request.getStudentActivitySessionView().getActivityItems();
  }
}
