package com.hmhco.api.grading.dao.readwrite;

import com.hmhco.api.grading.entities.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface ActivityEntityRepository extends JpaRepository<ActivityEntity,UUID> {

}