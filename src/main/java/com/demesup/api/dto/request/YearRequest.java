package com.demesup.api.dto.request;

import com.demesup.domain.enums.YearCode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class YearRequest {
  @NotEmpty
  Long facultyId;

  @NotNull
  YearCode year;
}
