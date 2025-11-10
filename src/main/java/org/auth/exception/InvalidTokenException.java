package org.auth.exception;

public class InvalidTokenException extends CustomException {
  public InvalidTokenException(String message) {
    super(message, 402);
  }
}
