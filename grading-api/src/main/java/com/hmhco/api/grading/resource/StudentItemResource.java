package com.hmhco.api.grading.resource;

import com.hmhco.api.grading.views.getresponse.StudentItemGetView;
import org.springframework.hateoas.Resource;

/**
 * Created by nandipatim on 5/8/17.
 */
public class StudentItemResource extends Resource<StudentItemGetView> {
  public StudentItemResource(StudentItemGetView content) {
    super(content);
  }
}
