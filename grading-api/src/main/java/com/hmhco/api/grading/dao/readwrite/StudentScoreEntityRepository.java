package com.hmhco.api.grading.dao.readwrite;

import com.hmhco.api.grading.entities.StudentScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface StudentScoreEntityRepository extends JpaRepository<StudentScoreEntity, String> {


}
