package com.demesup.domain.items;

import com.demesup.domain.Group;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  Long groupId;

  @JsonProperty
  List<Long> professorIds;

  public static Lab create(Group group, List<Long> professorIds) {
    return Lab.builder()
        .groupId(group.getId())
        .professorIds(professorIds)
        .build();
  }
}
