package com.hmhco.api.grading.views.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.StudentScoreView;
import java.util.List;
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
@JsonRootName("studentQuestion")
@Relation(value = "studentQuestion", collectionRelation = "studentQuestions")
public class StudentQuestionRequestView extends AbstractView{

    @Null
    private Long refId;

    @NotNull
    private  String questionReference;

    private Object actualResponse;
    
    private Object binaryResponse;
}
