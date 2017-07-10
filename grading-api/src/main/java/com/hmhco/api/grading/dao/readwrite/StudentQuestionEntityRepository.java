package com.hmhco.api.grading.dao.readwrite;


import com.hmhco.api.grading.entities.StudentItemEntity;
import com.hmhco.api.grading.entities.StudentQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface StudentQuestionEntityRepository extends JpaRepository<StudentQuestionEntity, Long> {

  StudentQuestionEntity findByquestionReferenceAndStudentItems(String questionReference , StudentItemEntity studentItemEntity);

}
