package org.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.auth.exception.InvalidTokenException;
import org.auth.model.dto.ErrorResponse;
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
  @Operation(
      summary = "User Authentication",
      description = "Authenticate a user with your credentials and return a JWT token"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Successfully Authentication",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = LoginResponse.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Entry Data invalid",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Invalid Credentials user not found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal Server Error"
      )
  })
  public ResponseEntity<LoginResponse> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Authentication Credentials",
      required = true,
      content = @Content(
          schema = @Schema(implementation = LoginRequest.class)
      )
  )@Valid @RequestBody LoginRequest loginRequest) {
    LoginResponse response = userService.login(loginRequest);
    return ResponseEntity.ok(response);
  }

}
