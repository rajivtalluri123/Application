package com.hmhcho.api.grading.entities;

import com.hmhcho.api.grading.testutils.GenericBeanCoverage;
import com.hmhco.api.grading.entities.ItemEntity;
import org.junit.Test;

/**
 * Created by srikanthk on 4/28/17.
 */
public class ItemEntityTest {


    @Test
    public void testBean() throws Exception {

        GenericBeanCoverage.doBeanCodeCoverage(ItemEntity.class);

    }
}