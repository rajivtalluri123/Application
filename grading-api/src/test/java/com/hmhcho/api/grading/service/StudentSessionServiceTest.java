package com.hmhcho.api.grading.service;

import com.hmhcho.api.grading.testutils.ItemViewBuilder;
import com.hmhco.api.grading.dao.readwrite.*;
import com.hmhco.api.grading.entities.ItemEntity;
import com.hmhco.api.grading.entities.QuestionEntity;
import com.hmhco.api.grading.entities.ScoreEntity;
import com.hmhco.api.grading.mapper.viewmapper.ItemsViewMapper;
import com.hmhco.api.grading.service.StudentSessionService;
import com.hmhco.api.grading.service.StudentSessionServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by srikanthk on 5/2/17.
 */
public class StudentSessionServiceTest {


    @Rule
    public MockitoRule mocitoRule = MockitoJUnit.rule();

    @Mock
    private StudentSessionEntityRepository studentSessionEntityRepository;

    @Mock
    private ActivityEntityRepository activityEntityRepository;

    @Mock
    private ActivityItemScoreEntityRepository activityItemScoreEntityRepository;

    @Mock
    private ItemEntityRepository itemEntityRepository;

    @Mock
    private QuestionEntityRepository questionEntityRepository;

    @Mock
    private ScoreEntityRepository scoreEntityRepository;

    Map<String, ItemEntity> itemsMap = new HashMap<String, ItemEntity>();

    Map<String, ScoreEntity> scoresMap = new HashMap<String, ScoreEntity>();

    Map<String, QuestionEntity> questionsMap = new HashMap<String, QuestionEntity>();

    @Mock
    private StudentItemEntityRepository studentItemEntityRepository;

    @Mock
    private StudentQuestionEntityRepository studentQuestionEntityRepository;

    @Mock
    private  StudentScoreEntityRepository studentScoreEntityRepository;

    @Mock
    private ItemsViewMapper itemsViewMapper;

    @InjectMocks
    private StudentSessionService studentSessionService = new StudentSessionServiceImpl();

    @InjectMocks
    private StudentSessionServiceImpl studentSessionServiceImpl = new StudentSessionServiceImpl();

    @Test
    public void createorUpdateItemEntitiesTest(){


        ItemViewBuilder itemViewBuilder = new ItemViewBuilder();

        itemViewBuilder.buildviewList(2);

    }



}
