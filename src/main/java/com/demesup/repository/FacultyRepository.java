package com.demesup.repository;

import com.demesup.domain.Faculty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long> {
  @Query("SELECT f FROM Faculty f LEFT JOIN FETCH f.dean d LEFT JOIN FETCH f.viceDeans vd WHERE f.active = true")
  List<Faculty> findAll();

  @Override
  @Query("SELECT f FROM Faculty f LEFT JOIN FETCH f.dean d WHERE f.id = :id AND f.active = true")
  Optional<Faculty> findById(Long id);
}
