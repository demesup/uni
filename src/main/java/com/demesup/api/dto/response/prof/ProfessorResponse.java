package com.demesup.api.dto.response.prof;

import com.demesup.domain.Professor;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfessorResponse {
  String name;
  String email;
  String phone;
  String description;

  public static ProfessorResponse fromEntity(Professor professor) {
    return ProfessorResponse.builder()
        .name(professor.getName())
        .email(professor.getEmail())
        .phone(professor.getPhone())
        .description(professor.getDescription())
        .build();
  }
}
