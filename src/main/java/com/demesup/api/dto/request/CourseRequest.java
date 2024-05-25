package com.demesup.api.dto.request;

import com.demesup.domain.enums.Day;
import com.demesup.domain.enums.Frequency;
import com.demesup.domain.enums.Hour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
@With
public class CourseRequest {
  Day day;
  Hour hour;
  Integer duration;
  Frequency frequency;
  String subject;
  String description;
  String note;
  Long yearId;
  Long professorId;
}
