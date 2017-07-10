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
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



/**
 * Created by srikanthk on 4/25/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="student_question")
public class StudentQuestionEntity extends AuditableEntity {


    private static final long serialVersionUID = -2343650976853515712L;

    @Id
    @Column(name="refid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refId;

    @Column(name="question_reference")
    private String questionReference;

    @Column(name="actual_response")
    private String actualResponse;

    @Column(name="response_id")
    private String responseId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="student_item_refid", referencedColumnName = "refId")
    private StudentItemEntity studentItems;

    @OneToMany( mappedBy ="studentQuestions"  ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<StudentScoreEntity> studentScores;

    @Override
    public String toString() {
        return "StudentQuestionEntity{" +
            "refId=" + refId +
            ", questionReference='" + questionReference + '\'' +
            ", actualResponse='" + actualResponse + '\'' +
            ", responseId='" + responseId + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentQuestionEntity that = (StudentQuestionEntity) o;

        if (studentItems.getItemReference() != null ? !studentItems.getItemReference().equals(that.studentItems.getItemReference()) : that.studentItems.getItemReference() != null) return false;
        if (studentItems.getSessionRefId() != null ? !studentItems.getSessionRefId().equals(that.studentItems.getSessionRefId()) : that.studentItems.getSessionRefId() != null) return false;
        return !(questionReference != null ? !questionReference.equals(that.questionReference) : that.questionReference != null);
    }

    @Override
    public int hashCode() {
        int result = studentItems.getItemReference() != null ? studentItems.getItemReference().hashCode() : 0;
        result = 31 * result + (studentItems.getSessionRefId() != null ? studentItems.getSessionRefId().hashCode() : 0);
        result = 31 * result + (questionReference != null ? questionReference.hashCode() : 0);
        return result;
    }
}
