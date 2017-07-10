package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.entities.ActivityEntity;
import com.hmhco.api.grading.entities.ActivityItemScoreEntity;
import com.hmhco.api.grading.mapper.viewmapper.ActivityItemScoreViewMapper;
import com.hmhco.api.grading.views.ActivityItemScoreView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import java.util.List;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class ActivityItemScoreHelper {

  @Autowired
  private ActivityItemScoreViewMapper activityItemScoreViewMapper;

  public List<ActivityItemScoreEntity> getOrCreateActivityItemScore(ActivityEntity activityEntity, List<ActivityItemScoreView> activityItemScores){

    if(activityItemScores == null){
      return activityEntity.getActivityItemScores();
    }

    List<ActivityItemScoreEntity> activityItemScoreEntitiesDB = activityEntity.getActivityItemScores();
    List<ActivityItemScoreEntity> activityItemScoreEntities = activityItemScoreViewMapper.convert(activityItemScores);

    if(!CollectionUtils.isEmpty(activityItemScoreEntities)) {
      activityItemScoreEntities.stream().forEach(activityItemScoreEntity -> {
        activityItemScoreEntity.setActivityEntity(activityEntity);
        activityItemScoreEntity.setActivityRefId(activityEntity.getActivityRefId());
      });
    }

    if(CollectionUtils.isEmpty(activityItemScoreEntitiesDB)){
      return activityItemScoreEntities;
    }
    List<ActivityItemScoreEntity> newEntries = null;
    if(!CollectionUtils.isEmpty(activityItemScoreEntitiesDB)) {

      activityItemScoreEntitiesDB.forEach(activityItemScoreEntity -> {
        Long refId = activityItemScoreEntity.getRefId();
        activityItemScoreEntity.setActivityRefId(activityEntity.getActivityRefId());
        int indexOfScore = activityItemScoreEntities.indexOf(activityItemScoreEntity);
        ActivityItemScoreEntity activityItemScoreEntityFromView = null;
        if (indexOfScore != -1) {
          activityItemScoreEntityFromView = activityItemScoreEntities.get(indexOfScore);
        }
        if (activityItemScoreEntityFromView != null) {
          BeanPropertyUtils.copyPropertiesWithOnlyPopulated(activityItemScoreEntityFromView, activityItemScoreEntity);
          activityItemScoreEntity.setActivityEntity(activityEntity);
          activityItemScoreEntity.setRefId(refId);
          activityItemScoreEntity.setActivityRefId(activityEntity.getActivityRefId());
        }

      });
      newEntries = ListUtils.subtract(activityItemScoreEntities, activityItemScoreEntitiesDB);
      activityItemScoreEntitiesDB.addAll(newEntries);
    }

    return activityItemScoreEntitiesDB;

  }

}
