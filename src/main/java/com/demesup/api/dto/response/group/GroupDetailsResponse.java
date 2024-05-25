package com.demesup.api.dto.response.group;

import com.demesup.api.dto.response.fac.FacultyResponse;
import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.api.dto.response.stud.StudentResponse;
import com.demesup.domain.Group;
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
public class GroupDetailsResponse extends GroupResponse {
  FacultyResponse faculty;
  List<StudentResponse> students;

  public static GroupDetailsResponse fromEntity(Group group) {
    return GroupDetailsResponse.builder()
        .year(group.getYear().getYear().toString())
        .head(StudentResponse.fromEntity(group.getHead()))
        .advisor(ProfessorResponse.fromEntity(group.getAdvisor()))
        .faculty(FacultyResponse.fromEntity(group.getFaculty()))
        .students(StudentResponse.fromEntities(group.getStudents()))
        .build();
  }


  public static List<GroupDetailsResponse> fromEntitiesDetails(List<Group> groups) {
    return groups.stream().map(GroupDetailsResponse::fromEntity).toList();
  }
}
