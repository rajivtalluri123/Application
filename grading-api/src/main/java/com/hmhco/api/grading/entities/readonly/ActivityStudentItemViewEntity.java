package com.hmhco.api.grading.entities.readonly;

import com.hmhco.api.grading.entities.AbstractEntity;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.List;
import java.util.UUID;

/**
 * Created by srikanthk on 5/12/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="activitystudentitem_view")
@Immutable
public class ActivityStudentItemViewEntity extends AbstractEntity {


    private static final long serialVersionUID = 898558825365004543L;

    @Id
    @Column(name="refId")
    private Long refId;

    @Column(name="itemReference")
    private String itemReference;

    @Column(name="time")
    private Integer time;

    @Column(name="maxScore")
    private Integer maxScore;


    @Column(name="userFlagged")
    private Boolean userFlagged;

    @Column(name="organisationId")
    private Integer organizationId;

    @Column(name="itemPoolId")
    private String itemPoolId;

    @Column(name="type")
    private String type;

    @Column(name="sessionId")
    private UUID sessionId;

    @Column(name="automarkable")
    private Boolean automarkable;

    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "studentItemRefId")
    private List<ActivityStudentQuestionViewEntity> questions;



}