package com.demesup.service;

import com.demesup.api.dto.request.FacultyRequest;
import com.demesup.domain.Faculty;
import com.demesup.domain.Professor;

import java.util.List;
import java.util.Optional;

public interface FacultyService {
  List<Faculty> findAll();

  Optional<Faculty> findById(Long id);

  Faculty save(Faculty Faculty);

  Faculty create(FacultyRequest request);

  Faculty update(Faculty faculty, FacultyRequest request);

  void delete(Faculty faculty);

  List<Professor> findDeans();

}
