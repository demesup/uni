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
  YearCode year;

  public static YearResponse fromEntity(Year year) {
    return YearResponse.builder()
        .year(year.getYear())
        .build();
  }
}
