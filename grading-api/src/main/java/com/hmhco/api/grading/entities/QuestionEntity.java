package com.hmhco.api.grading.entities;

import javax.persistence.FetchType;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Created by srikanthk on 4/25/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class QuestionEntity extends AuditableEntity {

    private static final long serialVersionUID = 5571586449004786172L;

    @Id
    @Column(name="question_reference")
    private String questionReference;

    @Column(name="rubric_reference")
    private String rubricReference;

    @Column(name="question_type")
    private String questionType;

    @Column(name="automarkable")
    private Boolean automarkable;


    @Override
    public String toString() {
        return "QuestionEntity{" +
            "questionReference='" + questionReference + '\'' +
            ", rubricReference='" + rubricReference + '\'' +
            ", questionType='" + questionType + '\'' +
            ", automarkable='" + automarkable + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionEntity that = (QuestionEntity) o;

        return !(questionReference != null ? !questionReference.equals(that.questionReference) : that.questionReference != null);

    }

    @Override
    public int hashCode() {
        return questionReference != null ? questionReference.hashCode() : 0;
    }
}
