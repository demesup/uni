package com.demesup.repository;

import com.demesup.domain.Year;
import com.demesup.domain.enums.YearCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearRepository extends CrudRepository<Year, Long> {
  @Override
  @Query("SELECT y from Year y where y.active = true")
  List<Year> findAll();

  List<Year> findAllByActiveTrue();

  @Override
  @Query("SELECT y from Year y  where y.id = :id AND y.active = true")
  Optional<Year> findById(Long id);

  @Query("SELECT y from Year y  where y.year = :code AND y.faculty.id = :facultyId AND y.active = true")
  Optional<Long> findIdByYearAndFaculty(YearCode code, Long facultyId);

  @Query("SELECT y from Year y  where y.faculty.id = :id AND y.active = true")
  List<Year> findAllByFacultyId(Long id);
}
