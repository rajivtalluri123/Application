package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.controller.exception.PreConditionRequiredException;
import com.hmhco.api.grading.dao.readonly.ActivityStudentItemViewRepository;
import com.hmhco.api.grading.entities.StudentItemEntity;
import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentQuestionViewEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentScoreViewEntity;
import com.hmhco.api.grading.mapper.entitymapper.StudentItemViewMapper;
import com.hmhco.api.grading.service.action.SaveStudentSessionRequest;
import com.hmhco.api.grading.views.StudentItemView;
import com.hmhco.api.grading.views.getresponse.StudentItemGetView;
import io.hmheng.grading.learnosity.domain.Item;
import io.hmheng.grading.learnosity.domain.Responses;
import io.hmheng.grading.learnosity.domain.Scoring;
import io.hmheng.grading.learnosity.domain.Source;
import io.hmheng.grading.utils.BeanPropertyUtils;
import io.hmheng.grading.utils.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.ListUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class StudentItemHelper {

  @Autowired
  private StudentItemViewMapper studentItemViewMapper;

  @Autowired
  private StudentQuestionHelper studentQuestionHelper;

  @Autowired
  private ActivityStudentItemViewRepository activityStudentItemViewRepository;


  public List<StudentItemEntity> getOrCreateStudentItemEntity(SaveStudentSessionRequest request
      ,StudentSessionEntity studentSessionEntity,List<StudentItemView> studentItems){

    if(studentSessionEntity == null) {
      throw new IllegalArgumentException();
    }

    List<StudentItemEntity> studentItemEntitiesFromDB = studentSessionEntity.getStudentItems();

    if(studentItems == null) {
      return studentItemEntitiesFromDB;
    }

    List<StudentItemEntity> studentItemEntityList = (!request.getIsNewSessionEnity())? studentItemViewMapper.convert(studentItems):studentItemEntitiesFromDB;
    studentItemEntityList.stream().forEach(studentItem -> {
      studentItem.setSessionRefId(studentSessionEntity.getSessionRefId());
      studentItem.setStudentSession(studentSessionEntity);
      if(request.getIsNewSessionEnity()){
        studentQuestionHelper.getOrCreateStudentQuestionEntity(studentItem, studentItem);
      }

    });

    if(!CollectionUtils.isEmpty(studentItemEntitiesFromDB) && !request.getIsNewSessionEnity()) {
      studentItemEntitiesFromDB.stream().forEach(item -> {
        item.setSessionRefId(studentSessionEntity.getSessionRefId());
        item.setStudentSession(studentSessionEntity);
        int indexOfItem = studentItemEntityList.indexOf(item);
        StudentItemEntity studentItemEntity = null;
        List<StudentQuestionEntity> studentQuestionEntities = item.getStudentQuestions();
        List<StudentScoreEntity> studentScoreEntities = item.getStudentScores();
        if (indexOfItem != -1) {

          studentItemEntity = studentItemEntityList.get(indexOfItem);
          studentItemEntity.setRefId(item.getRefId());
          studentItemEntity.setSessionRefId(studentSessionEntity.getSessionRefId());
          BeanPropertyUtils.copyPropertiesWithOnlyPopulated(studentItemEntity, item);
          item.setStudentSession(studentSessionEntity);
        }

        item.setStudentQuestions(studentQuestionEntities);
        item.setStudentScores(studentScoreEntities);
        if(studentItemEntity != null) {
          item.setStudentQuestions(studentQuestionHelper.getOrCreateStudentQuestionEntity(item,
              studentItemEntity));
        }
      });
    }

    List<StudentItemEntity> newEntities = null;

    if(!request.getIsNewSessionEnity()) {
      newEntities = ListUtils.subtract(studentItemEntityList, studentItemEntitiesFromDB);
    }

    if(!CollectionUtils.isEmpty(newEntities)) {
      newEntities.stream().forEach(items -> {
        items.setStudentSession(studentSessionEntity);
        items.setSessionRefId(items.getStudentSession().getSessionRefId());
        studentQuestionHelper.getOrCreateStudentQuestionEntity(items, items);
      });
      studentItemEntitiesFromDB.addAll(newEntities);
    }

    return studentItemEntitiesFromDB;

  }

  public Boolean isScoringCompleted(List<StudentItemGetView> studentItemGetViews){

    if(CollectionUtils.isEmpty(studentItemGetViews)){
      return Boolean.FALSE;
    }

    return (studentItemGetViews.stream().allMatch(
        studentItemGetView -> (Status.SCORING_COMPLETED == studentItemGetView.getStatus())));
  }

  public List<String> getItemReferencesNotScored(List<StudentItemGetView> studentItemGetViews){

    if(CollectionUtils.isEmpty(studentItemGetViews)){
      return null;
    }

    List<String> itemReferencesNotScored = new ArrayList<>();

    studentItemGetViews.stream().forEach(studentItemGetView -> {
      if(Status.SCORING_COMPLETED != studentItemGetView.getStatus()){
        itemReferencesNotScored.add(studentItemGetView.getItemReference());
      }
    });
    return itemReferencesNotScored;
  }

  public StudentItems createResponses(StudentSessionEntity studentSessionEntity
      ,List<ActivityStudentItemViewEntity> itemViewEntities){

    if(studentSessionEntity == null){
      throw new IllegalArgumentException("Student Session Entity cannot be null");
    }

    if(itemViewEntities == null){
      itemViewEntities = activityStudentItemViewRepository.findBySessionId(studentSessionEntity.getSessionRefId()) ;
    }

    if(CollectionUtils.isEmpty(itemViewEntities)){
      throw new PreConditionRequiredException("Session Id "+studentSessionEntity.getSessionRefId()+" Do not have Any items");
    }

    StudentItems studentItems = new StudentItems();

    List<Responses> responsesList = new ArrayList<>();

    List<Item> items = new ArrayList<>();

    studentItems.setResponses(responsesList);
    studentItems.setItems(items);

    if(!CollectionUtils.isEmpty(itemViewEntities)) {
      itemViewEntities.forEach(studentItemEntity -> {
        Item item = new Item();
        items.add(item);
        item.setReference(studentItemEntity.getItemReference());
        Source source = new Source();
        source.setItemPoolId(studentItemEntity.getItemPoolId());
        source.setOrganisationId(studentItemEntity.getOrganizationId());
        source.setReference(studentItemEntity.getItemReference());
        item.setSource(source);
        item.setTime(studentItemEntity.getTime());
        Boolean userFlagged = studentItemEntity.getUserFlagged();
        item.setUserFlagged((userFlagged == null) ? Boolean.FALSE : userFlagged);
        Scoring scoring = new Scoring();
        scoring.setMaxScore(0);
        scoring.setScore(0);
        scoring.setMaxScoreOfAttempted(0);
        item.setScoring(scoring);
        List<String> responseIds = new ArrayList<>();
        item.setResponseIds(responseIds);
        List<ActivityStudentQuestionViewEntity> studentQuestionEntities = studentItemEntity.getQuestions();
        if (!CollectionUtils.isEmpty(studentQuestionEntities)) {
          studentQuestionEntities.forEach(studentQuestionEntity -> {
            List<ActivityStudentScoreViewEntity> studentScoreEntities = studentQuestionEntity.getResponses();
            if (!CollectionUtils.isEmpty(studentScoreEntities)) {
              studentScoreEntities.forEach(studentScoreEntity -> {
               Responses responses = new Responses();
                responsesList.add(responses);
                responses.setResponseId(studentScoreEntity.getResponseId());
                responses.setItemReference(studentItemEntity.getItemReference());
                responses.setQuestionReference(studentQuestionEntity.getQuestionReference());
                responses.setQuestionType(studentQuestionEntity.getQuestionType());
                responses.setScore(studentScoreEntity.getScore());
                responses.setMaxScore(studentScoreEntity.getMaxScore());
                Boolean isAttempted = studentScoreEntity.getAttempted();
                responses.setAttempted((isAttempted == null) ? Boolean.FALSE : isAttempted);
                // Get the Automarkable from Question.
                Boolean isAutomarkable = studentQuestionEntity.isAutomarkable();
                responses.setAutomarkable((isAutomarkable == null) ? Boolean.FALSE : isAutomarkable);
                Map<String , Object> response = new HashMap<String, Object>();
                String valueJson = studentScoreEntity.getValue();
                Object valueObject = null;
                try{
                	valueObject = new ObjectMapper().readValue(valueJson, Object.class);
                }catch(Exception e){}
                response.put("value", valueObject);
                responses.setResponse(response);
                responseIds.add(studentScoreEntity.getResponseId());
                Integer maxScore = studentScoreEntity.getMaxScore();
                if(maxScore == null)
                  maxScore = 0;
                scoring.setMaxScore(scoring.getMaxScore() + maxScore);
                scoring.setScore(scoring.getScore() + ((studentScoreEntity.getScore() == null) ? 0 :
                    studentScoreEntity.getScore()));
                scoring.setType(studentScoreEntity.getScoreType());
                if(isAttempted) {
                  scoring.setMaxScoreOfAttempted(scoring.getMaxScoreOfAttempted() + maxScore);
                }
              });
            }
          });
        }
      });
    }
      return studentItems;
  }


  public static class StudentItems {
    List<Responses> responses;
    List<Item> items;

    public List<Responses> getResponses() {
      return responses;
    }

    public void setResponses(List<Responses> responses) {
      this.responses = responses;
    }

    public List<Item> getItems() {
      return items;
    }

    public void setItems(List<Item> items) {
      this.items = items;
    }
  }

}
