package com.demesup.service.impl;


import com.demesup.api.dto.request.ProfessorRequest;
import com.demesup.domain.Faculty;
import com.demesup.domain.Professor;
import com.demesup.repository.ProfessorRepository;
import com.demesup.service.FacultyService;
import com.demesup.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProfessorServiceImpl implements ProfessorService {

  ProfessorRepository repository;
  FacultyService facultyService;

  @Override
  public List<Professor> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Professor> findById(Long id) {
    return repository.findByIdAndActiveTrue(id);
  }

  @Override
  public Professor save(Professor professor) {
    return repository.save(professor);
  }

  @Override
  public Professor create(ProfessorRequest request) {
    return null;
  }

  @Override
  public Professor update(Professor professor, ProfessorRequest request, Faculty faculty) {
    professor.update(request, faculty);
    return save(professor);
  }

  @Override
  public void delete(Professor professor) {
    professor.delete();
    save(professor);
  }

  @Override
  public Optional<Professor> findIdByEmail(String email) {
    return repository.findByEmailAndActiveTrue(email);
  }

  @Override
  public Optional<Professor> findIdByPhone(String phone) {
    return repository.findByPhoneAndActiveTrue(phone);
  }


  @Override
  public List<Professor> findAllViceDeans() {
    return repository.findAllByActiveTrueAndViceDeanOfNotNull();
  }

  @Override
  public Optional<Professor> findDeanByFacultyId(Long facultyId) {
    return facultyService.findById(facultyId).map(Faculty::getDean);
  }

  @Override
  public List<Professor> findAllDeans() {
    return facultyService.findDeans();
  }

  @Override
  public List<Professor> findAllViceDeans(Long facultyId) {
    return repository.findAllByActiveTrueAndViceDeanOfNotNullAndFacultyId(facultyId);
  }

  @Override
  public List<Professor> findAllByFaculty(Long facultyId) {
    return repository.findAllByFacultyIdAndActiveTrue(facultyId);
  }

}
