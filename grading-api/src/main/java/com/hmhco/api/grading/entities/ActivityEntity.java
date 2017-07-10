package com.hmhco.api.grading.entities;

import javax.persistence.FetchType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
/**
 * Created by srikanthk on 4/25/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="activity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ActivityEntity extends AuditableEntity{


    private static final long serialVersionUID = -2955871271092163289L;

    @Id
    @Column(name="activity_refid")
    private UUID activityRefId;

    @Column(name="teacher_assignment_refid")
    private UUID teacherAssignmentRefId;

    @Column(name="staff_personal_refid")
    private UUID staffPersonalRefId;

    @Column(name="num_questions")
    private Integer numQuestions;

    @Column(name="max_score")
    private Integer maxScore;

    @Column(name="max_time")
    private Integer maxTime;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "activityEntity" , fetch = FetchType.LAZY)
    private List<StudentSessionEntity> studentSession;


    @OneToMany(cascade= CascadeType.ALL,  mappedBy ="activityEntity" , fetch = FetchType.LAZY)
    private List<ActivityItemScoreEntity> activityItemScores;


    @Override
    public String toString() {
        return "ActivityEntity [activityRefId=" + activityRefId + ", teacherAssignmentRefId=" + teacherAssignmentRefId
                + ", staffPersonalRefId=" + staffPersonalRefId + ", numQuestions=" + numQuestions + ", maxScore="
                + maxScore + ", maxTime=" + maxTime + "activityItemScores"+ activityItemScores+ "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityEntity)) return false;
        if (!super.equals(o)) return false;

        ActivityEntity that = (ActivityEntity) o;

        return activityRefId.equals(that.activityRefId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + activityRefId.hashCode();
        return result;
    }
}
