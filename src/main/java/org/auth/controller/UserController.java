package org.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @Operation(summary = " Create a new User")
  public ResponseEntity<UserResponse> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "User Data",
      required = true,
      content = @Content(schema = @Schema(implementation = UserRequest.class))
  )@Valid @RequestBody UserRequest userRequest) {
    UserResponse response = userService.createUser(userRequest);
    return ResponseEntity.ok(response);
  }

}
