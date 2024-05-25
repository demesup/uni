package com.demesup.service;

import com.demesup.api.dto.request.StudentRequest;
import com.demesup.domain.Group;
import com.demesup.domain.Student;
import com.demesup.domain.enums.YearCode;

import java.util.List;
import java.util.Optional;

public interface StudentService {
  List<Student> findAll();

  Optional<Student> findById(Long id);

  Student save(Student student);

  Student create(StudentRequest request);

  Student create(StudentRequest request, Group group);

  Student update(Student student, StudentRequest request, Group group);

  void delete(Student student);

  Optional<Student> findByEmail(String email);

  Optional<Student> findByUniEmail(String email);

  Optional<Student> findByPhone(String phone);

  List<Student> findAllByFaculty(Long facultyId);

  List<Student> findAllByGroup(Long groupId);

  List<Student> findAllByYear(Long yearId);

  List<Student> findAllByYear(YearCode yearCode);


}
