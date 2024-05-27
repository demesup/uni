package com.demesup.service.impl;


import com.demesup.api.dto.request.GroupRequest;
import com.demesup.domain.Group;
import com.demesup.domain.Professor;
import com.demesup.domain.Student;
import com.demesup.domain.Year;
import com.demesup.domain.enums.YearCode;
import com.demesup.repository.GroupRepository;
import com.demesup.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class GroupServiceImpl implements GroupService {

  GroupRepository repository;

  @Override
  public List<Group> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Group> findById(Long id) {
    if (id == null) return Optional.empty();
    return repository.findById(id);
  }

  public Group save(Group group) {
    return repository.save(group);
  }

  @Override
  public Group create(GroupRequest request, Year year, Student head, Professor professor) {
    return save(Group.create(request, year, head, professor));
  }

  @Override
  public Group update(Group group, GroupRequest request, Year year, Professor advisor, Student head) {
    group.update(request, year, head, advisor);
    return save(group);
  }

  @Override
  public void delete(Group group) {
    group.delete();
    save(group);
  }

  @Override
  public List<Group> findAllByFacultyId(Long id) {
    return repository.findAllByFacultyId(id);
  }

  @Override
  public List<Group> findAllByYearId(Long id) {
    return repository.findAllByYearId(id);
  }

  @Override
  public List<Group> findAllByYearCode(YearCode code) {
    return repository.findAllByYearCode(code);
  }

  @Override
  public Optional<Group> findByCode(String code, Long yearId) {
    return repository.findByCode(code, yearId);
  }

  @Override
  public Optional<Group> findByCode(String code) {
    return repository.findByCode(code);
  }


}
