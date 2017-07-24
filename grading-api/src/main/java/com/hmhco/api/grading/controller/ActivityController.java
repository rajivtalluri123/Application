package com.hmhco.api.grading.controller;

import com.hmhco.api.grading.controller.utils.Constants;
import com.hmhco.api.grading.resource.StudentItemResource;
import com.hmhco.api.grading.security.RoleConverter;
import com.hmhco.api.grading.service.StudentSessionService;
import com.hmhco.api.grading.service.action.SaveResponseRequest;
import com.hmhco.api.grading.service.action.SaveStudentSessionRequest;
import com.hmhco.api.grading.service.action.SaveStudentSessionResponse;
import com.hmhco.api.grading.utils.StudentSessionRequestType;
import com.hmhco.api.grading.views.ActivityView;
import com.hmhco.api.grading.views.SessionStatusView;
import com.hmhco.api.grading.views.StudentSessionView;
import com.hmhco.api.grading.views.StudentActivitySessionView;
import com.hmhco.api.grading.views.getresponse.ScoringCompleteResponse;
import com.hmhco.api.grading.views.getresponse.StudentItemGetView;
import com.hmhco.api.grading.views.request.GradingEndpoint;
import com.hmhco.api.grading.views.request.ResponseView;
import com.hmhco.api.grading.assembler.ViewResolver;
import com.hmhco.api.grading.views.request.StudentStatusRequest;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import java.util.UUID;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by srikanthk on 4/24/17.
 */

@RestController
@RequestMapping("v{"+ Constants.VERSION_PARAM_NAME +"}/activities")
public class ActivityController extends BaseController {

  @Autowired
  private StudentSessionService studentSessionService;

  @Autowired
  private ViewResolver viewResolver;

  @PreAuthorize("hasRole('" + RoleConverter.ROLE_TRUSTED_API+ "')")
  @RequestMapping(value="/{sessionId}/init", method = RequestMethod.POST,
                        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public StudentActivitySessionView saveStudentSessionActivity(
            @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
            @PathVariable("sessionId")UUID sessionId,
            @Valid  @RequestBody StudentActivitySessionView studentActivitySessionview) {
                checkForVersion(versionNbr , GradingEndpoint.ACTIVITY_INIT_POST);

      if(sessionId == null) {
        throw new BadRequestException();
      }

      SaveStudentSessionRequest request = new SaveStudentSessionRequest(sessionId, studentActivitySessionview);
      request.setRequestType(StudentSessionRequestType.INIT);

      SaveStudentSessionResponse response = studentSessionService.getOrCreateStudentSession(request);

      return response.getStudentActivitySessionView();
    }

  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('" + RoleConverter.ROLE_TEACHER + "','"+RoleConverter.ROLE_TRUSTED_API+"')")
  @RequestMapping(value="/{sessionId}/items/scores", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public StudentSessionView scoringCompletePerSession(
      @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
      @PathVariable("sessionId") UUID sessionId,
      @Valid @RequestBody StudentSessionView studentSessionview) {
    checkForVersion(versionNbr , GradingEndpoint.ACTIVITY_ITEM_SCORES_POST);
    if(sessionId == null) {
      throw new BadRequestException();
    }
    SaveStudentSessionRequest request = new SaveStudentSessionRequest(sessionId, studentSessionview);
    request.setRequestType(StudentSessionRequestType.SCORES);
    SaveStudentSessionResponse response = studentSessionService.getOrCreateStudentSession(request);

    return response.getStudentSessionView();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('" + RoleConverter.ROLE_TEACHER + "','"+RoleConverter.ROLE_TRUSTED_API+"')")
  @RequestMapping(value="/{sessionId}/itemresponses/{responseId}", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseView saveItemResponse(
      @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
      @PathVariable("sessionId")UUID sessionId,
      @PathVariable("responseId")String responseId,
      @Valid  @RequestBody ResponseView responseView) {
    checkForVersion(versionNbr, GradingEndpoint.STUDENT_ITEMSCORES_RESPONSE_POST);

    if(sessionId == null || responseId == null) {
      throw new BadRequestException();
    }

    SaveResponseRequest saveResponseRequest = new SaveResponseRequest(sessionId , responseId , responseView);
    responseView = studentSessionService.createOrUpdateItemReponse(saveResponseRequest);
    return responseView;
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyRole('" + RoleConverter.ROLE_TEACHER + "','"+RoleConverter.ROLE_TRUSTED_API+"')")
  @RequestMapping(value="/{sessionId}/status", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public SessionStatusView saveItemResponse(
      @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
      @PathVariable("sessionId")UUID sessionId,
      @Valid  @RequestBody StudentStatusRequest studentStatusRequest) {
    checkForVersion(versionNbr , GradingEndpoint.STUDENT_SESSION_STATUS);

    if(sessionId == null) {
      throw new BadRequestException();
    }

      SessionStatusView responseView = studentSessionService.updateSessionStatus(sessionId, studentStatusRequest);

    return responseView;
  }


  @RequestMapping(value = "/{sessionId}/items/{itemReference}", method = RequestMethod.GET, produces = Constants.HALJSON)
  @PreAuthorize("hasAnyRole('" + RoleConverter.ROLE_TEACHER + "','"+RoleConverter.ROLE_TRUSTED_API+"')")
  public StudentItemResource getItemDetails(
      @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
      @PathVariable("sessionId")UUID sessionId,
      @PathVariable("itemReference")String itemReference) {
    checkForVersion(versionNbr , GradingEndpoint.STUDENT_ITEM_DETAILS_GET);
   //TODO Create a new StudentItemGetView and should be changes only used for producing contract

    if(sessionId == null || itemReference == null)
      throw new BadRequestException();

    return viewResolver.resolveSingleDtoView(studentSessionService.getStudentItems(sessionId,itemReference ) , versionNbr);
  }

  @ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class})
  @ResponseBody
  public ResponseEntity resolveMessageNotReadableException() {
    ResponseEntity<String> responseEntity = new ResponseEntity<>("Invalid request parameter(s)", HttpStatus.BAD_REQUEST);
    return responseEntity;
  }

  @PreAuthorize("hasAnyRole('" + RoleConverter.ROLE_TEACHER + "','"+RoleConverter.ROLE_TRUSTED_API+"')")
  @RequestMapping(value="/{sessionId}/scoringcomplete", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ScoringCompleteResponse scoringCompletePerSession(
      @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
      @PathVariable("sessionId") UUID sessionId,
      HttpServletResponse response) {

    response.setStatus(HttpServletResponse.SC_CREATED);
    checkForVersion(versionNbr, GradingEndpoint.STUDENT_SESSION_SCORING_COMPLETE);

    if(sessionId == null){
      throw new BadRequestException();
    }

    ScoringCompleteResponse scoringCompleteResponse = studentSessionService.scoringCompleted(sessionId);
    UUID activityRefId = scoringCompleteResponse.getActivityRefId();
    ActivityView activityView = studentSessionService.getActivityDetails(activityRefId);
    scoringCompleteResponse.getStudentSession().setAssignment(activityView);

    if(!CollectionUtils.isEmpty(scoringCompleteResponse.getIncompleteItemReferences())){
      response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
    }
    return scoringCompleteResponse;
  }

  @PreAuthorize("hasAnyRole('"+RoleConverter.ROLE_TRUSTED_API+"')")
  @RequestMapping(value="/{sessionId}/pushScoresToScoring", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> pushScoresToScoring(
      @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
      @PathVariable("sessionId") UUID sessionId){

    checkForVersion(versionNbr, GradingEndpoint.STUDENT_SESSION_PUSH_SCORING);
    studentSessionService.pushScoresToScoring(sessionId);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('"+RoleConverter.ROLE_TRUSTED_API+"')")
  @RequestMapping(value="/{sessionId}/pushStatusForAssignment", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> pushStatusForAssignment(
      @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
      @PathVariable("sessionId") UUID sessionId){

    checkForVersion(versionNbr, GradingEndpoint.STUDENT_SESSION_PUSH_SCORING);
    studentSessionService.pushStatusToAssignment(sessionId);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/{sessionId}/items", method = RequestMethod.GET, produces = Constants.HALJSON)
  @PreAuthorize("hasAnyRole('" + RoleConverter.ROLE_TEACHER + "','"+RoleConverter.ROLE_TRUSTED_API+"')")
  public Page<StudentItemGetView> getStudentActivityItems(
      @PathVariable(Constants.VERSION_PARAM_NAME) String versionNbr,
      @PathVariable("sessionId")UUID sessionId,
      @RequestParam(value = "onlyManualScored", required = false, defaultValue = "false") Boolean onlyManualScored,
      @RequestParam(value = "includeQuestionsAndScores", required = false, defaultValue = "false") Boolean includeQuestionsAndScores,
      Pageable pageable) {
    checkForVersion(versionNbr, GradingEndpoint.STUDENT_ITEMS_GET);

    if(sessionId == null)
      throw new BadRequestException();

    Page<StudentItemGetView> views = studentSessionService.getStudentActivityItems(pageable, sessionId, onlyManualScored, includeQuestionsAndScores);
    return views;
  }

}
