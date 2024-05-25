package com.demesup.api.controller;

import com.demesup.api.dto.request.ProfessorRequest;
import com.demesup.api.dto.response.prof.ProfessorDetailsResponse;
import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.domain.Faculty;
import com.demesup.domain.Professor;
import com.demesup.domain.User;
import com.demesup.exception.AlreadyExistsException;
import com.demesup.exception.NotFoundException;
import com.demesup.service.FacultyService;
import com.demesup.service.ProfessorService;
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
@RequestMapping("/professors")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProfessorController {
  ProfessorService professorService;
  FacultyService facultyService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<ProfessorDetailsResponse> getAll(@AuthenticationPrincipal User user) {
    return professorService.findAll().stream()
        .map(ProfessorDetailsResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/{facultyId}")
  public List<ProfessorResponse> getAll(@AuthenticationPrincipal User user, @PathVariable Long facultyId) {
    return professorService.findAllByFaculty(facultyId).stream()
        .map(ProfessorResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public ProfessorResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return professorService.findById(id)
        .map(ProfessorResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Professor", id));
  }

  @PostMapping
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public ProfessorResponse create(@Valid @RequestBody ProfessorRequest request, @AuthenticationPrincipal User user) {
    log.info("Received Professor registration request {}", request);
    String email = request.getEmail();
    if (professorService.findIdByEmail(email).isPresent()) {
      throw new AlreadyExistsException("Professor", email);
    }
    String phone = request.getPhone();
    if (professorService.findIdByPhone(phone).isPresent()) {
      throw new AlreadyExistsException("Professor", phone);
    }
    Professor newProfessor = professorService.create(request);
    log.info("Created new Professor {}", request.getEmail());
    return ProfessorResponse.fromEntity(newProfessor);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public ProfessorResponse update(@PathVariable Long id, @RequestBody @Valid ProfessorRequest request,
                                  @AuthenticationPrincipal User user) {
    Professor currentProfessor = professorService.findById(id)
        .orElseThrow(() -> new NotFoundException("Professor", id));
    Optional<Professor> foundIdByEmail = professorService.findIdByEmail(request.getEmail());
    if (foundIdByEmail.isPresent() && !foundIdByEmail.get().equals(id)) {
      throw new AlreadyExistsException("Professor", request.getEmail());
    }
    Optional<Professor> foundIdByPhone = professorService.findIdByPhone(request.getPhone());
    if (foundIdByPhone.isPresent() && !foundIdByPhone.get().equals(id)) {
      throw new AlreadyExistsException("Professor", request.getPhone());
    }

    Optional<Faculty> faculty = facultyService.findById(request.getFacultyId());
    if (faculty.isEmpty()) {
      throw new NotFoundException("Faculty", request.getFacultyId());
    }
    log.info("Updated Professor {}", currentProfessor.getEmail());
    return ProfessorResponse.fromEntity(professorService.update(currentProfessor, request, faculty.get()));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Professor currentProfessor = professorService.findById(id)
        .orElseThrow(() -> new NotFoundException("Professor", id));
    log.info("Deleted Professor {}", currentProfessor.getEmail());
    professorService.delete(currentProfessor);
  }
}
