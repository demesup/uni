package com.demesup.api.dto.response.year;

import com.demesup.api.dto.response.group.GroupResponse;
import com.demesup.domain.Year;
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
public class YearGroupDetailsResponse extends YearResponse {
  List<GroupResponse> groups;

  public static YearGroupDetailsResponse fromEntity(Year year) {
    return YearGroupDetailsResponse.builder()
        .groups(GroupResponse.fromEntities(year.getGroups()))
        .year(year.getYear())
        .build();
  }
}
