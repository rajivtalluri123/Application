package com.hmhco.api.grading.assembler;

import com.hmhco.api.grading.controller.ActivityController;
import com.hmhco.api.grading.resource.StudentItemResource;
import com.hmhco.api.grading.views.getresponse.StudentItemGetView;
import org.springframework.hateoas.ResourceAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class StudentItemAssembler implements ResourceAssembler<StudentItemGetView, StudentItemResource> {

    @Override
    public StudentItemResource toResource(StudentItemGetView view) {
        StudentItemResource resource = new StudentItemResource(view);
        resource.add(linkTo(methodOn(ActivityController.class).getItemDetails(view.getVersion(), view.getSessionId()
            , view.getItemReference()))
            .withSelfRel());
        return resource;
    }
}
