package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
public class StudentScoreView extends AbstractView{

    @NotNull
    private String responseId;

    private String scoreReference;

    private UUID staffPersonalRefId;

    private Boolean attempted;

    private String value;

    private Integer weight;

    private Integer maxScore;

    private Integer score;
}
