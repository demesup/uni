package com.demesup.api.dto.response.office;

import com.demesup.api.dto.response.fac.FacultyResponse;
import com.demesup.domain.Office;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OfficeResponse {
  Long id;
  FacultyResponse faculty;
  String email;
  String phone;


  public static OfficeResponse fromEntity(Office office) {
    if (office == null) return null;
    return OfficeResponse.builder()
        .id(office.getId())
        .faculty(FacultyResponse.fromEntity(office.getFaculty()))
        .email(office.getEmail())
        .phone(office.getPhone())
        .build();
  }
}
