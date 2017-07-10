package com.hmhco.api.grading.views.request;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonRootName("studentItem")
@Relation(value = "studentItem", collectionRelation = "studentItems")
public class StudentItemRequestView extends AbstractView{

    @Null
    private Long refId;

    @NotNull
    private String itemReference;

    @NotNull
    private Integer time;

    private Integer maxScore;

    private Boolean userFlagged;

}
