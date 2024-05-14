package com.kpop.demesup.exception;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class AlreadyExistsException extends ValidationException {

  public AlreadyExistsException(String entityName, String param) {
    super(entityName + " with param: " + param + " already exists");
  }
}
