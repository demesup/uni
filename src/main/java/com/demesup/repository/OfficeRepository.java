package com.demesup.repository;

import com.demesup.domain.Office;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends CrudRepository<Office, Long> {
  @Override
  @Query("SELECT o from Office o where o.active = true")
  List<Office> findAll();

  @Override
  @Query("SELECT o from Office o where o.id = :id AND o.active = true")
  Optional<Office> findById(Long id);

  @Query("SELECT o from Office o where o.faculty.id = :facultyId AND o.active = true")
  Optional<Office> findByFacultyId(Long facultyId);
}
