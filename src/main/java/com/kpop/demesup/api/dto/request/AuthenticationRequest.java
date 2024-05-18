package com.kpop.demesup.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Builder
public class AuthenticationRequest {

  @NotBlank
  String email;

  @NotBlank
  String password;

}
