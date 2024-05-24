package com.kpop.demesup.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
@With
public class ProfessorRequest {

  @NotEmpty
  Long facultyId;

  @Email
  String email;

  @Pattern(regexp = "\\+?\\d{1,3}[\\s\\-]?\\(?\\d{1,3}\\)?([\\s\\-]?\\d){7}", message = "Invalid phone number")
  String phone;

  @NotEmpty
  String name;

  String description;

}
