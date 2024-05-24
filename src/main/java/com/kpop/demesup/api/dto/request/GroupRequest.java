package com.kpop.demesup.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
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
public class GroupRequest {
  @NotEmpty
  String code;
  @NotEmpty
  Long facultyId;
  @NotEmpty
  Long yearId;

}
