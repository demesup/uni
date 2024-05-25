package com.demesup.service.impl;

import com.demesup.api.dto.response.item.*;
import com.demesup.domain.Group;
import com.demesup.domain.Professor;
import com.demesup.domain.TimetableItem;
import com.demesup.domain.enums.Day;
import com.demesup.domain.items.Course;
import com.demesup.domain.items.ItemInfo;
import com.demesup.domain.items.Lab;
import com.demesup.domain.items.Seminar;
import com.demesup.exception.NotFoundException;
import com.demesup.repository.TimetableItemRepository;
import com.demesup.service.ProfessorService;
import com.demesup.service.TimetableItemService;
import com.demesup.service.YearService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TimetableItemServiceImpl implements TimetableItemService {
  TimetableItemRepository repository;
  YearService yearService;
  ProfessorService professorService;

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
