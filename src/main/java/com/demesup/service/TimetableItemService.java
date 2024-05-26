package com.demesup.service;


import com.demesup.api.dto.request.CourseRequest;
import com.demesup.api.dto.request.LabRequest;
import com.demesup.api.dto.request.SeminarRequest;
import com.demesup.api.dto.response.item.TimetableItemResponse;
import com.demesup.api.dto.response.item.TimetableResponse;
import com.demesup.domain.Group;
import com.demesup.domain.Professor;
import com.demesup.domain.Year;

import java.util.List;
import java.util.Optional;

public interface TimetableItemService {
  TimetableResponse getTimetableStructure(Group group);

  Optional<TimetableItemResponse> findById(Long id);

  TimetableItemResponse createSeminar(SeminarRequest request, Group group, Professor professor);

  TimetableItemResponse createCourse(CourseRequest request, Year year, Professor professor);

  TimetableItemResponse createLab(LabRequest request, Group group, List<Professor> professors);

  TimetableResponse getSeminars(Group group);

  TimetableResponse getLabs(Group group);

  TimetableResponse getCourses(Group group);
}
