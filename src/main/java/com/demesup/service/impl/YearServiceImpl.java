package com.demesup.service.impl;


import com.demesup.api.dto.request.YearRequest;
import com.demesup.domain.Faculty;
import com.demesup.domain.Year;
import com.demesup.domain.enums.YearCode;
import com.demesup.repository.YearRepository;
import com.demesup.service.YearService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class YearServiceImpl implements YearService {

  YearRepository repository;

  @Override
  public List<Year> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Year> findById(Long id) {
    if (id == null) return Optional.empty();
    return repository.findById(id);
  }

  public Year save(Year year) {
    return repository.save(year);
  }

  @Override
  public Year create(YearRequest request, Faculty faculty) {
    return save(Year.create(request, faculty));
  }

  @Override
  public Year update(Year year, YearRequest request, Faculty faculty) {
    year.update(request, faculty);
    return save(year);
  }

  @Override
  public void delete(Year year) {
    year.delete();
    save(year);
  }

  @Override
  public List<Year> findAllByFacultyId(Long id) {
    return repository.findAllByFacultyId(id);
  }

  @Override
  public Optional<Long> findIdByYearAndFacultyId(YearCode yearCode, Long facultyId) {
    return repository.findIdByYearAndFaculty(yearCode, facultyId);
  }

}
