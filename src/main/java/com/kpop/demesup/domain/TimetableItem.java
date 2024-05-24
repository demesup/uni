package com.kpop.demesup.domain;

import com.kpop.demesup.domain.enums.Day;
import com.kpop.demesup.domain.enums.Frequency;
import com.kpop.demesup.domain.enums.Hour;
import com.kpop.demesup.domain.items.ItemInfo;
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
}
