package com.demesup.domain.items;

import com.demesup.domain.Year;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  Long yearId;

  @JsonProperty
  Long professorId;

  public static Course create(Year year, Long professorId) {
    return Course.builder()
        .yearId(year.getId())
        .professorId(professorId)
        .build();
  }
}
