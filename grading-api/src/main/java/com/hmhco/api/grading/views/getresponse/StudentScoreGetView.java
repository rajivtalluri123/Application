package com.hmhco.api.grading.views.getresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.AbstractView;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by srikanthk on 4/26/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("studentScore")
@Relation(value = "studentScore", collectionRelation = "studentScores")
@JsonPropertyOrder({"responseId", "scoreReference", "staffPersonalRefId", "attempted", "value", "weight", "maxScore", "score", "title", "description", "automarkable","correctResponse"})
public class StudentScoreGetView extends AbstractView{

    @NotNull
    private String responseId;

    private String scoreReference;

    private UUID staffPersonalRefId;

    private Boolean attempted;

    private Object value;

    private Integer weight;

    private Integer maxScore;

    private Integer score;

    private String title;

    private String description;

    private boolean automarkable;

    private String correctResponse;

}
