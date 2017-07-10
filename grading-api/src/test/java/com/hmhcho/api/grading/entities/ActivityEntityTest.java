package com.hmhcho.api.grading.entities;

import com.hmhcho.api.grading.testutils.GenericBeanCoverage;
import com.hmhco.api.grading.entities.ActivityEntity;
import org.junit.Test;

/**
 * Created by srikanthk on 4/27/17.
 */
public class ActivityEntityTest {

        @Test
        public void testBean ()throws Exception{

        GenericBeanCoverage.doBeanCodeCoverage(ActivityEntity.class);

    }


}
