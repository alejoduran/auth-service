package org.auth.exception;

public class UserAlreadyExistException extends CustomException {
  public UserAlreadyExistException(String message) {
    super(message, 400);
  }
}
