package com.demesup.service;

import com.demesup.api.dto.request.ProfessorRequest;
import com.demesup.domain.Faculty;
import com.demesup.domain.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {
  List<Professor> findAll();

  Optional<Professor> findById(Long id);

  Professor save(Professor professor);

  Professor create(ProfessorRequest request);

  Professor update(Professor professor, ProfessorRequest request, Faculty faculty);

  void delete(Professor professor);

  Optional<Professor> findIdByEmail(String email);

  Optional<Professor> findIdByPhone(String phone);

  List<Professor> findAllViceDeans();

  Optional<Professor> findDeanByFacultyId(Long facultyId);

  List<Professor> findAllDeans();

  List<Professor> findAllViceDeans(Long facultyId);

  List<Professor> findAllByFaculty(Long facultyId);

  List<Professor> findAllByIds(List<Long> professorIds);
}
