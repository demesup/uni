package com.demesup.service.impl;

import com.demesup.api.dto.request.CourseRequest;
import com.demesup.api.dto.request.LabRequest;
import com.demesup.api.dto.request.SeminarRequest;
import com.demesup.api.dto.response.item.*;
import com.demesup.domain.Group;
import com.demesup.domain.Professor;
import com.demesup.domain.TimetableItem;
import com.demesup.domain.Year;
import com.demesup.domain.enums.Day;
import com.demesup.domain.items.Course;
import com.demesup.domain.items.ItemInfo;
import com.demesup.domain.items.Lab;
import com.demesup.domain.items.Seminar;
import com.demesup.exception.NotFoundException;
import com.demesup.repository.TimetableItemRepository;
import com.demesup.service.GroupService;
import com.demesup.service.ProfessorService;
import com.demesup.service.TimetableItemService;
import com.demesup.service.YearService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TimetableItemServiceImpl implements TimetableItemService {
  TimetableItemRepository repository;
  YearService yearService;
  ProfessorService professorService;
  GroupService groupService;

  @Override
  public TimetableResponse getTimetableStructure(Group group) {
    List<TimetableItemResponse> responses = new ArrayList<>();
    List<TimetableItem> timetableItems = repository.findAll();

    for (TimetableItem item : timetableItems) {
      ItemInfo body = item.getBody();
      if (body instanceof Course) {
        handleCourseItem((Course) body, item, group, responses);
      } else if (body instanceof Lab) {
        handleLabItem((Lab) body, item, group, responses);
      } else if (body instanceof Seminar) {
        handleSeminarItem((Seminar) body, item, group, responses);
      } else {
        throw new NotFoundException("Class not found", "");
      }
    }
    return TimetableResponse.fromMap(mapByDay(responses));
  }

  @Override
  public Optional<TimetableItemResponse> findById(Long id) {
    Optional<TimetableItem> item = repository.findById(id);
    if (item.isEmpty()) {
      return Optional.empty();
    }
    ItemInfo body = item.get().getBody();
    if (body instanceof Course c) {
      return Optional.ofNullable(TimetableItemResponse.fromEntity(item.get(), CourseResponse.fromEntity(
          yearService.findById(c.getYearId()).orElse(null),
          professorService.findById(c.getProfessorId()).orElse(null)
      )));
    }

    if (body instanceof Lab l) {
      return Optional.ofNullable(TimetableItemResponse.fromEntity(item.get(),
          LabResponse.fromEntity(
              groupService.findById(l.getGroupId()).orElse(null),
              professorService.findAllByIds(l.getProfessorIds())
          )));
    }

    if (body instanceof Seminar s) {
      return Optional.ofNullable(TimetableItemResponse.fromEntity(
          item.get(),
          SeminarResponse.fromEntity(
              groupService.findById(s.getGroupId()).orElse(null),
              professorService.findById(s.getAssistantId()).orElse(null)
          )
      ));
    }
    return Optional.empty();
  }

  @Override
  public TimetableItemResponse createSeminar(SeminarRequest request, Group group, Professor professor) {
    Seminar seminar = Seminar.create(request.getGroupId(), request.getAssistantId());
    TimetableItem item = save(TimetableItem.create(request, seminar));
    return TimetableItemResponse.fromEntity(item, SeminarResponse.fromEntity(group, professor));
  }


  @Override
  public TimetableItemResponse createCourse(CourseRequest request, Year year, Professor professor) {
    Course course = Course.create(request.getYearId(), request.getProfessorId());
    TimetableItem item = save(TimetableItem.create(request, course));
    return TimetableItemResponse.fromEntity(item, CourseResponse.fromEntity(year, professor));
  }

  @Override
  public TimetableItemResponse createLab(LabRequest request, Group group, List<Professor> professors) {
    Lab lab = Lab.create(request.getGroupId(), request.getProfessorIds());
    TimetableItem item = save(TimetableItem.create(request, lab));
    return TimetableItemResponse.fromEntity(item, LabResponse.fromEntity(group, professors));
  }

  @Override
  public TimetableResponse getSeminars(Group group) {
    List<TimetableItemResponse> responses = new ArrayList<>();
    List<TimetableItem> timetableItems = repository.findAll();

    for (TimetableItem item : timetableItems) {
      ItemInfo body = item.getBody();
      if (body instanceof Seminar) {
        handleSeminarItem((Seminar) body, item, group, responses);
      }
    }
    return TimetableResponse.fromMap(mapByDay(responses));

  }

  @Override
  public TimetableResponse getLabs(Group group) {
    List<TimetableItemResponse> responses = new ArrayList<>();
    List<TimetableItem> timetableItems = repository.findAll();

    for (TimetableItem item : timetableItems) {
      ItemInfo body = item.getBody();
      if (body instanceof Lab) {
        handleLabItem((Lab) body, item, group, responses);
      }
    }
    return TimetableResponse.fromMap(mapByDay(responses));

  }

  @Override
  public TimetableResponse getCourses(Group group) {
    List<TimetableItemResponse> responses = new ArrayList<>();
    List<TimetableItem> timetableItems = repository.findAll();

    for (TimetableItem item : timetableItems) {
      ItemInfo body = item.getBody();
      if (body instanceof Course) {
        handleCourseItem((Course) body, item, group, responses);
      }
    }
    return TimetableResponse.fromMap(mapByDay(responses));

  }

  @Override
  public TimetableResponse getTimetableStructure(Year year) {
    List<TimetableItemResponse> responses = new ArrayList<>();
    List<TimetableItem> timetableItems = repository.findAll();

    for (TimetableItem item : timetableItems) {
      ItemInfo body = item.getBody();
      if (body instanceof Course) {
        handleCourseItem((Course) body, item, year, responses);
      }
    }
    return TimetableResponse.fromMap(mapByDay(responses));
  }

  private TimetableItem save(TimetableItem item) {
    return repository.save(item);
  }

  public static Map<Day, List<TimetableItemResponse>> mapByDay(List<TimetableItemResponse> responses) {
    Map<Day, List<TimetableItemResponse>> timetableMap = new HashMap<>();

    for (Day day : Day.values()) {
      timetableMap.put(day, new ArrayList<>());
    }

    for (TimetableItemResponse response : responses) {
      Day day = response.getDay();
      List<TimetableItemResponse> dayList = timetableMap.get(day);
      dayList.add(response);
    }

    return timetableMap;
  }

  private void handleCourseItem(Course course, TimetableItem item, Group group, List<TimetableItemResponse> responses) {
    yearService.findById(course.getYearId()).ifPresent(year -> {
      if (year.getId().equals(group.getYear().getId())) {
        professorService.findById(course.getProfessorId()).ifPresent(professor -> {
          responses.add(
              TimetableItemResponse.fromEntity(item, CourseResponse.fromEntity(year, professor))
          );
        });
      }
    });
  }

  private void handleCourseItem(Course course, TimetableItem item, Year year, List<TimetableItemResponse> responses) {
    if (year.getId().equals(course.getYearId())) {
      professorService.findById(course.getProfessorId()).ifPresent(professor -> {
        responses.add(
            TimetableItemResponse.fromEntity(item, CourseResponse.fromEntity(year, professor))
        );
      });
    }
  }

  private void handleLabItem(Lab lab, TimetableItem item, Group group, List<TimetableItemResponse> responses) {
    if (lab.getGroupId().equals(group.getId())) {
      List<Professor> professors = professorService.findAllByIds(lab.getProfessorIds());
      responses.add(TimetableItemResponse.fromEntity(item, LabResponse.fromEntity(group, professors)));
    }
  }

  private void handleSeminarItem(Seminar seminar, TimetableItem item, Group group, List<TimetableItemResponse> responses) {
    if (seminar.getGroupId().equals(group.getId())) {
      professorService.findById(seminar.getAssistantId()).ifPresent(professor -> {
        responses.add(TimetableItemResponse.fromEntity(item, SeminarResponse.fromEntity(group, professor)));
      });
    }
  }
}
