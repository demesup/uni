package com.kpop.demesup.domain;

import com.kpop.demesup.api.dto.request.FacultyRequest;
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
@Table(name = "users")
@FieldDefaults(level = PRIVATE)
public class Faculty {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
  Long id;

  @Column
  String fullName;

  @Column
  String shortName;

  @Column
  @Builder.Default
  Boolean active = true;

  @ToString.Exclude
  @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
  @Where(clause = "active = true")
  @OrderBy("id asc")
  @Builder.Default
  List<Professor> professors = new ArrayList<>();


  @ToString.Exclude
  @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
  @Where(clause = "active = true")
  @OrderBy("id asc")
  @Builder.Default
  List<Year> years = new ArrayList<>();


  public static Faculty create(FacultyRequest request) {
    return Faculty.builder()
        .fullName(request.getFullName())
        .shortName(request.getShortName())
        .build();
  }

  public void update(FacultyRequest request) {
    fullName = request.getFullName();
    shortName = request.getShortName();
  }

  public void delete() {
    active = false;
  }


}
