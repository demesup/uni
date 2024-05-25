package com.demesup.repository;

import com.demesup.domain.Group;
import com.demesup.domain.enums.YearCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {

  @Query("SELECT g from Group g where g.active = true")
  List<Group> findAll();

  @Query("SELECT g from Group g where g.year.faculty.id = :id AND g.active = true")
  List<Group> findAllByFacultyId(Long id);

  @Query("SELECT g from Group g where g.year.id = :id AND g.active = true")
  List<Group> findAllByYearId(Long id);

  @Query("SELECT g from Group g where g.year.year = :code AND g.active = true")
  List<Group> findAllByYearCode(YearCode code);

  @Query("SELECT g from Group g where g.code = :code AND g.year.id = :yearId AND g.active = true")
  Optional<Group> findByCode(String code, Long yearId);

  @Query("SELECT g FROM Group g WHERE LOWER(g.code) = LOWER(:code) AND g.active = true")
  Optional<Group> findByCode(String code);
}
