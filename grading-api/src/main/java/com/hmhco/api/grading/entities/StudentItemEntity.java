package com.hmhco.api.grading.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;


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
@Table(name="student_item")
public class StudentItemEntity  extends AuditableEntity{

    private static final long serialVersionUID = 895959986536766060L;

    @Id
    @Column(name="refid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refId;

    @Column(name="session_refid", insertable = false, updatable = false)
    private UUID sessionRefId;

    @Column(name="time")
    private Integer time;

    @Column(name="max_score")
    private int maxScore;

    @Column(name="user_flagged")
    private boolean userFlagged;

    @Column(name="item_reference")
    private String itemReference;

    @OneToMany( mappedBy = "studentItems"  , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<StudentQuestionEntity> studentQuestions;

    @OneToMany( mappedBy ="studentItems"   , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<StudentScoreEntity> studentScores;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="session_refid")
    private StudentSessionEntity studentSession;

    @Override
    public String toString() {
        return "StudentItemEntity{" +
            "refId=" + refId +
            ", sessionRefId=" + sessionRefId +
            ", time=" + time +
            ", maxScore=" + maxScore +
            ", userFlagged=" + userFlagged +
            ", itemReference='" + itemReference + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentItemEntity that = (StudentItemEntity) o;

        if (sessionRefId != null ? !sessionRefId.equals(that.sessionRefId) : that.sessionRefId != null) return false;
        return !(itemReference != null ? !itemReference.equals(that.itemReference) : that.itemReference != null);
    }

    @Override
    public int hashCode() {
        int result = sessionRefId != null ? sessionRefId.hashCode() : 0;
        result = 31 * result + (itemReference != null ? itemReference.hashCode() : 0);
        return result;
    }
}
