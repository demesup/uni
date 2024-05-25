package com.demesup.api.dto.response.year;

import com.demesup.api.dto.response.fac.FacultyResponse;
import com.demesup.api.dto.response.group.GroupResponse;
import com.demesup.domain.Year;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YearDetailsResponse extends YearResponse {
  FacultyResponse faculty;
  List<GroupResponse> groups;


  public static YearDetailsResponse fromEntity(Year year) {
    return YearDetailsResponse.builder()
        .year(year.getYear())
        .faculty(FacultyResponse.fromEntity(year.getFaculty()))
        .groups(GroupResponse.fromEntities(year.getGroups()))
        .build();
  }
}
