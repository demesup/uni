package com.demesup.service.impl;


import com.demesup.api.dto.request.FacultyRequest;
import com.demesup.domain.Faculty;
import com.demesup.domain.Professor;
import com.demesup.repository.FacultyRepository;
import com.demesup.service.FacultyService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FacultyServiceImpl implements FacultyService {

  FacultyRepository repository;

  @Override
  public List<Faculty> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Faculty> findById(Long id) {
    if (id == null) return Optional.empty();
    return repository.findById(id);
  }

  @Override
  public Faculty save(Faculty faculty) {
    return repository.save(faculty);
  }

  @Override
  public Faculty create(FacultyRequest request) {
    return null;
  }

  @Override
  public Faculty update(Faculty faculty, FacultyRequest request) {
    faculty.update(request);
    return save(faculty);
  }

  @Override
  public void delete(Faculty faculty) {
    faculty.delete();
    save(faculty);
  }

  @Override
  public List<Professor> findDeans() {
    return repository.findAll().stream().map(Faculty::getDean).filter(Objects::nonNull).toList();
  }
}
