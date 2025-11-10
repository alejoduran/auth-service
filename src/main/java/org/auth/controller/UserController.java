package org.auth.controller;

import org.auth.model.dto.UserRequest;
import org.auth.model.dto.UserResponse;
import org.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
    UserResponse response = userService.createUser(userRequest);
    return ResponseEntity.ok(response);
  }

}
