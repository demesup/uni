package com.demesup.domain.items;

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

  public static Course create(Long yearId, Long professorId) {
    return Course.builder()
        .yearId(yearId)
        .professorId(professorId)
        .build();
  }
}
