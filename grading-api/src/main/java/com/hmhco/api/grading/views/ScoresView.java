package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by srikanthk on 4/26/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("score")
@Relation(value = "score", collectionRelation = "scores")
public class ScoresView extends AbstractView{

    @NotNull
    private String scoreReference;

    private String title;

    private String description;

    private boolean automarkable;

    private String correctResponse;
}
