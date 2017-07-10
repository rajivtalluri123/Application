package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.request.QuestionsRequestView;
import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.hateoas.core.Relation;

/**
 * Created by srikanthk on 4/26/17.
 */

@Data
@NoArgsConstructor
@JsonRootName("question")
@Relation(value = "question", collectionRelation = "question")
public class QuestionsView  extends QuestionsRequestView{


    List<ScoresView> scores;

}
