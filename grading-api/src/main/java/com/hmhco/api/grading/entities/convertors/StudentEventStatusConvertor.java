package com.hmhco.api.grading.entities.convertors;

import io.hmheng.grading.utils.StudentAssignmentStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StudentEventStatusConvertor implements AttributeConverter<StudentAssignmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(StudentAssignmentStatus attribute) {return String.valueOf(attribute.ordinal()); }

    @Override
    public StudentAssignmentStatus convertToEntityAttribute(String dbData) { return StudentAssignmentStatus.fromOrdinal(dbData); }

}

