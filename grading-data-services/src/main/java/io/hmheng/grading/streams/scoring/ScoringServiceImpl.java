package io.hmheng.grading.streams.scoring;

import io.hmheng.grading.authorization.AuthorizationService;
import io.hmheng.grading.authorization.util.HeadersHelper;
import io.hmheng.grading.rest.RestTemplateService;
import io.hmheng.grading.streams.scoring.domain.Event;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * Created by nandipatim on 5/31/17.
 */
@Service
public class ScoringServiceImpl implements ScoringService {

  @Autowired
  private RestTemplateService restTemplateService;

  @Autowired
  private HeadersHelper headersHelper;

  @Value("${scoring.host.baseUrl}")
  private String scoringUrl;
  //Get Event Object from scoring.

  @Override
  public Event getEventForActivityRefid(UUID activityRefId){

    String uri = String.format("/v1/events/%s", activityRefId);
    HttpHeaders httpHeaders = headersHelper.createHttpHeaders(AuthorizationService.Service.SCORING);
    Event event = restTemplateService.getEntity(scoringUrl , uri , httpHeaders ,Event.class);

    return event;
  }
}
