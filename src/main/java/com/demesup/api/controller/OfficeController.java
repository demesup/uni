package com.demesup.api.controller;

import com.demesup.api.dto.request.OfficeRequest;
import com.demesup.api.dto.response.office.OfficeResponse;
import com.demesup.domain.Faculty;
import com.demesup.domain.Office;
import com.demesup.domain.User;
import com.demesup.exception.AlreadyExistsException;
import com.demesup.exception.NotFoundException;
import com.demesup.service.FacultyService;
import com.demesup.service.OfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/offices")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OfficeController {
  OfficeService officeService;
  FacultyService facultyService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<OfficeResponse> getAll(@AuthenticationPrincipal User user) {

    return officeService.findAll().stream()
        .map(OfficeResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/faculty/{facultyId}")
  public OfficeResponse get(@AuthenticationPrincipal User user, @PathVariable Long facultyId) {
    Optional<Faculty> faculty = facultyService.findById(facultyId);
    if (faculty.isEmpty()) {
      throw new NotFoundException("Faculty", facultyId);
    }
    return officeService.findByFacultyId(facultyId).map(OfficeResponse::fromEntity).orElseThrow(() ->
        new NotFoundException("Office", "facultyId: " + facultyId));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public OfficeResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return officeService.findById(id)
        .map(OfficeResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Office", id));
  }

  @PostMapping
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public OfficeResponse create(@Valid @RequestBody OfficeRequest request, @AuthenticationPrincipal User user) {
    log.info("Received Office registration request {}", request);

    if (officeService.findByFacultyId(request.getFacultyId())
        .isPresent()) {
      throw new AlreadyExistsException("Office", "facultyId:" + request.getFacultyId());
    }

    Faculty faculty = facultyService.findById(request.getFacultyId())
        .orElseThrow(() -> new NotFoundException("Faculty", request.getFacultyId()));

    Office newOffice = officeService.create(request, faculty);
    log.info("Created new Office");
    return OfficeResponse.fromEntity(newOffice);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public OfficeResponse update(@PathVariable Long id, @RequestBody @Valid OfficeRequest request,
                               @AuthenticationPrincipal User user) {
    Optional<Office> found = officeService.findByFacultyId(request.getFacultyId());
    if (found.isPresent() && !found.get().getId().equals(id)) {
      throw new AlreadyExistsException("Office", "faculty: " + request.getFacultyId());
    }

    if (found.isEmpty()) {
      throw new NotFoundException("Office", id);
    }

    Optional<Faculty> faculty = facultyService.findById(request.getFacultyId());
    if (faculty.isEmpty()) {
      throw new NotFoundException("Faculty", request.getFacultyId());
    }
    log.info("Updated Office");
    return OfficeResponse.fromEntity(officeService.update(found.get(), request, faculty.get()));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Office currentOffice = officeService.findById(id)
        .orElseThrow(() -> new NotFoundException("Office", id));
    officeService.delete(currentOffice);
  }
}
