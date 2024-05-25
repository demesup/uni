package com.demesup.service;

import com.demesup.api.dto.request.OfficeRequest;
import com.demesup.domain.Faculty;
import com.demesup.domain.Office;

import java.util.List;
import java.util.Optional;

public interface OfficeService {

  Optional<Office> findById(Long id);

  void delete(Office currentOffice);

  Office update(Office office, OfficeRequest request, Faculty faculty);

  Optional<Office> findByFacultyId(Long facultyId);

  Office create(OfficeRequest request, Faculty faculty);

  List<Office> findAll();

}
