package com.demesup.api.dto.response.item;

import com.demesup.domain.TimetableItem;
import com.demesup.domain.enums.Day;
import com.demesup.domain.enums.Frequency;
import com.demesup.domain.enums.Hour;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimetableItemResponse {
  Day day;
  Hour hour;
  Integer duration;
  Frequency frequency;
  ItemInfoResponse info;
  String subject;
  String description;
  String note;

  public static TimetableItemResponse fromEntity(TimetableItem timetableItem, ItemInfoResponse itemInfo) {
    return TimetableItemResponse.builder()
        .day(timetableItem.getDay())
        .hour(timetableItem.getHour())
        .duration(timetableItem.getDuration())
        .frequency(timetableItem.getFrequency())
        .subject(timetableItem.getSubject())
        .note(timetableItem.getNote())
        .description(timetableItem.getDescription())
        .info(itemInfo)
        .build();
  }
}
