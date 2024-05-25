package com.demesup.api.controller;

import com.demesup.api.dto.request.GroupRequest;
import com.demesup.api.dto.response.group.GroupDetailsResponse;
import com.demesup.api.dto.response.group.GroupResponse;
import com.demesup.domain.*;
import com.demesup.domain.enums.YearCode;
import com.demesup.exception.AlreadyExistsException;
import com.demesup.exception.NotFoundException;
import com.demesup.service.*;
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
@RequestMapping("/groups")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class GroupController {
  GroupService groupService;
  YearService yearService;
  FacultyService facultyService;
  ProfessorService professorService;
  StudentService studentService;


  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<GroupDetailsResponse> getAll(@RequestParam(required = false) Optional<Long> yearId,
                                           @RequestParam(required = false) Optional<Long> facultyId,
                                           @RequestParam(required = false) Optional<YearCode> code,
                                           @AuthenticationPrincipal User user) {
    if (yearId.isPresent()) {
      if (yearService.findById(yearId.get()).isEmpty()) {
        throw new NotFoundException("Year", yearId.get());
      }
      return groupService.findAllByYearId(yearId.get()).stream().map(GroupDetailsResponse::fromEntity).toList();
    }

    if (code.isPresent()) {
      return groupService.findAllByYearCode(code.get())
          .stream().map(GroupDetailsResponse::fromEntity).toList();
    }

    if (facultyId.isPresent()) {
      if (facultyService.findById(facultyId.get()).isEmpty()) {
        throw new NotFoundException("Faculty", facultyId.get());
      }
      return groupService.findAllByFacultyId(facultyId.get())
          .stream().map(GroupDetailsResponse::fromEntity).toList();

    }

    return groupService.findAll()
        .stream().map(GroupDetailsResponse::fromEntity).toList();
  }


  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public GroupDetailsResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return groupService.findById(id)
        .map(GroupDetailsResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Group", id));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/code/{code}")
  public GroupDetailsResponse get(@PathVariable String code, @AuthenticationPrincipal User user) {
    return groupService.findByCode(code)
        .map(GroupDetailsResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Group", code));
  }

  @PostMapping
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public GroupResponse create(@Valid @RequestBody GroupRequest request, @AuthenticationPrincipal User user) {
    if (groupService.findByCode(request.getCode(), request.getYearId()).isPresent()) {
      throw new AlreadyExistsException("Group", request.getCode());
    }

    log.info("Received Group registration request {}", request);

    Year year = yearService.findById(request.getYearId())
        .orElseThrow(() -> new NotFoundException("Year", request.getYearId()));

    Group newGroup = groupService.create(request, year);
    log.info("Created new Group");
    return GroupResponse.fromEntity(newGroup);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public GroupResponse update(@PathVariable Long id, @RequestBody @Valid GroupRequest request,
                              @AuthenticationPrincipal User user) {
    Optional<Group> byCode = groupService.findByCode(request.getCode(), request.getYearId());
    if (byCode.isPresent() && !byCode.get().getId().equals(id)) {
      throw new AlreadyExistsException("Group", request.getCode());
    }

    Group currentGroup = groupService.findById(id)
        .orElseThrow(() -> new NotFoundException("Group", id));

    Year year = yearService.findById(request.getYearId())
        .orElseThrow(() -> new NotFoundException("Year", request.getYearId()));

    Optional<Professor> advisor = professorService.findById(request.getAdvisorId());

    Optional<Student> head = studentService.findById(request.getHeadId());

    log.info("Updated Group");
    return GroupResponse.fromEntity(groupService.update(currentGroup, request, year,
        advisor.orElse(null),
        head.orElse(null)));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Group currentGroup = groupService.findById(id)
        .orElseThrow(() -> new NotFoundException("Group", id));
    groupService.delete(currentGroup);
  }
}
