package com.hmhco.api.grading.entities.readonly;

import com.hmhco.api.grading.entities.AbstractEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by srikanthk on 5/12/17.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name="activitystudentquestion_view")
public class ActivityStudentQuestionViewEntity extends AbstractEntity {


    private static final long serialVersionUID = 6832272060991970828L;

    @Id
    @Column(name="refId")
    private Long refId;

    @Column(name="studentItemRefId")
    private Long studentItemRefId;

    @Column(name="questionReference")
    private  String questionReference;

    @Column(name="actualResponse")
    private String actualResponse;
    
    @Lob
    @Column(name="binaryResponse")
    private String binaryResponse;

    @Column(name="rubricReference")
    private String rubricReference;

    @Column(name="questionType")
    private String questionType;

    @Column(name="automarkable")
    private boolean automarkable;

    @Column(name="responseId")
    private String responseId;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "studentQuestionRefID")
    private List<ActivityStudentScoreViewEntity> responses;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studentItemRefId", insertable = false, updatable = false)
    private ActivityStudentItemViewEntity activityStudentItemView;



}