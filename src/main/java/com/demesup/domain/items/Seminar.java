package com.demesup.domain.items;

import com.demesup.domain.Group;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  Long groupId;

  @JsonProperty
  Long assistantId;

  public static Seminar create(Group group, Long assistantId) {
    return Seminar.builder()
        .groupId(group.getId())
        .assistantId(assistantId)
        .build();
  }
}
