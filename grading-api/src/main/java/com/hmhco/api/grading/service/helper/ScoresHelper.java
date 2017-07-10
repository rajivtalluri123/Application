package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.dao.readwrite.ScoreEntityRepository;
import com.hmhco.api.grading.entities.ScoreEntity;
import com.hmhco.api.grading.mapper.viewmapper.ScoreViewMapper;
import com.hmhco.api.grading.views.ScoresView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class ScoresHelper {

  @Autowired
  private ScoreViewMapper scoreViewMapper;

  @Autowired
  private ScoreEntityRepository scoreEntityRepository;

  public List<ScoreEntity> createorUpdateScores(List<ScoresView> scoreEntitiesEntitiesView){

    List<ScoreEntity> scoreEntityList = scoreViewMapper.convert(scoreEntitiesEntitiesView);
    List<String> scoreReferences = new ArrayList<>();
    List<ScoreEntity> scoreEntitiesDB ;


    scoreEntitiesEntitiesView.stream().forEach(view->{
      scoreReferences.add(view.getScoreReference());
    });

    scoreEntitiesDB = scoreEntityRepository.findByScoreReferenceIn(scoreReferences);

    if(scoreEntityList.size() == 0){
      return scoreEntitiesDB;
    }

    Set<ScoreEntity> scoreEntities = new HashSet<>(scoreEntityList);

    List<ScoreEntity> newEntries = ListUtils.subtract(new ArrayList<>(scoreEntities), scoreEntitiesDB);

    scoreEntitiesDB.stream().forEach(score -> {
      int indexOfScore = scoreEntityList.indexOf(score);
      LocalDateTime createDate = score.getCreatedDate();
      ScoreEntity scoreEntity = null;
      if (indexOfScore != -1) {
        scoreEntity = scoreEntityList.get(indexOfScore);
      }
      if (scoreEntity != null) {

        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(scoreEntity, score);
        score.setScoreReference(score.getScoreReference());
        if(createDate != null){
          score.setCreatedDate(LocalDateTime.now());
        }

      }

    });

    if(!CollectionUtils.isEmpty(newEntries)){
      Set<ScoreEntity> scoreEntitySet = new HashSet<>(newEntries);
      scoreEntitiesDB.addAll(scoreEntitySet);
    }

    return scoreEntitiesDB;
  }

}
