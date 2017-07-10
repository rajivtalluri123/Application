package com.hmhco.api.grading.entities;

import javax.persistence.FetchType;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import java.util.UUID;

/**
 * Created by srikanthk on 4/25/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="activity_item_score")
public class ActivityItemScoreEntity extends AuditableEntity{


    private static final long serialVersionUID = 5796711527969312458L;

    @Id
    @Column(name="refid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refId;

    @Column(name = "max_score")
    private Integer maxScore;

    @Column(name="item_reference")
    private String itemReference;

    @Column(name="activity_refid", insertable = false, updatable = false)
    private UUID activityRefId;

    @Column(name="score_reference")
    private String scoreReference;

    @Column(name="question_reference")
    private String questionReference;

    @Column(name="weight")
    private Integer weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="activity_refid")
    private ActivityEntity activityEntity;

    @Override
    public String toString() {
        return "ActivityItemScoreEntity{" +
            "refId=" + refId +
            ", maxScore=" + maxScore +
            ", itemReference='" + itemReference + '\'' +
            ", activityRefId=" + activityRefId +
            ", scoreReference='" + scoreReference + '\'' +
            ", weight=" + weight +
            ", activityEntity=" + activityEntity +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityItemScoreEntity that = (ActivityItemScoreEntity) o;

        if (activityRefId != null ? !activityRefId.equals(that.activityRefId) : that.activityRefId != null) return false;
        if (scoreReference != null ? !scoreReference.equals(that.scoreReference) : that.scoreReference != null) return false;
        return !(itemReference != null ? !itemReference.equals(that.itemReference) : that.itemReference != null);
    }

    @Override
    public int hashCode() {
        int result = activityRefId != null ? activityRefId.hashCode() : 0;
        result = 31 * result + (scoreReference != null ? scoreReference.hashCode() : 0);
        result = 31 * result + (itemReference != null ? itemReference.hashCode() : 0);
        return result;
    }


}
