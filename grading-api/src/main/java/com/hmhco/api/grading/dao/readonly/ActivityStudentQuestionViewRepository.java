package com.hmhco.api.grading.dao.readonly;

import com.hmhco.api.grading.entities.readonly.ActivityStudentQuestionViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by srikanthk on 5/12/17.
 */
public interface ActivityStudentQuestionViewRepository extends JpaRepository<ActivityStudentQuestionViewEntity, Long> {



}