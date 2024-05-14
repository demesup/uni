package com.kpop.demesup.api.dto.request;

import com.kpop.demesup.domain.enums.UserAuthority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UserRequest {

  @Email
  @NotEmpty
  String email;

  @NotEmpty
  @Size(min = 2, max = 256)
  String firstName;

  @NotEmpty
  @Size(min = 2, max = 256)
  String lastName;

  @NotEmpty
  @Size(min = 6, max = 256)
  String password;

  @NotEmpty
  @Pattern(regexp = "\\+?\\d{1,3}[\\s\\-]?\\(?\\d{1,3}\\)?([\\s\\-]?\\d){7}", message = "Invalid phone number")
  String phone;

  UserAuthority authorities;
}
