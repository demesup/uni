package com.demesup.service;

import com.demesup.api.dto.request.YearRequest;
import com.demesup.domain.Faculty;
import com.demesup.domain.Year;
import com.demesup.domain.enums.YearCode;

import java.util.List;
import java.util.Optional;

public interface YearService {
  List<Year> findAll();

  Optional<Year> findById(Long id);

  Year create(YearRequest request, Faculty faculty);

  Year update(Year year, YearRequest request, Faculty faculty);

  void delete(Year year);

  List<Year> findAllByFacultyId(Long id);

  Optional<Long> findIdByYearAndFacultyId(YearCode yearCode, Long facultyId);
}
