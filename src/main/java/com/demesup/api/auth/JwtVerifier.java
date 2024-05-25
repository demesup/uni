package com.demesup.api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demesup.domain.User;
import com.demesup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class JwtVerifier {

  private static final String ISSUER = "autopartner-service";

  @Value("90")
  private Integer accessTokenExpirationMinutes;
  @Value("sdhj")
  private String secret;

  @Autowired
  UserDetailsService userDetailsService;
  @Autowired
  UserService userService;


  public String generateToken(String subject) {
    final Date expDate = Date.from(LocalDateTime.now().plusMinutes(accessTokenExpirationMinutes)
        .atZone(ZoneId.systemDefault()).toInstant());
    return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(subject)
        .withExpiresAt(expDate)
        .sign(Algorithm.HMAC256(secret));
  }

  public String verify(DecodedJWT jwt) {
    if (ISSUER.equals(jwt.getIssuer())) {
      final DecodedJWT verified = JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
      return verified.getSubject();
    }
    return null;
  }


  public void verifyUser(String email) {
    User user = (User) userDetailsService.loadUserByUsername(email);
    userService.findIdByEmail(user.getEmail()).orElseThrow(() -> new AuthenticationException("Unauthorized") {
    }
    );
  }
}
