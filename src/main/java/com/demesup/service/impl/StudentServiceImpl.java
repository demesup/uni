package com.demesup.service.impl;

import com.demesup.api.dto.request.StudentRequest;
import com.demesup.domain.Group;
import com.demesup.domain.Student;
import com.demesup.domain.enums.YearCode;
import com.demesup.repository.StudentRepository;
import com.demesup.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StudentServiceImpl implements StudentService {

  StudentRepository repository;

  @Override
  public List<Student> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Student> findById(Long id) {
    if (id == null) return Optional.empty();
    return repository.findById(id);
  }

  @Override
  public Student save(Student student) {
    return repository.save(student);
  }

  @Override
  public Student create(StudentRequest request) {
    Student student = Student.create(request, null);
    return save(student);
  }


  @Override
  public Student create(StudentRequest request, Group group) {
    Student student = Student.create(request, group);
    return save(student);
  }

  @Override
  public Student update(Student student, StudentRequest request, Group group) {
    student.update(request, group);
    return save(student);
  }

  @Override
  public void delete(Student student) {
    student.delete();
    save(student);
  }

  @Override
  public Optional<Student> findByEmail(String email) {
    return repository.findByEmail(email);
  }

  @Override
  public Optional<Student> findByUniEmail(String email) {
    return repository.findByUniEmail(email);
  }

  @Override
  public Optional<Student> findByPhone(String phone) {
    return repository.findByPhone(phone);
  }

  @Override
  public List<Student> findAllByFaculty(Long facultyId) {
    return repository.findAllByFaculty(facultyId);
  }

  @Override
  public List<Student> findAllByGroup(Long groupId) {
    return repository.findAllByGroup(groupId);
  }

  @Override
  public List<Student> findAllByYear(Long yearId) {
    return repository.findAllByYear(yearId);
  }

  @Override
  public List<Student> findAllByYear(YearCode yearCode) {
    return repository.findAllByYear(yearCode);
  }
}
