package com.kpop.demesup.api.dto.response;

import com.kpop.demesup.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
public class UserResponse {

  Long id;
  String email;
  String phone;
  String firstName;
  String lastName;
  String authorities;
  Long companyId;
  String token;

  public static UserResponse fromEntity(User user, String token) {
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .phone(user.getPhone())
        .authorities(user.getAuthoritiesString())
        .token(token)
        .build();
  }
}
