package com.demesup.api;

import com.demesup.api.dto.response.ErrorResponse;
import com.demesup.exception.NotFoundException;
import com.demesup.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

  public static final int ERROR_CODE_FIELD_VALIDATION_FAILED = 402;

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
    final String message = e.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + "=" + error.getRejectedValue() + ", " + error.getDefaultMessage())
        .findFirst()
        .orElse("Argument Not Valid");
    log.info(message);
    return error(BAD_REQUEST, ERROR_CODE_FIELD_VALIDATION_FAILED, message);
  }

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<ErrorResponse> handleException(AuthenticationException e) {
    return error(UNAUTHORIZED, UNAUTHORIZED.value(), e.getMessage());
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
    String error = e.getMessage();
    log.info(error);
    return error(NOT_FOUND, NOT_FOUND.value(), error);
  }

  @ExceptionHandler(value = {ValidationException.class})
  public ResponseEntity<ErrorResponse> handleException(ValidationException e) {
    String error = e.getMessage();
    log.info(error);
    return error(BAD_REQUEST, ERROR_CODE_FIELD_VALIDATION_FAILED, error);
  }
  @ExceptionHandler(value = {HttpMessageNotReadableException.class})
  public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e) {
    String error = e.getMessage();
    log.info(error);
    return error(BAD_REQUEST, ERROR_CODE_FIELD_VALIDATION_FAILED, error);
  }

  @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
  public ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException e) {
    String error = e.getMessage();
    log.info(error);
    return error(BAD_REQUEST, ERROR_CODE_FIELD_VALIDATION_FAILED, "Type is mismatched");
  }

  private ResponseEntity<ErrorResponse> error(HttpStatus status, int code, String message) {
    final ErrorResponse response = new ErrorResponse(status.value(), code, message);
    log.info("Error response: {}", response);
    return ResponseEntity
        .status(status)
        .header("Content-Type", "application/json")
        .body(response);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    String error = e.getMessage();
    log.info(error);
    return error(INTERNAL_SERVER_ERROR, 500, error);
  }
}
