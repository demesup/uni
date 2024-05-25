package com.demesup.api.dto.response.fac;

import com.demesup.domain.Faculty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FacultyResponse {
  Long id;
  String fullName;
  String shortName;

  public static FacultyResponse fromEntity(Faculty faculty) {
    if (faculty == null) return null;
    return FacultyResponse.builder()
        .id(faculty.getId())
        .fullName(faculty.getFullName())
        .shortName(faculty.getShortName())
        .build();
  }
}
