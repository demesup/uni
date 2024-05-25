package com.demesup.api.dto.response.group;

import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.api.dto.response.stud.StudentResponse;
import com.demesup.domain.Group;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupResponse {
  String year;
  StudentResponse head;
  ProfessorResponse advisor;


  public static GroupResponse fromEntity(Group group) {
    return GroupResponse.builder()
        .year(group.getYear().getYear().toString())
        .head(StudentResponse.fromEntity(group.getHead()))
        .advisor(ProfessorResponse.fromEntity(group.getAdvisor()))
        .build();
  }

  public static List<GroupResponse> fromEntities(List<Group> groups) {
    return groups.stream().map(GroupResponse::fromEntity).toList();
  }
}
