package com.kpop.demesup.domain;

import com.kpop.demesup.api.dto.request.UserRequest;
import com.kpop.demesup.domain.enums.UserAuthority;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

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
public class User implements UserDetails {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
  Long id;

  @Column
  String firstName;

  @Column
  String lastName;

  @Column
  String password;

  @Column(unique = true)
  String email;

  @Column
  @Enumerated(EnumType.STRING)
  UserAuthority authorities;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  LocalDateTime updatedAt;

  @Column
  @Builder.Default
  Boolean active = true;

  @Column(unique = true)
  String phone;


  public static User create(UserRequest request, String password) {
    return User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .phone(request.getPhone())
        .password(password)
        .authorities(Objects.requireNonNullElse(request.getAuthorities(), UserAuthority.ROLE_USER))
        .build();
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return active;
  }

  @Override
  public boolean isAccountNonLocked() {
    return active;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return active;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }

  public String getAuthoritiesString() {
    return String.valueOf(authorities);
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities.toString());
  }

  public boolean isOwner() {
    return authorities.equals(UserAuthority.ROLE_ADMIN);
  }

  public void update(UserRequest request) {
    firstName = request.getFirstName();
    lastName = request.getLastName();
    email = request.getEmail();
    phone = request.getPhone();
    authorities = request.getAuthorities();
  }

  public void delete() {
    active = false;
  }
}
