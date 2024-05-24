package com.kpop.demesup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpop.demesup.api.dto.request.YearRequest;
import com.kpop.demesup.domain.enums.YearCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@With
@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "years")
@FieldDefaults(level = PRIVATE)
public class Year {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "years_seq")
  @SequenceGenerator(name = "years_seq", sequenceName = "years_seq", allocationSize = 1)
  Long id;

  @ToString.Exclude
  @OneToMany(mappedBy = "year", cascade = CascadeType.ALL)
  @Where(clause = "active = true")
  @OrderBy("id asc")
  @JsonIgnore
  @Builder.Default
  List<Group> groups = new ArrayList<>();


  @JoinColumn(name = "faculty_id")
  @ManyToOne
  Faculty faculty;

  @Column
  @Enumerated(EnumType.STRING)
  YearCode year;


  @Column
  @Builder.Default
  Boolean active = true;

  public static Year create(YearRequest request, Faculty faculty) {
    return Year.builder()
        .year(request.getYearCode())
        .faculty(faculty)
        .build();
  }

  public void update(YearRequest request, Faculty faculty) {
    this.year = request.getYearCode();
    this.faculty = faculty;
  }

  public void delete() {
    this.active = false;
  }

}
