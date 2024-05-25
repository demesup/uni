package com.demesup.exception;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class NotFoundException extends RuntimeException {

  public NotFoundException(String entity, Long id) {
    super(entity + " with id=" + id + " is not found");
  }

  public NotFoundException(String entity, String key, Long id) {
    super(entity + " with " + key + "=" + id + " is not found");
  }

  public NotFoundException(String entity, String param) {
    super(entity + " with param=" + param + " is not found");
  }
}
