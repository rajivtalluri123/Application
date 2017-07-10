package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.ScoreEntity;
import com.hmhco.api.grading.mapper.EntityMapper;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.ScoresView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/28/17.
 */
@Component
public class ScoreEntityMapper  implements SingleEntityMapper<ScoreEntity, ScoresView> {


    @Autowired
    @Qualifier("genericEntityMapper")
    private EntityMapper entityMapper;


    @Override
    public ScoresView convert( ScoreEntity entity){
     ScoresView scoreView = new ScoresView();
        BeanUtils.copyProperties(scoreView,entity);
        return scoreView;

}


    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz){
        return ScoreEntity.class.equals(clazz);


    }



}
