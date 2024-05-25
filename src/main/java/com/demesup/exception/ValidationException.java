package com.demesup.exception;

public abstract class ValidationException extends RuntimeException {

  protected ValidationException(String message) {
    super(message);
  }
}