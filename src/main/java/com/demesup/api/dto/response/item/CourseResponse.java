package com.demesup.api.dto.response.item;

import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.api.dto.response.year.YearResponse;
import com.demesup.domain.Professor;
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
public class CourseResponse extends ItemInfoResponse {
  YearResponse year;
  ProfessorResponse professor;

  public static CourseResponse fromEntity(Year year, Professor professor) {
    return CourseResponse.builder()
        .year(YearResponse.fromEntity(year))
        .professor(ProfessorResponse.fromEntity(professor))
        .build();
  }
}
