package com.demesup.repository;

import com.demesup.domain.Student;
import com.demesup.domain.enums.YearCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
  @Override
  @Query("SELECT p FROM Student p WHERE p.active = true")
  List<Student> findAll();

  @Query("SELECT p FROM Student p WHERE p.id = :id AND p.active = true")
  Optional<Student> findById(Long id);

  @Query("SELECT s FROM Student s WHERE s.active = true AND s.group.year.faculty.id = :facultyId")
  List<Student> findAllByFaculty(Long facultyId);

  @Query("SELECT s FROM Student s WHERE s.active = true AND s.group.year.id = :yearId")
  List<Student> findAllByYear(Long yearId);

  @Query("SELECT s FROM Student s WHERE s.active = true AND s.group.year.year = :code")
  List<Student> findAllByYear(YearCode code);

  @Query("SELECT s FROM Student s WHERE s.active = true AND s.email = :email")
  Optional<Student> findByEmail(String email);

  @Query("SELECT s FROM Student s WHERE s.active = true AND s.uniEmail = :email")
  Optional<Student> findByUniEmail(String email);

  @Query("SELECT s FROM Student s WHERE s.active = true AND s.phone = :phone")
  Optional<Student> findByPhone(String phone);

  @Query("SELECT s FROM Student s WHERE s.active = true AND s.group.id = :groupId")
  List<Student> findAllByGroup(Long groupId);
}
