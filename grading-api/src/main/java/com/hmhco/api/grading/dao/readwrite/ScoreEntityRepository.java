package com.hmhco.api.grading.dao.readwrite;


import com.hmhco.api.grading.entities.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface ScoreEntityRepository extends JpaRepository<ScoreEntity, String> {

    ScoreEntity findByScoreReference(String scoreReference);

    List<ScoreEntity> findByScoreReferenceIn(List<String> scoreReferences);
}
