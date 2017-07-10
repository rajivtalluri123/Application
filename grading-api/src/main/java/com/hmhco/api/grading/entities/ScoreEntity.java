package com.hmhco.api.grading.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Created by srikanthk on 4/25/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="score")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ScoreEntity extends AuditableEntity {

    private static final long serialVersionUID = 6519419890804622848L;

    @Id
    @Column(name="score_reference")
    private String scoreReference;


    @Column(name="automarkable")
    private Boolean automarkable;

    @Column(name="correct_response")
    private String correctResponse;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;


    @Override
    public String toString() {
        return "ScoreEntity{" +
            "scoreReference='" + scoreReference + '\'' +
            ", automarkable=" + automarkable +
            ", correctResponse='" + correctResponse + '\'' +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreEntity that = (ScoreEntity) o;

        return !(scoreReference != null ? !scoreReference.equals(that.scoreReference) : that.scoreReference != null);

    }

    @Override
    public int hashCode() {
        return scoreReference != null ? scoreReference.hashCode() : 0;
    }
}
