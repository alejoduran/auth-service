package org.auth.exception;

import org.auth.model.dto.ErrorDetail;
import org.auth.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    ErrorDetail errorDetail = new ErrorDetail(ex.getErrorCode(), ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(errorDetail));
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<ErrorDetail> errorDetails = ex.getBindingResult().getAllErrors().stream()
        .map(error -> {
          String fieldName = ((FieldError) error).getField();
          String errorMessage = error.getDefaultMessage();
          return new ErrorDetail(400, fieldName + ": " + errorMessage);
        })
        .collect(Collectors.toList());

    ErrorResponse errorResponse = new ErrorResponse(errorDetails);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
    List<ErrorDetail> errorDetails = ex.getConstraintViolations().stream()
        .map(violation -> new ErrorDetail(400, violation.getMessage()))
        .collect(Collectors.toList());

    ErrorResponse errorResponse = new ErrorResponse(errorDetails);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorDetail errorDetail = new ErrorDetail(500, "Internal server error: " + ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(errorDetail));
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
