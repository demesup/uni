package com.demesup.api.dto.response.stud;

import com.demesup.api.dto.response.group.GroupResponse;
import com.demesup.domain.Student;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDetailsResponse extends StudentResponse {
  GroupResponse group;


  public static StudentDetailsResponse fromEntity(Student student) {
    if (student == null) return null;
    return StudentDetailsResponse.builder()
        .firstName(student.getFirstName())
        .lastName(student.getLastName())
        .id(student.getId())
        .email(student.getEmail())
        .uniEmail(student.getUniEmail())
        .phone(student.getPhone())
        .group(GroupResponse.fromEntity(student.getGroup()))
        .build();
  }


  public static List<StudentDetailsResponse> fromEntitiesDetails(List<Student> groups) {
    return groups.stream().map(StudentDetailsResponse::fromEntity).toList();
  }
}
