package com.demesup.repository;

import com.demesup.domain.TimetableItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TimetableItemRepository extends CrudRepository<TimetableItem, Long> {

  @Query("SELECT i from TimetableItem i WHERE i.active = true")
  List<TimetableItem> findAll();

  @Query("SELECT i from TimetableItem i WHERE i.id = :id AND i.active = true")
  Optional<TimetableItem> findById(Long id);
}
