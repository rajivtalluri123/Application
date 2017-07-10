package com.hmhco.api.grading.entities.readonly;


import com.hmhco.api.grading.entities.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by srikanthk on 5/12/17.
 */


@Data
@NoArgsConstructor
@Entity
@Table(name="activitystudentscore_view")
public class ActivityStudentScoreViewEntity extends AbstractEntity {


    private static final long serialVersionUID = -6814318927536595995L;

    @Id
    @Column(name="responseId")
    private String responseId;

    @Column(name="scoreReference")
    private String scoreReference;

    @Column(name="staffPersonalRefId")
    private UUID staffPersonalRefId;

    @Column(name="studentQuestionRefId")
    private Long studentQuestionRefID;

    @Column(name="attempted")
    private Boolean attempted;

    @Column(name = "value")
    private String value;

    @Column(name="weight")
    private Integer weight;

    @Column(name="maxScore")
    private Integer maxScore;


    @Column(name="Score")
    private Integer score;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="automarkable")
    private boolean automarkable;

    @Column(name="correctResponse")
    private String correctResponse;

    @Column(name="score_type")
    private String scoreType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studentQuestionRefId", insertable = false, updatable = false)
    private ActivityStudentQuestionViewEntity activityStudentQuestionView;


}