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
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/vicedeans")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ViceDeanController {
  ProfessorService professorService;


  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<ProfessorDetailsResponse> getAll(@RequestParam(required = false) Optional<Long> facultyId, @AuthenticationPrincipal User user) {
    return facultyId.map(professorService::findAllViceDeans)
        .orElse(professorService.findAllViceDeans()).stream()
        .map(ProfessorDetailsResponse::fromEntity)
        .toList();
  }


  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public ProfessorResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return professorService.findById(id).map(ProfessorDetailsResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Vice dean", id));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Professor currentProfessor = professorService.findById(id)
        .orElseThrow(() -> new NotFoundException("Vice dean", id));
    log.info("Deleted Vice dean {}", currentProfessor.getEmail());
    professorService.delete(currentProfessor);
  }
}
