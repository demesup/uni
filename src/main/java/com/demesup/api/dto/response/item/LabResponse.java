package com.demesup.api.dto.response.item;

import com.demesup.api.dto.response.group.GroupResponse;
import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.domain.Group;
import com.demesup.domain.Professor;
import com.demesup.domain.items.Lab;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabResponse extends ItemInfoResponse {
  GroupResponse group;
  List<ProfessorResponse> professors;

  public static LabResponse fromEntity(Group group, List<Professor> professors) {
    return LabResponse.builder()
        .group(GroupResponse.fromEntity(group))
        .professors(professors.stream().map(ProfessorResponse::fromEntity).toList())
        .type(Lab.class.getSimpleName())
        .build();
  }
}
