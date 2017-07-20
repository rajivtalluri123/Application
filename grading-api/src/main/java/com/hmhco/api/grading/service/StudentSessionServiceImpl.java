package com.hmhco.api.grading.service;

import com.hmhco.api.grading.controller.exception.NotFoundException;
import com.hmhco.api.grading.dao.readonly.ActivityStudentItemViewRepository;
import com.hmhco.api.grading.dao.readwrite.ActivityEntityRepository;
import com.hmhco.api.grading.dao.readwrite.ItemEntityRepository;
import com.hmhco.api.grading.dao.readwrite.QuestionEntityRepository;
import com.hmhco.api.grading.dao.readwrite.ScoreEntityRepository;
import com.hmhco.api.grading.dao.readwrite.StudentItemEntityRepository;
import com.hmhco.api.grading.dao.readwrite.StudentQuestionEntityRepository;
import com.hmhco.api.grading.dao.readwrite.StudentScoreEntityRepository;
import com.hmhco.api.grading.dao.readwrite.StudentSessionEntityRepository;
import com.hmhco.api.grading.entities.ActivityEntity;
import com.hmhco.api.grading.entities.ActivityItemScoreEntity;
import com.hmhco.api.grading.entities.ItemEntity;
import com.hmhco.api.grading.entities.QuestionEntity;
import com.hmhco.api.grading.entities.ScoreEntity;
import com.hmhco.api.grading.entities.StudentItemEntity;
import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;
import com.hmhco.api.grading.mapper.entitymapper.ActivityEntityMapper;
import com.hmhco.api.grading.mapper.entitymapper.ItemEntityMapper;
import com.hmhco.api.grading.mapper.viewmapper.ItemsViewMapper;
import com.hmhco.api.grading.mapper.entitymapper.QuestionsEntityMapper;
import com.hmhco.api.grading.mapper.viewmapper.QuestionsViewMapper;
import com.hmhco.api.grading.mapper.viewmapper.ScoreViewMapper;
import com.hmhco.api.grading.mapper.entitymapper.StudentItemViewMapper;
import com.hmhco.api.grading.mapper.viewmapper.StudentQuestionViewMapper;
import com.hmhco.api.grading.mapper.viewmapper.StudentScoreViewMapper;
import com.hmhco.api.grading.mapper.entitymapper.ActivityItemScoreMapper;
import com.hmhco.api.grading.mapper.entitymapper.StudentItemEntityMapper;
import com.hmhco.api.grading.mapper.entitymapper.StudentQuestionEntityMapper;
import com.hmhco.api.grading.mapper.entitymapper.StudentScoreEntityMapper;
import com.hmhco.api.grading.mapper.entitymapper.ActivityStudentItemMapper;
import com.hmhco.api.grading.service.action.SaveResponseRequest;
import com.hmhco.api.grading.service.action.SaveStudentSessionRequest;
import com.hmhco.api.grading.service.action.SaveStudentSessionResponse;
import com.hmhco.api.grading.service.assignment.StudentSessionDetailService;
import com.hmhco.api.grading.service.config.ReadWriteService;
import com.hmhco.api.grading.service.helper.ActivityHelper;
import com.hmhco.api.grading.service.helper.ActivityItemScoreHelper;
import com.hmhco.api.grading.service.helper.ItemHelper;
import com.hmhco.api.grading.service.helper.StudentItemHelper;
import com.hmhco.api.grading.service.helper.StudentSessionHelper;
import com.hmhco.api.grading.service.scoring.LearnosityScoresService;
import com.hmhco.api.grading.utils.StudentSessionRequestType;


import com.hmhco.api.grading.views.ActivityItemScoreView;
import com.hmhco.api.grading.views.ActivityView;
import com.hmhco.api.grading.views.ItemsView;
import com.hmhco.api.grading.views.QuestionsView;
import com.hmhco.api.grading.views.ScoresView;
import com.hmhco.api.grading.views.SessionStatusView;
import com.hmhco.api.grading.views.StudentItemView;
import com.hmhco.api.grading.views.StudentQuestionView;
import com.hmhco.api.grading.views.StudentScoreView;
import com.hmhco.api.grading.views.getresponse.ScoringCompleteResponse;
import com.hmhco.api.grading.views.getresponse.StudentItemGetView;
import com.hmhco.api.grading.views.getresponse.StudentSessionGetView;
import com.hmhco.api.grading.views.request.ItemsRequestView;
import com.hmhco.api.grading.views.request.QuestionsRequestView;
import com.hmhco.api.grading.views.request.ResponseView;
import com.hmhco.api.grading.views.request.StudentItemRequestView;
import com.hmhco.api.grading.views.request.StudentQuestionRequestView;
import com.hmhco.api.grading.views.request.StudentStatusRequest;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;
import io.hmheng.grading.utils.BeanPropertyUtils;
import io.hmheng.grading.utils.PageUtils;
import io.hmheng.grading.utils.StudentAssignmentStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;


/**
 * Created by srikanthk on 4/26/17.
 */

@ReadWriteService
public class StudentSessionServiceImpl implements  StudentSessionService{


    private static  final Logger logger = LoggerFactory.getLogger(StudentSessionServiceImpl.class);


    @Autowired
    private StudentSessionEntityRepository studentSessionEntityRepository;

    @Autowired
    private ActivityEntityRepository activityEntityRepository;

    @Autowired
    private ItemEntityRepository itemEntityRepository;

    @Autowired
    private StudentScoreEntityRepository studentScoreEntityRepository;

    @Autowired
    private ItemsViewMapper itemsViewMapper;

    @Autowired
    private QuestionsViewMapper questionsViewMapper;

    @Autowired
    private ScoreEntityRepository scoreEntityRepository;


    @Autowired
    private QuestionEntityRepository questionEntityRepository;

    @Autowired
    private StudentItemEntityRepository studentItemEntityRepository;

    @Autowired
    private StudentQuestionEntityRepository studentQuestionEntityRepository;

    @Autowired
    private ActivityStudentItemViewRepository activityStudentItemViewRepository;

    @Autowired
    private ScoreViewMapper scoreViewMapper;

    @Autowired
    private ActivityStudentItemMapper activityStudentItemMapper;

    @Autowired
    private StudentScoreViewMapper studentScoreViewMapper;

    @Autowired
    private StudentItemEntityMapper studentItemEntityMapper;

    @Autowired
    private StudentQuestionEntityMapper studentQuestionEntityMapper;

    @Autowired
    private StudentScoreEntityMapper studentScoreEntityMapper;

    @Autowired
    private ItemEntityMapper itemEntityMapper;

    @Autowired
    private QuestionsEntityMapper questionsEntityMapper;

    @Autowired
    private StudentQuestionViewMapper studentQuestionViewMapper;

    @Autowired
    private StudentItemViewMapper studentItemViewMapper;

    @Autowired
    private ActivityItemScoreMapper activityItemScoreMapper;

    @Autowired
    private ActivityEntityMapper activityEntityMapper;

    @Autowired
    private ActivityItemScoreHelper activityItemScoreHelper;

    @Autowired
    private StudentItemHelper studentItemHelper;

    @Autowired
    private ActivityHelper activityHelper;

    @Autowired
    private StudentSessionHelper studentSessionHelper;

    @Autowired
    private ItemHelper itemHelper;

    @Autowired
    private LearnosityScoresService learnosityScoresService;

    @Autowired
    private StudentSessionDetailService studentSessionDetailService;

    @Override
    public SaveStudentSessionResponse getOrCreateStudentSession(SaveStudentSessionRequest request){


        SaveStudentSessionResponse studentSessionResponse = getSaveStudentSessionResponse(request);
        try{

            //FIXME: We need to create Entity Persistent object in below order as 3 groups.
            // 1.Item , Question , Score.
            // 2.Actitity and ActivityItemScores
            // 3.All student related studentsession,studentitem,studentquestion and studentscore.

            List<ItemsView> itemsView = getItems(request);
            itemEntityRepository.save(itemHelper.createorUpdateItemEntities(itemsView));
            itemEntityRepository.flush();

            ActivityEntity activityEntity = activityHelper.getOrCreateActivity(request);
            activityEntityRepository.saveAndFlush(activityEntity);


            StudentSessionEntity studentSessionEntity = studentSessionHelper.getOrCreateStudentSessionEntity(request);
            studentSessionEntity.setActivityEntity(activityEntity);

            List<StudentItemEntity> studentItemEntities = studentItemHelper.getOrCreateStudentItemEntity(request , studentSessionEntity, getStudentItems(request));
            studentSessionEntity.setStudentItems(studentItemEntities);
            studentSessionEntityRepository.save(studentSessionEntity);

            return studentSessionResponse;

        }catch(Exception ex){
           throw   new DataIntegrityViolationException(ex.getMessage() ) ;

        }

    }

    @Override
    public ResponseView createOrUpdateItemReponse(SaveResponseRequest saveResponseRequest) {

        ResponseView responseView = saveResponseRequest.getResponseView();
        UUID sessionId = saveResponseRequest.getSessionId();
        String responseId = saveResponseRequest.getResponseId();
        responseView.setResponseId(responseId);

        ItemsRequestView itemsView = responseView.getItem();
        QuestionsRequestView questionsRequestView = responseView.getQuestion();
        ScoresView scoresView = responseView.getScores();

        if(itemsView != null){
            String itemReference = itemsView.getItemReference();
            ItemEntity itemEntity = itemEntityRepository.findByItemReference(itemReference);
            if(itemEntity == null){
                ItemsView view = new ItemsView();
                BeanUtils.copyProperties(itemsView, view);
                itemEntityRepository.saveAndFlush(itemsViewMapper.convert(view));
            }
        }

        if(questionsRequestView != null){
            String questionReference = questionsRequestView.getQuestionReference();
            QuestionEntity questionEntity = questionEntityRepository.findByQuestionReference(questionReference);
            if(questionEntity == null){
                QuestionsView view = new QuestionsView();
                BeanUtils.copyProperties(questionsRequestView, view);
                questionEntityRepository.saveAndFlush(questionsViewMapper.convert(view));
            }

        }

        if(scoresView != null){
            String scoreReference = scoresView.getScoreReference();
            ScoreEntity scoreEntity = scoreEntityRepository.findByScoreReference(scoreReference);
            if(scoreEntity == null){
                scoreEntityRepository.saveAndFlush(scoreViewMapper.convert(scoresView));
            }

        }

        StudentSessionEntity studentSessionEntity = studentSessionEntityRepository.findBySessionRefId(sessionId);

        if(studentSessionEntity == null){
            throw new EntityNotFoundException("Student Session Entity not found for Session --> "+sessionId);
        }

        StudentAssignmentStatus studentAssignmentStatus = studentSessionEntity.getStatus();

        if(studentAssignmentStatus != null && !studentAssignmentStatus.isAcceptScores()){
            throw new IllegalStateException("Grading Will not accept scores for Student Assignemnt Status : "+studentAssignmentStatus.name());
        }

        ActivityEntity activityEntity = studentSessionEntity.getActivityEntity();

        if(activityEntity == null){
            throw new EntityNotFoundException("Activity Entity not found for Session --> "+sessionId );
        }

        StudentScoreEntity studentScoreEntityFromDB = studentScoreEntityRepository.findOne(responseId);

        if(studentScoreEntityFromDB != null) {
            UUID sessionRefid = studentScoreEntityFromDB.getStudentSession().getSessionRefId();
            if(!sessionId.equals(sessionRefid)){
                throw new IllegalArgumentException("ResponseID :"+responseId+" is already associated " +
                    "with Student Session Id :"+sessionRefid+" cannot associate same response.");
            }
        }

        StudentScoreView studentScoreView = new StudentScoreView();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(responseView, studentScoreView);

        StudentScoreEntity studentScoreEntity = studentScoreViewMapper.convert(studentScoreView);
        StudentItemRequestView studentItemView = responseView.getStudentItem();
        StudentQuestionRequestView questionView = responseView.getStudentQuestion();

        if(studentScoreEntityFromDB == null){
            studentScoreEntityFromDB = studentScoreEntity;
            String itemRefernce = studentItemView.getItemReference();
            String questionReference = questionView.getQuestionReference();
            StudentItemEntity studentItemEntity = studentItemEntityRepository.findBySessionRefIdAndItemReference(sessionId, itemRefernce);
            if(studentItemEntity == null){
                throw new EntityNotFoundException("Student Item Entity for SessionId --> "+sessionId+" and Item Reference -->"+itemRefernce);
            }
            studentScoreEntityFromDB.setStudentItems(studentItemEntity);
            StudentQuestionEntity studentQuestionEntity = studentQuestionEntityRepository.findByquestionReferenceAndStudentItems(questionReference , studentItemEntity);
            if(studentQuestionEntity == null){
                throw new EntityNotFoundException("Student Question Entity for SessionId --> "+sessionId+" and Item Reference -->"+itemRefernce
                +" Question Reeference --> "+questionReference);
            }
            studentScoreEntityFromDB.setStudentQuestions(studentQuestionEntity);
        }else{
            StudentQuestionEntity studentQuestionEntity = studentScoreEntityFromDB.getStudentQuestions();
            StudentItemEntity itemEntity = studentScoreEntityFromDB.getStudentItems();
            BeanPropertyUtils.copyPropertiesWithOnlyPopulated(studentScoreEntity, studentScoreEntityFromDB);

            studentScoreEntityFromDB.setStudentQuestions(studentQuestionEntity);
            studentScoreEntityFromDB.setStudentItems(itemEntity);
            //If Cosnumer pass null score we should update it
            studentScoreEntityFromDB.setScore(responseView.getScore());
        }
        studentScoreEntityFromDB.setStudentSession(studentSessionEntity);

        if(questionView != null){
            StudentQuestionView studentQuestionView = new StudentQuestionView();
            BeanPropertyUtils.copyPropertiesWithOnlyPopulated(questionView, studentQuestionView);
            StudentQuestionEntity studentQuestionEntity = studentQuestionViewMapper.convert(studentQuestionView);
            BeanPropertyUtils.copyPropertiesWithOnlyPopulated(studentQuestionEntity,studentScoreEntityFromDB.getStudentQuestions());
        }

        if(studentItemView != null){
            StudentItemView studentItemViewForMapper = new StudentItemView();
            BeanPropertyUtils.copyPropertiesWithOnlyPopulated(studentItemView, studentItemViewForMapper);
            StudentItemEntity studentItemEntity = studentItemViewMapper.convert(studentItemViewForMapper);
            BeanPropertyUtils.copyPropertiesWithOnlyPopulated(studentItemEntity,studentScoreEntityFromDB.getStudentItems());
        }


        studentScoreEntityFromDB = studentScoreEntityRepository.save(studentScoreEntityFromDB);

        StudentItemRequestView studentItemGetView = new StudentItemRequestView();
        BeanUtils.copyProperties(studentItemEntityMapper.convert(studentScoreEntityFromDB.getStudentItems()), studentItemGetView);
        StudentQuestionRequestView studentQuestionGetView = new StudentQuestionRequestView();
        BeanUtils.copyProperties(studentQuestionEntityMapper.convert(studentScoreEntityFromDB.getStudentQuestions()), studentQuestionGetView);
        ResponseView studentScoreGetView = new ResponseView();
        BeanUtils.copyProperties(studentScoreEntityMapper.convert(studentScoreEntityFromDB), studentScoreGetView);
        if(studentScoreGetView.getItem() == null){
            ItemEntity itemEntity =  itemEntityRepository.findByItemReference(studentItemGetView.getItemReference());
            if(itemEntity == null){
                throw new EntityNotFoundException();
            }
            ItemsRequestView itemsRequestView = new ItemsRequestView();
            BeanUtils.copyProperties(itemEntityMapper.convert(itemEntity), itemsRequestView);
            studentScoreGetView.setItem(itemsRequestView);
        }
        if(studentScoreGetView.getQuestion() == null){
            QuestionEntity questionEntity =  questionEntityRepository.findByQuestionReference(studentQuestionGetView.getQuestionReference());
            if(questionEntity == null){
                throw new EntityNotFoundException();
            }
            QuestionsRequestView questionsView = new QuestionsRequestView();
            BeanUtils.copyProperties(questionsEntityMapper.convert(questionEntity), questionsView);
            studentScoreGetView.setQuestion(questionsView);
        }
        studentScoreGetView.setStudentQuestion(studentQuestionGetView);
        studentScoreGetView.setStudentItem(studentItemGetView);

        if(scoresView != null){
            studentScoreGetView.setScores(scoresView);
        }

        createOrUpdateActivityItemScoresForResponse(activityEntity, studentScoreGetView);


        return studentScoreGetView;
    }

    @Override
    public SessionStatusView updateSessionStatus(UUID sessionId, StudentStatusRequest studentStatusRequest) {

        SessionStatusView sessionStatusView = null;

        StudentSessionEntity studentSessionEntity = studentSessionEntityRepository.findBySessionRefId(sessionId);

        if (studentSessionEntity == null) {
            throw new EntityNotFoundException("Student Session Entity not found for Session --> " + sessionId);
        } else {
            studentSessionEntity.setStatus(studentStatusRequest.getStatus());
            StudentSessionEntity saveStudentSessionEntity = studentSessionEntityRepository.save(studentSessionEntity);
            sessionStatusView = new SessionStatusView();
            sessionStatusView.setSessionId(saveStudentSessionEntity.getSessionRefId());
            sessionStatusView.setStatus(saveStudentSessionEntity.getStatus());
            sessionStatusView.setDateStarted(saveStudentSessionEntity.getDateStarted());
            sessionStatusView.setDateCompleted(saveStudentSessionEntity.getDateCompleted());
            sessionStatusView.setNumAttempted(saveStudentSessionEntity.getNumAttempted());
            sessionStatusView.setSessionDuration(saveStudentSessionEntity.getSessionDuration());
            sessionStatusView.setScore(saveStudentSessionEntity.getScore());
            sessionStatusView.setStudentPersonalRefId(saveStudentSessionEntity.getStudentPersonalRefId());
        }

        return sessionStatusView;
    }

    @Override
    public ScoringCompleteResponse scoringCompleted(UUID sessionId){

        ScoringCompleteResponse scoringCompleteResponse = new ScoringCompleteResponse();

        try {

            StudentSessionEntity studentSessionEntity = studentSessionHelper.findBySessionRefId(sessionId);

            List<ActivityStudentItemViewEntity>  itemViewEntities = activityStudentItemViewRepository.findBySessionId(sessionId);


            Boolean isScoringCompleted = studentSessionHelper.checkForScoringCompletedAndUpdateStatus(sessionId,
                studentSessionEntity, scoringCompleteResponse, itemViewEntities);

            if (!isScoringCompleted) {
                scoringCompleteResponse.setIncompleteItemReferences(studentItemHelper.getItemReferencesNotScored(
                    scoringCompleteResponse.getStudentSession().getItems()));
            }


            if(isScoringCompleted && studentSessionEntity.getDatePushedToScoring() == null) {
               KinesisPutRecordResult putRecordResult = learnosityScoresService.createLearnosityStudentSession(studentSessionEntity
                   ,itemViewEntities, Boolean.TRUE);
               String sequenceNumber = putRecordResult.getSequenceNumber();
               studentSessionEntity.setScoringStreamSeq(sequenceNumber);
               studentSessionEntity.setDatePushedToScoring(LocalDateTime.now());
                KinesisPutRecordResult putRecordResultAssignmentStatus = studentSessionDetailService.pushStatusToAssignmentService(
                    studentSessionEntity, Boolean.TRUE);
                studentSessionEntity.setStatusStreamSeq(putRecordResultAssignmentStatus.getSequenceNumber());
                studentSessionEntity.setDatePushedToStatus(LocalDateTime.now());
               studentSessionEntityRepository.saveAndFlush(studentSessionEntity);
            }

        }catch (RuntimeException ex){
            ex.printStackTrace();
        }


        return scoringCompleteResponse;
    }

    @Override
    public StudentItemGetView getStudentItems(UUID sessionID, String itemReference) {

        ActivityStudentItemViewEntity activityStudentItemView = activityStudentItemViewRepository.findByItemReferenceAndSessionId(
            itemReference, sessionID);

        if(activityStudentItemView == null)
            throw new EntityNotFoundException();

        return activityStudentItemMapper.convert(activityStudentItemView);

    }

    @Override
    public ActivityView getActivityDetails(UUID activityRefId){

        if(activityRefId == null){
            throw new EntityNotFoundException("Activity Ref Id should not be null.");
        }

        ActivityEntity activityEntity = activityEntityRepository.findOne(activityRefId);

        ActivityView activityView = activityEntityMapper.convert(activityEntity);

        List<ActivityItemScoreEntity> activityItemScoreEntities = activityEntity.getActivityItemScores();

        if(!CollectionUtils.isEmpty(activityItemScoreEntities)){
            activityView.setActivityItemScores(activityItemScoreMapper.convert(activityItemScoreEntities));
        }

        return activityView;
    }

    @Override
    public void pushScoresToScoring(UUID sessionId){
        if(sessionId == null) {
            throw new IllegalArgumentException("Session Id cannot be null ..");
        }
        try {

             StudentSessionEntity studentSessionEntity = studentSessionHelper.findBySessionRefId(sessionId);

             List<ActivityStudentItemViewEntity>  itemViewEntities = activityStudentItemViewRepository.findBySessionId(sessionId);
             KinesisPutRecordResult putRecordResult = learnosityScoresService.createLearnosityStudentSession(studentSessionEntity
                 , itemViewEntities, Boolean.TRUE);
             String sequenceNumber = putRecordResult.getSequenceNumber();
             studentSessionEntity.setScoringStreamSeq(sequenceNumber);
             studentSessionEntity.setDatePushedToScoring(LocalDateTime.now());
             studentSessionEntityRepository.saveAndFlush(studentSessionEntity);

             logger.info("Session Id {} , Queue Sequence Number {} ,  Shrad ID {}",sessionId , sequenceNumber
                 ,putRecordResult.getShardId());

        }catch (RuntimeException ex){
             ex.printStackTrace();
        }

    }

    @Override
    public void pushStatusToAssignment(UUID sessionId) {
        if(sessionId == null) {
            throw new IllegalArgumentException("Session Id cannot be null ..");
        }
        try {

            StudentSessionEntity studentSessionEntity = studentSessionHelper.findBySessionRefId(sessionId);
            KinesisPutRecordResult putRecordResult = studentSessionDetailService.pushStatusToAssignmentService(
                studentSessionEntity , Boolean.TRUE);
            logger.info("Session Id {} , Queue Sequence Number {} ,  Shrad ID {}", sessionId, putRecordResult.getSequenceNumber()
                , putRecordResult.getShardId());
            studentSessionEntity.setStatusStreamSeq(putRecordResult.getSequenceNumber());
            studentSessionEntity.setDatePushedToStatus(LocalDateTime.now());
            studentSessionEntityRepository.saveAndFlush(studentSessionEntity);
        }catch (RuntimeException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Page<StudentItemGetView> getStudentActivityItems(Pageable pageable, UUID sessionId, boolean onlyManualScored, boolean includeQuestionsAndScores) {
        Page<ActivityStudentItemViewEntity> activityStudentItemViewEntityPage = null;

        if (onlyManualScored) {
          activityStudentItemViewEntityPage = activityStudentItemViewRepository.findBySessionIdAndAutomarkable(sessionId, !onlyManualScored, pageable);
        } else {
          activityStudentItemViewEntityPage = activityStudentItemViewRepository.findBySessionId(sessionId, pageable);
        }

        if (activityStudentItemViewEntityPage == null)
          throw new NotFoundException();

        activityStudentItemMapper.setExcludeQuestionsAndScores(!includeQuestionsAndScores);
        return new PageImpl<>(activityStudentItemMapper.convert(activityStudentItemViewEntityPage.getContent()),
            pageable, activityStudentItemViewEntityPage.getTotalElements());
    }

    private void createOrUpdateActivityItemScoresForResponse(ActivityEntity activityEntity , ResponseView responseView){

        StudentItemRequestView studentItemView = responseView.getStudentItem();
        StudentQuestionRequestView questionView = responseView.getStudentQuestion();
        String scoreReference = responseView.getScoreReference();
        String itemRefernce = studentItemView.getItemReference();
        String questioReference = questionView.getQuestionReference();
        ActivityItemScoreView activityItemScoreView = new ActivityItemScoreView();
        activityItemScoreView.setScoreReference(scoreReference);
        activityItemScoreView.setQuestionReference(questioReference);
        activityItemScoreView.setItemReference(itemRefernce);
        activityItemScoreView.setMaxScore(responseView.getMaxScore());
        Integer weight = responseView.getWeight();
        weight = (weight == null || weight == 0)?responseView.getMaxScore():weight;
        activityItemScoreView.setWeight(weight);
        List<ActivityItemScoreView> activityItemScoreViews = new ArrayList<>();
        activityItemScoreViews.add(activityItemScoreView);
        //List<ActivityItemScoreEntity> activityItemScoreEntities = getOrCreateActivityItemScore(activityEntity, activityItemScoreViews);
        activityEntity.setActivityItemScores(activityItemScoreHelper.getOrCreateActivityItemScore(activityEntity, activityItemScoreViews));

        activityEntityRepository.save(activityEntity);
    }

    private List<ItemsView> getItems(SaveStudentSessionRequest request) {
        if(StudentSessionRequestType.SCORES == request.getRequestType()){
            return request.getStudentSessionView().getItems();
        }
        return request.getStudentActivitySessionView().getItems();
    }

    private List<StudentItemView> getStudentItems(SaveStudentSessionRequest request) {

        if(StudentSessionRequestType.SCORES == request.getRequestType()){
            return request.getStudentSessionView().getStudentItems();
        }

        return request.getStudentActivitySessionView().getStudentItems();
    }

  private SaveStudentSessionResponse getSaveStudentSessionResponse(SaveStudentSessionRequest request) {

        SaveStudentSessionResponse saveStudentSessionResponse;

        if(StudentSessionRequestType.INIT == request.getRequestType())
            saveStudentSessionResponse = new SaveStudentSessionResponse(request.getStudentActivitySessionView());

        else if(StudentSessionRequestType.SCORES == request.getRequestType())
            saveStudentSessionResponse = new SaveStudentSessionResponse(request.getStudentSessionView());
        else
            throw new BadRequestException();
        return  saveStudentSessionResponse;
    }

	@Override
	public void setSessionLevelScores(ScoringCompleteResponse scoringCompleteResponse) {
		Integer totalMaxScore = 0;
		Integer totalScore = 0;
		StudentSessionGetView studentSessionView = scoringCompleteResponse.getStudentSession();
		ActivityView activityView = studentSessionView.getAssignment();
		List<StudentItemGetView> studentItemViews = studentSessionView.getItems();
		for (StudentItemGetView studentItemGetView : studentItemViews) {
			Integer maxScore = studentItemGetView.getMaxScore();
			Integer score = studentItemGetView.getScore();
			totalMaxScore += maxScore;
			totalScore += score;
		}
		studentSessionView.setScore(totalScore);
		activityView.setMaxScore(totalMaxScore);
	}

}




