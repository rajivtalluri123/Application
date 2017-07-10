package com.hmhcho.api.grading.entities;

import com.hmhcho.api.grading.testutils.GenericBeanCoverage;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import org.junit.Test;

/**
 * Created by srikanthk on 4/28/17.
 */
public class StudentScoreEntityTest {

    @Test
    public void testBean() throws Exception {

        GenericBeanCoverage.doBeanCodeCoverage(StudentScoreEntity.class);

    }
}
