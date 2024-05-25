package com.demesup.api.dto.response.stud;

import com.demesup.domain.Student;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
  String firstName;
  String lastName;
  String email;
  String uniEmail;
  String phone;

  public static StudentResponse fromEntity(Student student) {
    return StudentResponse.builder()
        .firstName(student.getFirstName())
        .lastName(student.getLastName())
        .email(student.getEmail())
        .uniEmail(student.getUniEmail())
        .phone(student.getPhone())
        .build();
  }

  public static List<StudentResponse> fromEntities(List<Student> students) {
    return students.stream().map(StudentResponse::fromEntity).toList();
  }
}
