package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.swing.text.View;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by srikanthk on 4/26/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("activityItemScore")
@Relation(value = "activityItemScore", collectionRelation = "activityItemScores")
public class ActivityItemScoreView extends AbstractView{


    @NotNull
    private String itemReference;

    @NotNull
    private String scoreReference;

    @NotNull
    private String questionReference;

    private Integer maxScore;

    private Integer weight;
}
