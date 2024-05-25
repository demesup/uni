package com.demesup.api.auth;

import com.demesup.api.dto.request.AuthenticationRequest;
import com.demesup.api.dto.response.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
  AuthenticationManager authenticationManager;
  JwtVerifier tokenService;

  @PostMapping
  public AuthenticationResponse auth(
      @RequestBody @Valid AuthenticationRequest authenticationRequest) throws AuthenticationException {
    System.out.println(authenticationRequest);
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

}
