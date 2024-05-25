package com.demesup.service.impl;

import com.demesup.api.dto.request.OfficeRequest;
import com.demesup.domain.Faculty;
import com.demesup.domain.Office;
import com.demesup.repository.OfficeRepository;
import com.demesup.service.OfficeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OfficeServiceImpl implements OfficeService {
  OfficeRepository repository;

  @Override
  public Optional<Office> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public void delete(Office office) {
    office.delete();
    save(office);
  }

  private Office save(Office office) {
    return repository.save(office);
  }

  @Override
  public Office update(Office office, OfficeRequest request, Faculty faculty) {
    office.update(request, faculty);
    return save(office);
  }

  @Override
  public Optional<Office> findByFacultyId(Long facultyId) {
    return repository.findByFacultyId(facultyId);
  }

  @Override
  public Office create(OfficeRequest request, Faculty faculty) {
    return save(Office.create(request, faculty));
  }

  @Override
  public List<Office> findAll() {
    return repository.findAll();
  }
}
