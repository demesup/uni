package com.demesup.api.configuration;

import com.demesup.api.auth.JwtRequestFilter;
import com.demesup.api.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfiguration {
  public static final ErrorResponse UNAUTHORIZED_RESPONSE = new ErrorResponse(SC_UNAUTHORIZED, SC_UNAUTHORIZED, "Unauthorized");

  JwtRequestFilter jwtRequestFilter;

  ObjectMapper objectMapper;

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .httpBasic().disable()
        .cors().and().csrf().disable();

    httpSecurity
        .exceptionHandling()
        .authenticationEntryPoint((request, response, authException) -> writeUnauthorized(response))
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf().disable().authorizeHttpRequests(auth ->
          auth.requestMatchers("swagger-ui/**", "swagger-ui**", "/v3/api-docs/**", "/v3/api-docs**").permitAll()
              .requestMatchers("/auth", "/login").permitAll()
              .requestMatchers(HttpMethod.POST, "/users").permitAll()
              .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
              .requestMatchers("/**").authenticated()
        );

    httpSecurity
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  private void writeUnauthorized(HttpServletResponse res) throws IOException {
    res.setContentType("application/json;charset=UTF-8");
    res.setStatus(SC_UNAUTHORIZED);
    res.getWriter().write(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE));
  }
}
