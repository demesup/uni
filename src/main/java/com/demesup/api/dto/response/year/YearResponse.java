package com.demesup.api.dto.response.year;

import com.demesup.domain.Year;
import com.demesup.domain.enums.YearCode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YearResponse {
  Long id;
  String year;

  public static YearResponse fromEntity(Year year) {
    if (year == null) return null;
    return YearResponse.builder()
        .year(year.getYear().getDescription())
        .id(year.getId())
        .build();
  }
}
