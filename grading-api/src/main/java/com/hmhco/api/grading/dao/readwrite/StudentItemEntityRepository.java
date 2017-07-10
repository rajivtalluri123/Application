package com.hmhco.api.grading.dao.readwrite;

import com.hmhco.api.grading.entities.StudentItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface StudentItemEntityRepository extends JpaRepository<StudentItemEntity, Long> {

   StudentItemEntity findBySessionRefIdAndItemReference(UUID sessionId , String itemReference);
 }
