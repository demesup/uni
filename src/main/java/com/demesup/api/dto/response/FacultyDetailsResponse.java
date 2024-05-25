package com.demesup.api.dto.response;

import com.demesup.domain.Faculty;
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
public class FacultyDetailsResponse extends FacultyResponse {
  ProfessorResponse dean;
  List<ProfessorResponse> viceDeans;

  public static FacultyDetailsResponse fromEntity(Faculty faculty) {
    FacultyDetailsResponse build = FacultyDetailsResponse.builder()
        .fullName(faculty.getFullName())
        .shortName(faculty.getShortName())
        .viceDeans(faculty.getViceDeans().stream().map(ProfessorResponse::fromEntity).toList())
        .build();
    if (faculty.getDean() != null) {
      build.setDean(ProfessorResponse.fromEntity(faculty.getDean()));
    }
    return build;
  }
}
