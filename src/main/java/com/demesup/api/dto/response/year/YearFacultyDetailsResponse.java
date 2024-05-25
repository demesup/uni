package com.demesup.api.dto.response.year;

import com.demesup.api.dto.response.fac.FacultyResponse;
import com.demesup.domain.Year;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YearFacultyDetailsResponse extends YearResponse {
  FacultyResponse faculty;

  public static YearFacultyDetailsResponse fromEntity(Year year) {
    return YearFacultyDetailsResponse.builder()
        .faculty(FacultyResponse.fromEntity(year.getFaculty()))
        .year(year.getYear())
        .build();
  }
}
