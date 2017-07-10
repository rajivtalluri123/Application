package com.hmhco.api.grading.service.action;

import com.hmhco.api.grading.views.request.ResponseView;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nandipatim on 5/9/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveResponseRequest {

  private UUID sessionId;

  private String responseId;

  private ResponseView responseView;

}
