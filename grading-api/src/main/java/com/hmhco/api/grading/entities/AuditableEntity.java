package com.hmhco.api.grading.entities;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * Created by srikanthk on 4/25/17.
 */


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AuditableEntity extends AbstractEntity {

    private static final long serialVersionUID = 7534381602406417288L;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedDate;
}
