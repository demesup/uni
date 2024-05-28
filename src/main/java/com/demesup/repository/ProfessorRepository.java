package com.demesup.repository;

import com.demesup.domain.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
  @Override
  @Query("SELECT p FROM Professor p WHERE p.active = true")
  List<Professor> findAll();

  List<Professor> findAllByFacultyIdAndActiveTrue(Long facultyId);


  List<Professor> findAllByActiveTrueAndViceDeanOfNotNull();

  @Query("SELECT p FROM Professor p WHERE p.viceDeanOf.id = :faculty_id AND p.active = true")
  List<Professor> findAllViceDeans(Long faculty_id);


  Optional<Professor> findByIdAndActiveTrue(Long id);

  Optional<Professor> findByEmailAndActiveTrue(String email);

  Optional<Professor> findByPhoneAndActiveTrue(String phone);

  List<Professor> findAllByIdInAndActiveTrue(List<Long> id);
}
