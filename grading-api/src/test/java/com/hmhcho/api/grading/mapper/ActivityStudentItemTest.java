package com.hmhcho.api.grading.mapper;

import com.hmhcho.api.grading.testutils.ActivityStudentItemViewBuilder;
import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;
import com.hmhco.api.grading.mapper.entitymapper.ActivityStudentItemMapper;
import com.hmhco.api.grading.service.StudentSessionServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by srikanthk on 5/16/17.
 */

public class ActivityStudentItemTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Autowired
    private ActivityStudentItemMapper activityStudentItem  = new ActivityStudentItemMapper();


    private ActivityStudentItemViewEntity activityStudentItemView;


    @InjectMocks
    private StudentSessionServiceImpl studentSessionService = new StudentSessionServiceImpl();


    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void studentItemViewConverterTest(){


        ActivityStudentItemViewBuilder activityStudentItemViewBuilder = new ActivityStudentItemViewBuilder();

        activityStudentItemView = activityStudentItemViewBuilder.getActivityStudentItemViewList(2,2);




    }


}
