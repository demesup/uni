package com.kpop.demesup.domain.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kpop.demesup.domain.Year;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class Lab extends ItemInfo {
  @JsonProperty
  Year year;

  @JsonProperty
  List<Long> professor_ids;

  public static Lab create(Year year, List<Long> professor_ids) {
    return Lab.builder()
        .year(year)
        .professor_ids(professor_ids)
        .build();
  }
}
