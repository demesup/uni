package com.kpop.demesup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpop.demesup.api.dto.request.GroupRequest;
import com.kpop.demesup.api.dto.request.OfficeRequest;
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
@Table(name = "offices")
@FieldDefaults(level = PRIVATE)
public class Office {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offices_seq")
  @SequenceGenerator(name = "offices_seq", sequenceName = "offices_seq", allocationSize = 1)
  Long id;

  @ManyToOne
  @JoinColumn(name = "faculty_id")
  Faculty faculty;

  @Column
  String email;

  @Column
  String phone;

  @Column
  @Builder.Default
  Boolean active = true;

  public static Office create(OfficeRequest request, Faculty faculty) {
    return Office.builder()
        .faculty(faculty)
        .email(request.getEmail())
        .phone(request.getPhone())
        .build();
  }


  public void update(OfficeRequest request, Faculty faculty) {
    email = request.getEmail();
    phone = request.getPhone();
    this.faculty = faculty;

  }

  public void delete() {
    active = false;
  }
}
