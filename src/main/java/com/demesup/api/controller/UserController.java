package com.demesup.api.controller;

import com.demesup.api.auth.JwtVerifier;
import com.demesup.api.dto.request.UserRequest;
import com.demesup.api.dto.response.UserResponse;
import com.demesup.domain.User;
import com.demesup.exception.AlreadyExistsException;
import com.demesup.exception.NotFoundException;
import com.demesup.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {
  JwtVerifier tokenService;

  UserService userService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<UserResponse> getAll(@AuthenticationPrincipal User user) {
    return userService.findAll().stream()
        .map(UserResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public UserResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return userService.findById(id)
        .map(UserResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("User", id));
  }

  @PostMapping
  public UserResponse create(@Valid @RequestBody UserRequest request) {
    log.info("Received user registration request {}", request);
    String email = request.getEmail();
    if (userService.findIdByEmail(email).isPresent()) {
      throw new AlreadyExistsException("User", email);
    }
    String phone = request.getPhone();
    if (userService.findIdByPhone(phone).isPresent()) {
      throw new AlreadyExistsException("User", phone);
    }
    User newUser = userService.create(request);
    log.info("Created new user {}", request.getEmail());
    return UserResponse.fromEntity(newUser, tokenService.generateToken(newUser.getEmail()));
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public UserResponse update(@PathVariable Long id, @RequestBody @Valid UserRequest request,
                             @AuthenticationPrincipal User user) {
    User currentUser = userService.findById(id)
        .orElseThrow(() -> new NotFoundException("User", id));
    Optional<Long> foundIdByEmail = userService.findIdByEmail(request.getEmail());
    if (foundIdByEmail.isPresent() && !foundIdByEmail.get().equals(id)) {
      throw new AlreadyExistsException("User", request.getEmail());
    }
    Optional<Long> foundIdByPhone = userService.findIdByPhone(request.getPhone());
    if (foundIdByPhone.isPresent() && !foundIdByPhone.get().equals(id)) {
      throw new AlreadyExistsException("User", request.getPhone());
    }
    log.info("Updated user {}", currentUser.getEmail());
    return UserResponse.fromEntity(userService.update(currentUser, request));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    User currentUser = userService.findById(id)
        .orElseThrow(() -> new NotFoundException("User", id));
    log.info("Deleted user {}", currentUser.getEmail());
    userService.delete(currentUser);
  }
}
