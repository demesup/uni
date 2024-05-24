package com.kpop.demesup.domain;

import com.kpop.demesup.api.dto.request.ProfessorRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class Professor {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professors_seq")
  @SequenceGenerator(name = "professors_seq", sequenceName = "professors_seq", allocationSize = 1)
  Long id;

  @Column
  String name;

  @Column
  String email;

  @Column
  String phone;

  @Column
  String description;

  @JoinColumn(name = "faculty_id")
  @ManyToOne
  Faculty faculty;


  @Column
  @Builder.Default
  Boolean active = true;

  public static Professor create(ProfessorRequest request, Faculty faculty) {
    return Professor.builder()
        .name(request.getName())
        .email(request.getEmail())
        .phone(request.getPhone())
        .description(request.getDescription())
        .faculty(faculty)
        .build();
  }

  public void update(ProfessorRequest request, Faculty faculty) {
    name = request.getName();
    email = request.getEmail();
    phone = request.getPhone();
    description = request.getDescription();
    this.faculty = faculty;
  }

  public void delete() {
    active = false;
  }
}
