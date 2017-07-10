package com.hmhco.api.grading.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Created by srikanthk on 4/25/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="student_score")
public class StudentScoreEntity extends AuditableEntity {


    private static final long serialVersionUID = -7013874303858039495L;

    @Id
    @Column(name="response_id")
    private String responseId;

    @Column(name="staff_personal_refid")
    private UUID staffPersonalRefId;

    @Column(name="attemted")
    private Boolean attempted;

    @Column(name="value")
    private String value;

    @Column(name="score_reference")
    private String scoreReference;

    @Column(name="weight")
    private Integer weight;

    @Column(name="max_score")
    private Integer  maxScore;

    @Column(name="Score")
    private Integer score;

    @ManyToOne(optional = false, fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="student_question_refid", referencedColumnName = "refId")
    private StudentQuestionEntity studentQuestions;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="student_item_refid")
    @NotFound(action = NotFoundAction.IGNORE)
    private StudentItemEntity studentItems;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="session_refid")
    @NotFound(action = NotFoundAction.IGNORE)
    private StudentSessionEntity studentSession;

    @Override
    public String toString() {
        return "StudentScoreEntity{" +
            "responseId='" + responseId + '\'' +
            ", staffPersonalRefId=" + staffPersonalRefId +
            ", attempted=" + attempted +
            ", value='" + value + '\'' +
            ", scoreReference='" + scoreReference + '\'' +
            ", weight=" + weight +
            ", maxScore=" + maxScore +
            ", score=" + score +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentScoreEntity that = (StudentScoreEntity) o;

        return !(responseId != null ? !responseId.equals(that.responseId) : that.responseId != null);
    }

    @Override
    public int hashCode() {
        int result = responseId != null ? responseId.hashCode() : 0;
        return result;
    }
}
