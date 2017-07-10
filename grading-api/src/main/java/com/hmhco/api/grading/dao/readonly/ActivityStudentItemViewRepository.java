package com.hmhco.api.grading.dao.readonly;

import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by srikanthk on 5/12/17.
 */
public interface ActivityStudentItemViewRepository  extends JpaRepository<ActivityStudentItemViewEntity, Long> {

    ActivityStudentItemViewEntity findByItemReferenceAndSessionId(String itemReference, UUID sessionId);

    List<ActivityStudentItemViewEntity> findBySessionId(UUID sessionId);

    Page<ActivityStudentItemViewEntity> findBySessionIdAndAutomarkable(UUID sessionId, Boolean automarkable, Pageable pageable);

    Page<ActivityStudentItemViewEntity> findBySessionId(UUID sessionId, Pageable pageable);

}

