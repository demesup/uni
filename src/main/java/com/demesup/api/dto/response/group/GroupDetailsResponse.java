package com.demesup.api.dto.response.group;

import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.api.dto.response.stud.StudentResponse;
import com.demesup.api.dto.response.year.YearFacultyDetailsResponse;
import com.demesup.domain.Group;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDetailsResponse extends GroupResponse {
  List<StudentResponse> students;

  public static GroupDetailsResponse fromEntity(Group group) {
    if (group == null) return null;
    return GroupDetailsResponse.builder().year(YearFacultyDetailsResponse.fromEntity(group.getYear())).code(group.getCode())
        .head(StudentResponse.fromEntity(group.getHead())).id(group.getId())
        .advisor(ProfessorResponse.fromEntity(group.getAdvisor()))
        .students(StudentResponse.fromEntities(group.getStudents()))
        .build();
  }


  public static List<GroupDetailsResponse> fromEntitiesDetails(List<Group> groups) {
    return groups.stream().map(GroupDetailsResponse::fromEntity).toList();
  }
}
