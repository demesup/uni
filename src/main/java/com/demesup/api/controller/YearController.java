package com.demesup.api.controller;

import com.demesup.api.dto.request.YearRequest;
import com.demesup.api.dto.response.year.YearDetailsResponse;
import com.demesup.api.dto.response.year.YearGroupDetailsResponse;
import com.demesup.api.dto.response.year.YearResponse;
import com.demesup.domain.Faculty;
import com.demesup.domain.User;
import com.demesup.domain.Year;
import com.demesup.exception.AlreadyExistsException;
import com.demesup.exception.NotFoundException;
import com.demesup.service.FacultyService;
import com.demesup.service.YearService;
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
@RequestMapping("/years")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class YearController {
  YearService yearService;
  FacultyService facultyService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<YearDetailsResponse> getAll(@AuthenticationPrincipal User user) {

    List<Year> all = yearService.findAll();
    log.warn(all.toString());
    return all.stream()
        .map(YearDetailsResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/faculty/{facultyId}")
  public List<YearGroupDetailsResponse> getAll(@PathVariable Long facultyId, @AuthenticationPrincipal User user) {
    Optional<Faculty> faculty = facultyService.findById(facultyId);
    if (faculty.isEmpty()) {
      throw new NotFoundException("Faculty", facultyId);
    }
    return yearService.findAllByFacultyId(facultyId).stream().map(YearGroupDetailsResponse::fromEntity).toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public YearDetailsResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return yearService.findById(id)
        .map(YearDetailsResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Year", id));
  }

  @PostMapping
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public YearResponse create(@Valid @RequestBody YearRequest request, @AuthenticationPrincipal User user) {
    log.info("Received Year registration request {}", request);

    if (yearService.findIdByYearAndFacultyId(request.getYear(), request.getFacultyId())
        .isPresent()) {
      throw new AlreadyExistsException("Year", "year code inside faculty");
    }

    Faculty faculty = facultyService.findById(request.getFacultyId())
        .orElseThrow(() -> new NotFoundException("Faculty", request.getFacultyId()));

    Year newYear = yearService.create(request, faculty);
    log.info("Created new Year");
    return YearResponse.fromEntity(newYear);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public YearResponse update(@PathVariable Long id, @RequestBody @Valid YearRequest request,
                             @AuthenticationPrincipal User user) {
    Optional<Long> idByYearAndFacultyId = yearService.findIdByYearAndFacultyId(request.getYear(), request.getFacultyId());
    if (idByYearAndFacultyId.isPresent() && !idByYearAndFacultyId.get().equals(id)) {
      throw new AlreadyExistsException("Year", "year code inside faculty");
    }

    Year currentYear = yearService.findById(id)
        .orElseThrow(() -> new NotFoundException("Year", id));

    Optional<Faculty> faculty = facultyService.findById(request.getFacultyId());
    if (faculty.isEmpty()) {
      throw new NotFoundException("Faculty", request.getFacultyId());
    }
    log.info("Updated Year");
    return YearResponse.fromEntity(yearService.update(currentYear, request, faculty.get()));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Year currentYear = yearService.findById(id)
        .orElseThrow(() -> new NotFoundException("Year", id));
    yearService.delete(currentYear);
  }
}
