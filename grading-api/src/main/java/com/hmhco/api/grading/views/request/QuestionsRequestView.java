package com.hmhco.api.grading.views.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.ScoresView;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by srikanthk on 4/26/17.
 */

@Data
@NoArgsConstructor
@JsonRootName("question")
@Relation(value = "question", collectionRelation = "question")
public class QuestionsRequestView extends AbstractView{

    @NotNull
    private String questionReference;

    private String rubricReference;

    private String questionType;

    private Boolean automarkable;
}
