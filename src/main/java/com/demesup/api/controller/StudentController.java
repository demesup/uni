package com.demesup.api.controller;

import com.demesup.api.dto.request.StudentRequest;
import com.demesup.api.dto.response.stud.StudentDetailsResponse;
import com.demesup.api.dto.response.stud.StudentResponse;
import com.demesup.domain.Group;
import com.demesup.domain.Student;
import com.demesup.domain.User;
import com.demesup.domain.enums.YearCode;
import com.demesup.exception.AlreadyExistsException;
import com.demesup.exception.NotFoundException;
import com.demesup.repository.GroupRepository;
import com.demesup.service.FacultyService;
import com.demesup.service.GroupService;
import com.demesup.service.StudentService;
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
@RequestMapping("/students")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StudentController {
  GroupService groupService;
  YearService yearService;
  FacultyService facultyService;
  StudentService studentService;
  private final GroupRepository groupRepository;


  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<StudentDetailsResponse> getAll(@RequestParam(required = false) Optional<Long> groupId,
                                             @RequestParam(required = false) Optional<Long> yearId,
                                             @RequestParam(required = false) Optional<Long> facultyId,
                                             @RequestParam(required = false) Optional<YearCode> code,
                                             @AuthenticationPrincipal User user) {
    if (groupId.isPresent()) {
      if (groupService.findById(groupId.get()).isEmpty()) {
        throw new NotFoundException("Group", groupId.get());
      }

      return studentService.findAllByGroup(groupId.get())
          .stream().map(StudentDetailsResponse::fromEntity).toList();
    }

    if (yearId.isPresent()) {
      if (yearService.findById(yearId.get()).isEmpty()) {
        throw new NotFoundException("Year", yearId.get());
      }
      return studentService.findAllByYear(yearId.get()).stream().map(StudentDetailsResponse::fromEntity).toList();
    }

    if (code.isPresent()) {
      return studentService.findAllByYear(code.get())
          .stream().map(StudentDetailsResponse::fromEntity).toList();
    }

    if (facultyId.isPresent()) {
      if (facultyService.findById(facultyId.get()).isEmpty()) {
        throw new NotFoundException("Faculty", facultyId.get());
      }
      return studentService.findAllByFaculty(facultyId.get())
          .stream().map(StudentDetailsResponse::fromEntity).toList();

    }

    return studentService.findAll()
        .stream().map(StudentDetailsResponse::fromEntity).toList();
  }


  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public StudentDetailsResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return studentService.findById(id)
        .map(StudentDetailsResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Student", id));
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/group/{id}")
  public List<StudentDetailsResponse> getByGroup(@PathVariable Long id, @AuthenticationPrincipal User user) {
    groupService.findById(id).orElseThrow(() -> new NotFoundException("Group", id));
    return studentService.findAllByGroup(id)
        .stream().map(StudentDetailsResponse::fromEntity).toList();
  }


  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/year/{id}")
  public List<StudentDetailsResponse> getByYear(@PathVariable Long id, @AuthenticationPrincipal User user) {
    yearService.findById(id).orElseThrow(() -> new NotFoundException("Year", id));
    return studentService.findAllByYear(id)
        .stream().map(StudentDetailsResponse::fromEntity).toList();
  }


  @PostMapping
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public StudentResponse create(@Valid @RequestBody StudentRequest request, @AuthenticationPrincipal User user) {
    log.info("Received Student registration request {}", request);

    if (!request.getUniEmail().isEmpty() && studentService.findByUniEmail(request.getUniEmail()).isPresent()) {
      throw new AlreadyExistsException("Student", request.getUniEmail());
    }

    Optional<Group> group = groupService.findById(request.getGroupId());
    Student student;
    if (group.isPresent()) {
      student = studentService.create(request, group.get());
    } else {
      student = studentService.create(request);
    }

    log.info("Created new Student {}", student);
    return StudentResponse.fromEntity(student);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public StudentResponse update(@PathVariable Long id, @RequestBody @Valid StudentRequest request,
                                @AuthenticationPrincipal User user) {
    Optional<Student> byUniEmail = studentService.findByUniEmail(request.getUniEmail());
    if (byUniEmail.isPresent() && !byUniEmail.get().getId().equals(id)) {
      throw new AlreadyExistsException("Student", request.getUniEmail());
    }

    Student student = studentService.findById(id).orElseThrow(() -> new NotFoundException("Student", id));
    Optional<Group> group = groupService.findById(request.getGroupId());

    log.info("Updated Student {}", student.getUniEmail());
    return StudentResponse.fromEntity(studentService.update(student, request, group.orElse(null)));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Group currentGroup = groupService.findById(id)
        .orElseThrow(() -> new NotFoundException("Student", id));
    groupService.delete(currentGroup);
  }
}
