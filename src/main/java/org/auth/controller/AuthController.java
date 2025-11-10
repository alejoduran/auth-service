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

}
