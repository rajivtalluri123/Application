package com.hmhco.api.grading.views.getresponse;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by nandipatim on 6/1/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("scoringComplete")
@Relation(value = "scoringComplete", collectionRelation = "scoringCompleteList")
public class ScoringCompleteResponse {

  List<String> incompleteItemReferences;

  UUID activityRefId;

  StudentSessionGetView studentSession;
}
