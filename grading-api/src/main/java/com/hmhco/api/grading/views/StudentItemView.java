package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.NotNull;
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
@JsonRootName("studentItem")
@Relation(value = "studentItem", collectionRelation = "studentItems")
public class StudentItemView extends AbstractView{

    private Long refId;

    @NotNull
    private String itemReference;

    @NotNull
    private Integer time;

    private Integer maxScore;

    private Boolean userFlagged;

    private List<StudentQuestionView> questions;

}
