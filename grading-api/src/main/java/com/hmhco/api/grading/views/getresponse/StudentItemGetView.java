package com.hmhco.api.grading.views.getresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.AbstractView;
import io.hmheng.grading.utils.Status;
import java.util.List;
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
@JsonRootName("studentItem")
@Relation(value = "studentItem", collectionRelation = "studentItems")
@JsonPropertyOrder({"refId", "itemReference", "time", "maxScore", "userFlagged", "organizationId", "itemPoolId", "type", "questions"})
public class StudentItemGetView extends AbstractView{

    private Long refId;

    @NotNull
    private String itemReference;

    @NotNull
    private Integer time;

    private Integer maxScore;
    
    private Integer score;

    private Boolean userFlagged;

    private Integer organizationId;

    private String itemPoolId;

    private String type;

    private Status status;

    private UUID sessionId;

    private List<StudentQuestionGetView> questions;

}
