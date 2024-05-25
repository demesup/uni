package com.demesup.domain;

import com.demesup.api.dto.request.FacultyRequest;
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
@Table(name = "faculties")
@FieldDefaults(level = PRIVATE)
public class Faculty {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculties_seq")
  @SequenceGenerator(name = "faculties_seq", sequenceName = "faculties_seq", allocationSize = 1)
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

  @OneToOne(optional = true)
  @JoinColumn(name = "dean_id")
  Professor dean;

  @OneToMany(mappedBy = "viceDeanOf", cascade = CascadeType.ALL)
  @Where(clause = "active = true")
  @OrderBy("id asc")
  @Builder.Default
  List<Professor> viceDeans = new ArrayList<>();


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

  public void setDean(Professor dean) {
    this.dean = dean;
    if (dean != null) {
      dean.setDeanOf(this);
    }
  }

  public void addViceDean(Professor viceDean) {
    this.viceDeans.add(viceDean);
    viceDean.setViceDeanOf(this);
  }

  public void removeViceDean(Professor viceDean) {
    this.viceDeans.remove(viceDean);
    viceDean.setViceDeanOf(null);
  }


}
