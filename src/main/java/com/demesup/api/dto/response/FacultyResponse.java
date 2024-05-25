package com.demesup.api.dto.response;

import com.demesup.domain.Faculty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FacultyResponse {
  String fullName;
  String shortName;

  public static FacultyResponse fromEntity(Faculty faculty) {
    return FacultyResponse.builder()
        .fullName(faculty.getFullName())
        .shortName(faculty.getShortName())
        .build();
  }
}
