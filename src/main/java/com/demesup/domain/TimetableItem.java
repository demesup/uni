package com.demesup.domain;

import com.demesup.api.dto.request.CourseRequest;
import com.demesup.api.dto.request.LabRequest;
import com.demesup.api.dto.request.SeminarRequest;
import com.demesup.domain.enums.Day;
import com.demesup.domain.enums.Frequency;
import com.demesup.domain.enums.Hour;
import com.demesup.domain.items.ItemInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import static lombok.AccessLevel.PRIVATE;

@With
@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
@FieldDefaults(level = PRIVATE)
public class TimetableItem {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_seq")
  @SequenceGenerator(name = "items_seq", sequenceName = "items_seq", allocationSize = 1)
  Long id;

  @Column
  @Builder.Default
  Boolean active = true;

  @Column
  Integer duration;

  @Column
  @Enumerated(EnumType.STRING)
  Day day;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  ItemInfo body;

  @Column
  @Enumerated(EnumType.STRING)
  Hour hour;

  @Column
  String description;

  @Column
  String note;

  @Column
  @Enumerated(EnumType.STRING)
  Frequency frequency;

  @Column
  String subject;

  public static TimetableItem create(SeminarRequest request, ItemInfo info) {
    return TimetableItem.builder()
        .day(request.getDay())
        .hour(request.getHour())
        .description(request.getDescription())
        .note(request.getNote())
        .frequency(request.getFrequency())
        .subject(request.getSubject())
        .body(info)
        .build();
  }

  public static TimetableItem create(LabRequest request, ItemInfo info) {
    return TimetableItem.builder()
        .day(request.getDay())
        .hour(request.getHour())
        .description(request.getDescription())
        .note(request.getNote())
        .frequency(request.getFrequency())
        .subject(request.getSubject())
        .body(info)
        .build();
  }

  public static TimetableItem create(CourseRequest request, ItemInfo info) {
    return TimetableItem.builder()
        .day(request.getDay())
        .hour(request.getHour())
        .description(request.getDescription())
        .duration(request.getDuration())
        .note(request.getNote())
        .frequency(request.getFrequency())
        .subject(request.getSubject())
        .body(info)
        .build();
  }

  public void delete() {
    active = false;
  }


}
