package io.hmheng.grading.streams.grading;

import com.google.gson.Gson;
import io.hmheng.grading.authorization.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import io.hmheng.grading.authorization.util.HeadersHelper;
import io.hmheng.grading.learnosity.domain.Item;
import io.hmheng.grading.learnosity.domain.Responses;
import io.hmheng.grading.learnosity.domain.StudentSession;
import io.hmheng.grading.rest.RestTemplateService;
import io.hmheng.grading.streams.grading.domain.ActivityItemScore;
import io.hmheng.grading.streams.grading.domain.Items;
import io.hmheng.grading.streams.grading.domain.Questions;
import io.hmheng.grading.streams.grading.domain.Scores;
import io.hmheng.grading.streams.grading.domain.StudentActivitySession;
import io.hmheng.grading.streams.grading.domain.StudentAssignmentStatus;
import io.hmheng.grading.streams.grading.domain.StudentItem;
import io.hmheng.grading.streams.grading.domain.StudentQuestion;
import io.hmheng.grading.streams.grading.domain.StudentScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by nandipatim on 5/31/17.
 */
@Slf4j
@Service
public class GradingServiceImpl implements GradingService{

  @Autowired
  private RestTemplateService restTemplateService;

  @Autowired
  private HeadersHelper headersHelper;

  @Value("${grading.host.baseUrl}")
  private String gradingUrl;

  //Grading method to post scores from lernosity.
  @Override
  public void postToGrading(StudentActivitySession studentActivitySession, UUID sessionId) {
    log.info("convertToGradingStructure sessionId {}", sessionId);
    log.info("convertToGradingStructure StudentActivitySession {}", studentActivitySession);
    String uri = String.format("/v1/activities/%s/init", sessionId);
    HttpHeaders httpHeaders = headersHelper.createHttpHeaders(AuthorizationService.Service.GRADING);
    restTemplateService.postEntity(gradingUrl, uri, studentActivitySession, httpHeaders);
  }

  @Override
  public StudentActivitySession convertToGradingStructure(StudentSession studentSession) {
    log.info("convertToGradingStructure StudentSession {}", studentSession);
    StudentActivitySession studentActivitySession = getActivitySession(studentSession);

    List<Item> itemList = studentSession.getItems();
    List<Responses> responsesList = studentSession.getResponses();

    if (itemList != null) {
      List<Items> gradingItemsList = getItems(itemList, responsesList);
      studentActivitySession.setItems(gradingItemsList);
    }

    if (responsesList != null) {
      List<ActivityItemScore> activityItemScoreList = getActivityItemScore(responsesList);
      studentActivitySession.setActivityItems(activityItemScoreList);
    }

    if (itemList != null && responsesList != null) {
      List<StudentItem> studentItemList = getStudentItem(itemList, responsesList);
      studentActivitySession.setStudentItems(studentItemList);
    }

    log.info("convertToGradingStructure studentActivitySession {}", studentActivitySession);
    postToGrading(studentActivitySession, studentSession.getSessionId());

    return studentActivitySession;
  }

  private StudentActivitySession getActivitySession(StudentSession studentSession) {
    StudentActivitySession studentActivitySession = new StudentActivitySession();
    studentActivitySession.setSessionRefId(studentSession.getSessionId());
    studentActivitySession.setActivityRefId(studentSession.getActivityId());
    studentActivitySession.setStatus(StudentAssignmentStatus.READY_FOR_SCORING);
    studentActivitySession.setMaxScore(studentSession.getMaxScore());
    studentActivitySession.setNumQuestions(studentSession.getNumQuestions());
    studentActivitySession.setStudentPersonalRefId(studentSession.getUserId());
    studentActivitySession.setNumAttempted(studentSession.getNumAttempted());
    studentActivitySession.setScore(studentSession.getScore());
    studentActivitySession.setSessionDuration(studentSession.getSessionDuration());
    studentActivitySession.setDateStarted(studentSession.getStartDate());
    studentActivitySession.setDateCompleted(studentSession.getCompletedDate());
    studentActivitySession.setUserAgent(studentSession.getMetadata().getUserAgent());
    studentActivitySession.setMaxTime(studentSession.getMetadata().getMaxTime());
    return studentActivitySession;
  }

  private List<Items> getItems(List<Item> itemList, List<Responses> responsesList) {
    List<Items> gradingItemsList = new ArrayList<Items>();
    for (Item item : itemList) {
      Items gradingItems = new Items();
      gradingItems.setItemReference(item.getReference());
      if (item.getSource() != null) {
        gradingItems.setOrganizationId(item.getSource().getOrganisationId());
        gradingItems.setItemPoolId(item.getSource().getItemPoolId());
      }
      if (item.getScoring() != null) {
        gradingItems.setType(item.getScoring().getType());
      }
      if (responsesList != null) {
        List<Questions> gradingQuestionsList = getQuestions(responsesList, item.getReference());
        gradingItems.setQuestions(gradingQuestionsList);
      }
      gradingItemsList.add(gradingItems);
    }
    return gradingItemsList;
  }

  private List<Questions> getQuestions(List<Responses> responsesList, String itemReference) {
    List<Responses> foundResponsesList = responsesList.stream().filter(r -> r.getItemReference().equals(itemReference)).collect(Collectors.toList());
    List<Questions> gradingQuestionsList = new ArrayList<Questions>();
    if (foundResponsesList != null) {
      for (Responses foundResponses : foundResponsesList) {
        Questions questions = new Questions();
        questions.setQuestionReference(foundResponses.getQuestionReference());
        questions.setQuestionType(foundResponses.getQuestionType());
        questions.setAutomarkable(foundResponses.isAutomarkable());
        // set scores only if it is autoscore
        if (foundResponses.isAutomarkable()) {
          List<Scores> scoresList = new ArrayList<Scores>();
          Scores scores = new Scores();
          scores.setScoreReference(foundResponses.getQuestionReference());
          scores.setAutomarkable(foundResponses.isAutomarkable());
          //scores.setCorrectResponse();
          scoresList.add(scores);
          questions.setScores(scoresList);
        }
        gradingQuestionsList.add(questions);
      }
    }
    return gradingQuestionsList;
  }

  private List<ActivityItemScore> getActivityItemScore(List<Responses> responsesList) {
    List<ActivityItemScore> activityItemScoreList = new ArrayList<ActivityItemScore>();
    for (Responses resp : responsesList) {
      if (resp.isAutomarkable()) {
        ActivityItemScore activityItemScore = new ActivityItemScore();
        activityItemScore.setItemReference(resp.getItemReference());
        activityItemScore.setMaxScore(resp.getMaxScore());
        activityItemScore.setQuestionReference(resp.getQuestionReference());
        activityItemScore.setScoreReference(resp.getQuestionReference());
        activityItemScore.setWeight(resp.getMaxScore());
        activityItemScoreList.add(activityItemScore);
        if (resp.getResponse() != null) {
          if (resp.getResponse().containsKey("apiVersion")) {
            Gson gson = new Gson();
            String version = gson.toJson(resp.getResponse().get("apiVersion"));
            activityItemScore.setVersion(version);
          }
        }
      }
    }
    return activityItemScoreList;
  }

  private List<StudentItem> getStudentItem(List<Item> itemList, List<Responses> responsesList) {

    List<StudentItem> studentItemList = new ArrayList<StudentItem>();
    for (Responses response : responsesList) {
      StudentItem studentItem = new StudentItem();
      studentItem.setItemReference(response.getItemReference());
      studentItem.setMaxScore(response.getMaxScore());
      Item foundItem = itemList.stream().filter(i -> i.getReference().equals(response.getItemReference())).findAny().orElse(null);
      if (foundItem != null) {
        studentItem.setTime(foundItem.getTime());
        studentItem.setUserFlagged(foundItem.isUserFlagged());
      }
      List<StudentQuestion> studentQuestionList = getStudentQuestion(response);
      studentItem.setQuestions(studentQuestionList);
      studentItemList.add(studentItem);
    }
    return studentItemList;
  }

  private List<StudentQuestion> getStudentQuestion(Responses response) {
    List<StudentQuestion> studentQuestionList = new ArrayList<StudentQuestion>();
    StudentQuestion studentQuestion = new StudentQuestion();
    studentQuestion.setQuestionReference(response.getQuestionReference());
    if (response.getResponse() != null) {
      if (response.getResponse().containsKey("value")) {
        Gson gson = new Gson();
        String responseValue = gson.toJson(response.getResponse().get("value"));
        studentQuestion.setActualResponse(responseValue);
      }
    }
    studentQuestion.setResponseId(response.getResponseId());
    if (response.isAutomarkable()) {
      List<StudentScore> studentScoreList = getStudentScore(response);
      studentQuestion.setResponses(studentScoreList);
    }
    studentQuestionList.add(studentQuestion);
    return studentQuestionList;
  }

  private List<StudentScore> getStudentScore(Responses response) {
    List<StudentScore> studentScoreList = new ArrayList<StudentScore>();
    StudentScore studentScore = new StudentScore();
    studentScore.setResponseId(response.getResponseId().toString());
    studentScore.setScoreReference(response.getQuestionReference());
    studentScore.setAttempted(response.isAttempted());
    studentScore.setMaxScore(response.getMaxScore());
    studentScore.setScore(response.getScore());
    //studentScore.setStaffPersonalRefId();
    studentScore.setWeight(response.getMaxScore());
    if (response.getResponse() != null) {
      if (response.getResponse().containsKey("value")) {
        Gson gson = new Gson();
        String responseValue = gson.toJson(response.getResponse().get("value"));
        studentScore.setValue(responseValue);
      }
    }
    studentScoreList.add(studentScore);
    return studentScoreList;
  }

}
