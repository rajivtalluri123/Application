package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
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
public class StudentQuestionView extends AbstractView{

    @Null
    private Long refId;

    @NotNull
    private  String questionReference;

    private String actualResponse;

    private String responseId;

    private List<StudentScoreView> responses;
}
