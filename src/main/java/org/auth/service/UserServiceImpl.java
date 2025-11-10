package org.auth.service;

import org.auth.exception.InvalidCredentialsException;
import org.auth.exception.InvalidTokenException;
import org.auth.exception.UserAlreadyExistException;
import org.auth.exception.ValidationException;
import org.auth.model.Phone;
import org.auth.model.User;
import org.auth.model.dto.*;
import org.auth.repository.UserRepository;
import org.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
  private static final Pattern PASSWORD_PATTERN = Pattern.compile(
      "^(?=[^A-Z]*[A-Z][^A-Z]*$)(?=(?:.*?[0-9]){2})(?!.*?[0-9].*?[0-9].*?[0-9])[a-zA-Z0-9]{8,12}$");

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public UserResponse createUser(UserRequest userRequest) {
    if (userRepository.existsByEmail(userRequest.getEmail())) {
      throw new UserAlreadyExistException("Email already exists");
    }

    validateUserRequest(userRequest);

    User user = new User();
    user.setName(userRequest.getName());
    user.setEmail(userRequest.getEmail());
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

    if (userRequest.getPhones() != null) {
      user.setPhones(userRequest.getPhones().stream().map(this::convertToPhoneEntity)
          .collect(Collectors.toList()));
    }

    String token = jwtUtil.generateToken(userRequest.getEmail());
    user.setToken(token);
    user.setLastLogin(LocalDateTime.now());

    User savedUser = userRepository.save(user);

    return convertToResponse(savedUser);
  }

  private void validateUserRequest(UserRequest userRequest) {
    if (userRequest.getEmail() == null || !EMAIL_PATTERN.matcher(userRequest.getEmail())
        .matches()) {
      throw new ValidationException("Invalid email format");
    }

    if (userRequest.getPassword() == null || !PASSWORD_PATTERN.matcher(userRequest.getPassword())
        .matches()) {
      throw new ValidationException(
          "Invalid password, please try again");
    }
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
      throw new InvalidCredentialsException("Email and password are required");
    }

    Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
    if (userOptional.isEmpty()) {
      throw new InvalidCredentialsException("Invalid credentials");
    }

    User user = userOptional.get();

    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new InvalidCredentialsException("Invalid credentials");
    }

    if (!user.getIsActive()) {
      throw new InvalidCredentialsException("User account is disabled");
    }

    String newToken = jwtUtil.generateToken(user.getEmail());

    user.setToken(newToken);
    user.setLastLogin(LocalDateTime.now());
    User updatedUser = userRepository.save(user);

    return getUserByToken(newToken);
  }

  @Override
  public LoginResponse getUserByToken(String token) {

    if (!jwtUtil.validateToken(token) || jwtUtil.isTokenExpired(token)) {
      throw new InvalidTokenException("Invalid or expired token");
    }

    String email = jwtUtil.extractEmail(token);

    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isEmpty()) {
      throw new InvalidTokenException("User not found for token");
    }

    User user = userOptional.get();

    if (!token.equals(user.getToken())) {
      throw new InvalidTokenException("Token mismatch");
    }

    return convertToLoginResponse(user);
  }


  private Phone convertToPhoneEntity(PhoneRequest phoneRequest) {
    return Phone.builder().number(phoneRequest.getNumber()).cityCode(phoneRequest.getCityCode())
        .countryCode(phoneRequest.getCountryCode()).build();
  }

  private UserResponse convertToResponse(User user) {
    return UserResponse.builder().id(user.getId()).created(LocalDateTime.now())
        .lastLogin(user.getLastLogin()).token(user.getToken()).isActive(user.getIsActive()).build();
  }

  private LoginResponse convertToLoginResponse(User user) {
    LoginResponse response = LoginResponse.builder().id(user.getId()).created(user.getCreated())
        .lastLogin(user.getLastLogin()).token(user.getToken()).isActive(user.getIsActive())
        .name(user.getName()).email(user.getEmail()).password(user.getPassword()).build();

    if (user.getPhones() != null) {
      response.setPhones(
          user.getPhones().stream().map(this::convertToPhoneResponse).collect(Collectors.toList()));
    }

    return response;
  }

  private PhoneResponse convertToPhoneResponse(Phone phone) {
    return PhoneResponse.builder().number(phone.getNumber()).cityCode(phone.getCityCode())
        .countryCode(phone.getCountryCode()).build();
  }
}
