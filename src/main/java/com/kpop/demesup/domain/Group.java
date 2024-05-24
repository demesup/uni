package com.kpop.demesup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpop.demesup.api.dto.request.GroupRequest;
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
public class Group {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_seq")
  @SequenceGenerator(name = "groups_seq", sequenceName = "groups_seq", allocationSize = 1)
  Long id;

  String code;

  @ManyToOne
  @JoinColumn(name = "faculty_id")
  Faculty faculty;


  @ManyToOne
  @JoinColumn(name = "year_id")
  Year year;

  @ToString.Exclude
  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
  @Where(clause = "active = true")
  @OrderBy("id asc")
  @JsonIgnore
  @Builder.Default
  List<Student> students = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "head_id", referencedColumnName = "id")
  Student head;

  @OneToOne
  @JoinColumn(name = "advisor_id", referencedColumnName = "id")
  Professor advisor;

  @Column
  @Builder.Default
  Boolean active = true;

  public static Group create(GroupRequest request, Faculty faculty, Year year, Student head, Professor advisor) {
    return Group.builder()
        .code(request.getCode())
        .faculty(faculty)
        .year(year)
        .head(head)
        .advisor(advisor)
        .build();

  }


  public void update(GroupRequest request, Faculty faculty, Year year, Student head, Professor advisor) {
    code = request.getCode();
    this.advisor = advisor;
    this.faculty = faculty;
    this.year = year;
    this.head = head;
  }

  public void delete() {
    active = false;
  }
}
