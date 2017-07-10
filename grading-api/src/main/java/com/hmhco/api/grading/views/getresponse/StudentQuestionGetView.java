package com.hmhco.api.grading.views.getresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.AbstractView;
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
@JsonPropertyOrder({"refId", "questionReference", "actualResponse", "rubricReference", "questionType", "responses"})
public class StudentQuestionGetView extends AbstractView{

    @Null
    private Long refId;

    @NotNull
    private  String questionReference;

    private String actualResponse;

    private String rubricReference;

    private String questionType;


    private List<StudentScoreGetView> responses;
}
