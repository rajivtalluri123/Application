package com.hmhco.api.grading.dao.readwrite;

import com.hmhco.api.grading.entities.ActivityItemScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface ActivityItemScoreEntityRepository extends JpaRepository<ActivityItemScoreEntity, Long>{




    List<ActivityItemScoreEntity> findByActivityRefId(UUID activityRefId);
}
