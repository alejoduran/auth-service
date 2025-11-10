package org.auth.service;

import org.auth.model.dto.LoginRequest;
import org.auth.model.dto.LoginResponse;
import org.auth.model.dto.UserRequest;
import org.auth.model.dto.UserResponse;

public interface UserService {
  UserResponse createUser(UserRequest userRequest);

  LoginResponse login(LoginRequest loginRequest);

  LoginResponse getUserByToken(String token);
}
