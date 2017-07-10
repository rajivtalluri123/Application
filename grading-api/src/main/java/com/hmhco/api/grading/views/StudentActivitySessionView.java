package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.hmheng.grading.utils.StudentAssignmentStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by srikanthk on 4/24/17.
 */

@Data
@NoArgsConstructor
@JsonRootName("studentSession")
@Relation(value = "studentSession", collectionRelation = "studentSessions")
public class StudentActivitySessionView extends AbstractView {

    @NotNull
    @JsonProperty("sessionId")
    private UUID sessionRefId;

    @NotNull
    private UUID activityRefId;

    private UUID teacherAssignmentRefId;

    private UUID staffPersonalRefId;

    private Integer maxScore;

    private Integer maxTime;

    private Integer numQuestions;

    @NotNull
    private UUID studentPersonalRefId;

    private Integer numAttempted;

    private Integer score;

    private Integer sessionDuration;

    @NotNull
    private StudentAssignmentStatus status;

    private LocalDateTime dateStarted;

    private LocalDateTime dateCompleted;

    private String  userAgent;

    private List<ActivityItemScoreView> activityItems;

    private List< ItemsView > items;

    private List<StudentItemView> studentItems;

}
