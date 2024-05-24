package com.kpop.demesup.domain.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kpop.demesup.domain.Year;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class Course extends ItemInfo {
  @JsonProperty
  Year year;

  @JsonProperty
  Long professor_id;

  public static Course create(Year year, Long professor_id) {
    return Course.builder()
        .year(year)
        .professor_id(professor_id)
        .build();
  }
}
