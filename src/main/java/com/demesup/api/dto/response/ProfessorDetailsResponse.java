package com.demesup.api.dto.response;

import com.demesup.domain.Professor;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfessorDetailsResponse extends ProfessorResponse {
  FacultyResponse faculty;

  public static ProfessorDetailsResponse fromEntity(Professor dean) {
    return ProfessorDetailsResponse.builder()
        .faculty(FacultyResponse.fromEntity(dean.getFaculty()))
        .email(dean.getEmail())
        .name(dean.getName())
        .phone(dean.getPhone())
        .description(dean.getDescription())
        .build();
  }
}
