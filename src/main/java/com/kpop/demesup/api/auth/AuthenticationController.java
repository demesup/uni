package com.kpop.demesup.api.auth;

import com.kpop.demesup.api.dto.request.AuthenticationRequest;
import com.kpop.demesup.api.dto.request.UserRequest;
import com.kpop.demesup.api.dto.response.AuthenticationResponse;
import com.kpop.demesup.api.dto.response.UserResponse;
import com.kpop.demesup.domain.User;
import com.kpop.demesup.exception.AlreadyExistsException;
import com.kpop.demesup.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

  AuthenticationManager authenticationManager;
  @Autowired
  UserService userService;
  JwtVerifier tokenService;

  @PutMapping
  public AuthenticationResponse auth(
      @RequestBody @Valid AuthenticationRequest authenticationRequest) throws AuthenticationException {
    tokenService.verifyUser(authenticationRequest.getEmail());
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getEmail(),
            authenticationRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = tokenService.generateToken(authenticationRequest.getEmail());
    return new AuthenticationResponse(token);
  }

  @PostMapping
  public UserResponse create(@Valid @RequestBody UserRequest request) {
    String email = request.getEmail();
    if (userService.findIdByEmail(email).isPresent()) {
      throw new AlreadyExistsException("User", email);
    }
    String phone = request.getPhone();
    if (userService.findIdByPhone(phone).isPresent()) {
      throw new AlreadyExistsException("User", phone);
    }
    User user = userService.create(request);
//    log.info("Created new company {}", request.getName());
    String token = tokenService.generateToken(request.getEmail());
    return UserResponse.fromEntity(user, token);
  }

}