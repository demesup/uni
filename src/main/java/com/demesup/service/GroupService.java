package com.demesup.service;

import com.demesup.api.dto.request.GroupRequest;
import com.demesup.domain.Group;
import com.demesup.domain.Professor;
import com.demesup.domain.Student;
import com.demesup.domain.Year;
import com.demesup.domain.enums.YearCode;

import java.util.List;
import java.util.Optional;

public interface GroupService {
  List<Group> findAll();

  Optional<Group> findById(Long id);

  Group create(GroupRequest request, Year year, Student head, Professor professor);

  Group update(Group group, GroupRequest request, Year year, Professor advisor, Student head);

  void delete(Group group);

  List<Group> findAllByFacultyId(Long id);

  List<Group> findAllByYearId(Long id);

  List<Group> findAllByYearCode(YearCode code);

  Optional<Group> findByCode(String code, Long yearId);

  Optional<Group> findByCode(String code);
}
