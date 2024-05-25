package com.demesup.api.dto.response.group;

import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.api.dto.response.stud.StudentResponse;
import com.demesup.api.dto.response.year.YearFacultyDetailsResponse;
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
  Long id;
  YearFacultyDetailsResponse year;
  String code;
  StudentResponse head;
  ProfessorResponse advisor;

  public static GroupResponse fromEntity(Group group) {
    if (group == null) return null;
    return GroupResponse.builder()
        .year(YearFacultyDetailsResponse.fromEntity(group.getYear()))
        .code(group.getCode())
        .head(StudentResponse.fromEntity(group.getHead()))
        .id(group.getId())
        .advisor(ProfessorResponse.fromEntity(group.getAdvisor()))
        .build();
  }

  public static List<GroupResponse> fromEntities(List<Group> groups) {
    return groups.stream().map(GroupResponse::fromEntity).toList();
  }
}
