package com.demesup.api.controller;

import com.demesup.api.dto.response.prof.ProfessorDetailsResponse;
import com.demesup.api.dto.response.prof.ProfessorResponse;
import com.demesup.domain.Professor;
import com.demesup.domain.User;
import com.demesup.exception.NotFoundException;
import com.demesup.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/deans")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DeanController {
  ProfessorService professorService;


  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<ProfessorDetailsResponse> getAll(@AuthenticationPrincipal User user) {
    return professorService.findAllDeans().stream()
        .map(ProfessorDetailsResponse::fromEntity)
        .toList();
  }


  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{facultyId}")
  public ProfessorResponse get(@PathVariable Long facultyId, @AuthenticationPrincipal User user) {
    return professorService.findDeanByFacultyId(facultyId).map(ProfessorDetailsResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Dean", facultyId));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Professor currentProfessor = professorService.findById(id)
        .orElseThrow(() -> new NotFoundException("Dean", id));
    log.info("Deleted Dean {}", currentProfessor.getEmail());
    professorService.delete(currentProfessor);
  }
}
