package com.kpop.demesup.domain;

import com.kpop.demesup.api.dto.request.StudentRequest;
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
@Table(name = "students")
@FieldDefaults(level = PRIVATE)
public class Student {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "students_seq")
  @SequenceGenerator(name = "students_seq", sequenceName = "students_seq", allocationSize = 1)
  Long id;

  @Column
  @Builder.Default
  Boolean active = true;

  @JoinColumn(name = "group_id")
  @ManyToOne
  Group group;

  @Column
  String firstName;


  @Column
  String lastName;

  @Column
  String email;

  @Column
  String uniEmail;

  @Column
  String phone;

  public static Student create(StudentRequest request, Group group) {
    return Student.builder()
        .uniEmail(request.getUniEmail())
        .email(request.getEmail())
        .lastName(request.getLastName())
        .firstName(request.getFirstName())
        .phone(request.getPhone())
        .group(group)
        .build();
  }

  public void update(StudentRequest request, Group group) {
    firstName = request.getFirstName();
    lastName = request.getLastName();
    email = request.getEmail();
    phone = request.getPhone();
    uniEmail = request.getUniEmail();
    this.group = group;
  }

  public void delete() {
    active = false;
  }
}
