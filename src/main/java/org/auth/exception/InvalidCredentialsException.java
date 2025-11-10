package org.auth.exception;

public class InvalidCredentialsException extends CustomException {
  public InvalidCredentialsException(String message) {
    super(message, 401);
  }
}
