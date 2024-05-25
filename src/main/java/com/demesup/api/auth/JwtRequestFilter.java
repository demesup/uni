package com.demesup.api.auth;

import com.auth0.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtRequestFilter extends OncePerRequestFilter {
  public static final String TOKEN_PREFIX = "Bearer ";

  JwtVerifier tokenService;

  UserDetailsService userDetailsService;

  private String getToken(HttpServletRequest request) {
    final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    return isNullOrEmpty(token) ? null : token.replace(TOKEN_PREFIX, "");
  }

  private static boolean isNullOrEmpty(String token) {
    return token == null || token.isEmpty();
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain chain) throws ServletException, IOException {

    final String token = getToken(request);
    if (!isNullOrEmpty(token)) {
      try {
        String username = tokenService.verify(JWT.decode(token));
        tokenService.verifyUser(username);
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user != null) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              user, null, user.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      } catch (Exception e) {
        log.warn("Invalid token {}. Msg {}", token, e.getMessage());
      }
    }
    chain.doFilter(request, response);

  }

}
