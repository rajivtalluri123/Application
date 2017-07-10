package io.hmheng.grading.streams.scoring;

import io.hmheng.grading.streams.scoring.domain.Event;
import java.util.UUID;

/**
 * Created by nandipatim on 5/31/17.
 */
public interface ScoringService {
  //Get Event Object from scoring

  Event getEventForActivityRefid(UUID activityRefId);
}
