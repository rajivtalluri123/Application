package com.hmhco.api.grading.mapper.viewmapper;

import com.hmhco.api.grading.entities.ScoreEntity;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.ScoresView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/30/17.
 */
@Component
public class ScoreViewMapper implements SingleViewMapper<ScoresView, ScoreEntity> {

    @Override
    public ScoreEntity convert(ScoresView view) {
        ScoreEntity scoreEntity = new ScoreEntity();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, scoreEntity);
        return scoreEntity;
    }

    @Override
    public boolean supports(Class<? extends AbstractView> clazz) {
        return ScoresView.class.equals(clazz);
    }
}
