package com.demesup.api.controller;

import com.demesup.api.dto.request.CourseRequest;
import com.demesup.api.dto.request.LabRequest;
import com.demesup.api.dto.request.SeminarRequest;
import com.demesup.api.dto.response.item.TimetableItemResponse;
import com.demesup.api.dto.response.item.TimetableResponse;
import com.demesup.domain.Group;
import com.demesup.domain.Professor;
import com.demesup.domain.User;
import com.demesup.domain.Year;
import com.demesup.exception.NotFoundException;
import com.demesup.service.GroupService;
import com.demesup.service.ProfessorService;
import com.demesup.service.TimetableItemService;
import com.demesup.service.YearService;
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
@RequestMapping("/timetable")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TimetableItemController {
  TimetableItemService service;
  GroupService groupService;
  ProfessorService professorService;
  YearService yearService;


  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/{groupId}")
  public TimetableResponse getTimetableStructure(@PathVariable Long groupId, @AuthenticationPrincipal User user) {
    return service.getTimetableStructure(groupService.findById(groupId).orElseThrow(() -> new NotFoundException("Group", groupId)));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/year/{yearId}")
  public TimetableResponse getTimetableStructureByYear(@PathVariable Long yearId, @AuthenticationPrincipal User user) {
    return service.getTimetableStructure(yearService.findById(yearId).orElseThrow(() -> new NotFoundException("Year", yearId)));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/seminars/{groupId}")
  public TimetableResponse getSeminars(@PathVariable Long groupId, @AuthenticationPrincipal User user) {
    return service.getSeminars(groupService.findById(groupId).orElseThrow(() -> new NotFoundException("Group", groupId)));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/labs/{groupId}")
  public TimetableResponse getLabs(@PathVariable Long groupId, @AuthenticationPrincipal User user) {
    return service.getLabs(groupService.findById(groupId).orElseThrow(() -> new NotFoundException("Group", groupId)));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/courses/{groupId}")
  public TimetableResponse getCourses(@PathVariable Long groupId, @AuthenticationPrincipal User user) {
    return service.getCourses(groupService.findById(groupId).orElseThrow(() -> new NotFoundException("Group", groupId)));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/seminars")
  public TimetableResponse getSeminars(@RequestParam String code, @AuthenticationPrincipal User user) {
    return service.getSeminars(groupService.findByCode(code).orElseThrow(() -> new NotFoundException("Group", code)));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/labs")
  public TimetableResponse getLabs(@RequestParam String code, @AuthenticationPrincipal User user) {
    return service.getLabs(groupService.findByCode(code).orElseThrow(() -> new NotFoundException("Group", code)));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/courses")
  public TimetableResponse getCourses(@RequestParam String code, @AuthenticationPrincipal User user) {
    return service.getCourses(groupService.findByCode(code).orElseThrow(() -> new NotFoundException("Group", code)));
  }


  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/item/{id}")
  public TimetableItemResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return service.findById(id).orElseThrow(() -> new NotFoundException("Item", id));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @PostMapping("/seminars")
  public TimetableItemResponse createSeminar(@AuthenticationPrincipal User user, @RequestBody SeminarRequest request) {
    Optional<Professor> professor = professorService.findById(request.getAssistantId());
    if (professor.isEmpty()) {
      throw new NotFoundException("Assistant", request.getAssistantId());
    }
    Optional<Group> group = groupService.findById(request.getGroupId());
    if (group.isEmpty()) {
      throw new NotFoundException("Group", request.getGroupId());
    }
    return service.createSeminar(request, group.get(), professor.get());
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @PostMapping("/labs")
  public TimetableItemResponse createLab(@AuthenticationPrincipal User user, @RequestBody LabRequest request) {
    List<Professor> professors = professorService.findAllByIds(request.getProfessorIds());
    if (professors.isEmpty()) {
      throw new NotFoundException("Professors", request.getProfessorIds().toString());
    }
    Optional<Group> group = groupService.findById(request.getGroupId());
    if (group.isEmpty()) {
      throw new NotFoundException("Group", request.getGroupId());
    }
    return service.createLab(request, group.get(), professors);
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @PostMapping("/courses")
  public TimetableItemResponse createCourse(@AuthenticationPrincipal User user, @RequestBody CourseRequest request) {
    Optional<Professor> professor = professorService.findById(request.getProfessorId());
    if (professor.isEmpty()) {
      throw new NotFoundException("Professor", request.getProfessorId().toString());
    }
    Optional<Year> year = yearService.findById(request.getYearId());
    if (year.isEmpty()) {
      throw new NotFoundException("Group", request.getYearId());
    }
    return service.createCourse(request, year.get(), professor.get());
  }

}
