package com.hmhco.api.grading.entities;

import com.hmhco.api.grading.entities.convertors.StudentEventStatusConvertor;
import io.hmheng.grading.utils.StudentAssignmentStatus;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.FetchType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Created by srikanthk on 4/25/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="student_session")
public class StudentSessionEntity extends AuditableEntity {


    private static final long serialVersionUID = -2670482815921079433L;

    @Id
    @Column(name="session_refid")
    private UUID sessionRefId;

    @Column(name ="activity_refid", insertable = false, updatable = false)
    private UUID activityRefId;

    @Column(name="student_personal_refid")
    private UUID studentPersonalRefId;

    @Column(name="num_attempted")
    private Integer numAttempted;

    @Column(name="score")
    private Integer score;

    @Column(name="session_duration")
    private Integer sessionDuration;

    @Column(name="status")
    @Convert(converter = StudentEventStatusConvertor.class)
    private StudentAssignmentStatus status;

    @Column(name="dt_started")
    private LocalDateTime dateStarted;

    @Column(name="user_agent")
    private String userAgent;

    @Column(name="dt_completed")
    private LocalDateTime dateCompleted;

    @Column(name="SCORING_STREAM_SEQ")
    private String scoringStreamSeq;

    @Column(name="SCORING_PUSH_DT")
    private LocalDateTime datePushedToScoring;

    @Column(name="STATUS_STREAM_SEQ")
    private String statusStreamSeq;

    @Column(name="STATUS_PUSH_DT")
    private LocalDateTime datePushedToStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="activity_refid")
    private ActivityEntity activityEntity;


    @OneToMany( mappedBy ="studentSession" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<StudentItemEntity> studentItems;


    @OneToMany( mappedBy ="studentSession" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<StudentScoreEntity> studentScores;

    @Override
    public String toString() {
        return "StudentSessionEntity{" +
            "sessionRefId=" + sessionRefId +
            ", activityRefId=" + activityRefId +
            ", studentPersonalRefId=" + studentPersonalRefId +
            ", numAttempted=" + numAttempted +
            ", score=" + score +
            ", sessionDuration=" + sessionDuration +
            ", status='" + status + '\'' +
            ", dateStarted=" + dateStarted +
            ", dateCompleted=" + dateCompleted +
            ", userAgent='" + userAgent + '\'' +
            ", scoringStreamSeq='" + scoringStreamSeq + '\'' +
            ", datePushedToScoring='" + datePushedToScoring + '\'' +
            ", statusStreamSeq='" + statusStreamSeq + '\'' +
            ", datePushedToStatus='" + datePushedToStatus + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentSessionEntity)) return false;
        if (!super.equals(o)) return false;

        StudentSessionEntity that = (StudentSessionEntity) o;

        return sessionRefId.equals(that.sessionRefId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + sessionRefId.hashCode();
        return result;
    }
}
