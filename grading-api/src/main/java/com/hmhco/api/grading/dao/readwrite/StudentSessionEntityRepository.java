package com.hmhco.api.grading.dao.readwrite;

import com.hmhco.api.grading.entities.StudentSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface StudentSessionEntityRepository extends JpaRepository<StudentSessionEntity, UUID> {


    StudentSessionEntity findBySessionRefId(UUID sessionRefId);
}
