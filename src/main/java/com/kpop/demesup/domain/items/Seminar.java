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
public class Seminar extends ItemInfo {
  @JsonProperty
  Year year;

  @JsonProperty
  Long assistantId;

  public static Seminar create(Year year, Long assistantId) {
    return Seminar.builder()
        .year(year)
        .assistantId(assistantId)
        .build();
  }
}
