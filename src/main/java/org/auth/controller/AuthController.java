package org.auth.controller;

import org.auth.exception.InvalidTokenException;
import org.auth.model.dto.LoginRequest;
import org.auth.model.dto.LoginResponse;
import org.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    LoginResponse response = userService.login(loginRequest);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user")
  public ResponseEntity<LoginResponse> getUserByToken(
      @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

    if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
      logger.warn("Authorization header is missing or empty");
      throw new InvalidTokenException("Authorization header is required");
    }

    if (!authorizationHeader.startsWith("Bearer ")) {
      logger.warn("Invalid Authorization header format: {}", authorizationHeader);
      throw new InvalidTokenException("Authorization header must start with 'Bearer '");
    }

    String token = authorizationHeader.substring(7).trim();

    if (token.isEmpty()) {
      logger.warn("Token is empty after removing 'Bearer ' prefix");
      throw new InvalidTokenException("Token cannot be empty");
    }

    logger.debug("Extracted token length: {} characters", token.length());

    try {
      LoginResponse response = userService.getUserByToken(token);
      logger.info("User information retrieved for: {}", response.getEmail());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      logger.error("Error retrieving user by token: {}", e.getMessage());
      throw e;
    }
  }
}
