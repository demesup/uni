package com.demesup.api.dto.response.item;

import com.demesup.api.dto.response.group.GroupResponse;
import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.domain.Group;
import com.demesup.domain.Professor;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeminarResponse extends ItemInfoResponse {
  GroupResponse group;
  ProfessorResponse professor;

  public static SeminarResponse fromEntity(Group group, Professor professor) {
    return SeminarResponse.builder()
        .group(GroupResponse.fromEntity(group))
        .professor(ProfessorResponse.fromEntity(professor))
        .build();
  }
}
