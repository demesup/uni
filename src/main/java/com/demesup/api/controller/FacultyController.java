package com.demesup.api.controller;

import com.demesup.api.dto.response.FacultyDetailsResponse;
import com.demesup.domain.User;
import com.demesup.service.FacultyService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/faculties")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FacultyController {
  FacultyService service;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<FacultyDetailsResponse> getAll(@AuthenticationPrincipal User user) {
    return service.findAll().stream()
        .map(FacultyDetailsResponse::fromEntity)
        .toList();
  }
}
