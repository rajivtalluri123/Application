package com.hmhco.api.grading.mapper.viewmapper;


import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.mapper.entitymapper.StudentItemViewMapper;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.StudentSessionView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentSessionViewMapper implements SingleViewMapper<StudentSessionView, StudentSessionEntity> {

  @Autowired
  private StudentItemViewMapper entityMapper;


  @Override
  public StudentSessionEntity convert(StudentSessionView view) {
    StudentSessionEntity studentSessionEntity = new StudentSessionEntity();
    BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, studentSessionEntity);
    studentSessionEntity.setStudentItems(entityMapper.convert(view.getStudentItems()));
    return studentSessionEntity;
  }

  @Override
  public boolean supports(Class<? extends AbstractView> clazz) {

    return StudentSessionView.class.equals(clazz);
  }
}
