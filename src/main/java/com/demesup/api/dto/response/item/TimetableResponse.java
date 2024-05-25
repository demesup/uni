package com.demesup.api.dto.response.item;

import com.demesup.domain.enums.Day;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimetableResponse {
  Map<Day, List<TimetableItemResponse>> timetable;

  public static TimetableResponse fromMap(Map<Day, List<TimetableItemResponse>> dayListMap) {
    return TimetableResponse.builder()
        .timetable(dayListMap)
        .build();
  }
}
