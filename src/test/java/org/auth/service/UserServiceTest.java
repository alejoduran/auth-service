package org.auth.service;

import org.auth.exception.InvalidCredentialsException;
import org.auth.exception.UserAlreadyExistException;
import org.auth.exception.ValidationException;
import org.auth.model.User;
import org.auth.model.dto.LoginRequest;
import org.auth.model.dto.LoginResponse;
import org.auth.model.dto.UserRequest;
import org.auth.model.dto.UserResponse;
import org.auth.repository.UserRepository;
import org.auth.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private UserServiceImpl userService;

  private UserRequest userRequest;

  private UserRequest validUserRequest;
  private UserRequest invalidEmailRequest;
  private UserRequest invalidPasswordRequest;

  private User testUser;
  private LoginRequest loginRequest;

  @BeforeEach
  void setUp() {
    validUserRequest = UserRequest.builder().name("John Duran").email("john.duran@gmail.com")
        .password("Alejo1dur4").build();

    invalidEmailRequest = UserRequest.builder().name("John Duran").email("invalid-email")
        .password("Alejo1dur4").build();

    invalidPasswordRequest = UserRequest.builder().name("John Duran").email("john.duran@gmail.com")
        .password("passw").build();

    testUser = User.builder()
        .id(UUID.randomUUID())
        .name("John Duran")
        .email("john.duran@gmail.com")
        .password("Alejo1dur4")
        .isActive(true)
        .token("oldToken")
        .created(LocalDateTime.now())
        .lastLogin(LocalDateTime.now().minusDays(1))
        .build();
    loginRequest = LoginRequest.builder()
        .email("john.duran@gmail.com")
        .password("Alejo1dur4")
        .build();
  }

  @Test
  void testCreateUser_Success() {
    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(passwordEncoder.encode(anyString())).thenReturn("Alejo1dur4");
    when(jwtUtil.generateToken(anyString())).thenReturn("jwtToken");
    when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    UserResponse response = userService.createUser(validUserRequest);

    assertNotNull(response);
    assertNotNull(response.getToken());
    assertTrue(response.getIsActive());
    verify(userRepository, times(1)).save(any());
  }

  @Test
  void testCreateUser_EmailAlreadyExists() {
    when(userRepository.existsByEmail(anyString())).thenReturn(true);

    assertThrows(UserAlreadyExistException.class, () -> {
      userService.createUser(validUserRequest);
    });
  }

  @Test
  void testCreateUser_InvalidEmail() {
    assertThrows(ValidationException.class, () -> {
      userService.createUser(invalidEmailRequest);
    });
  }

  @Test
  void testCreateUser_InvalidPassword() {
    assertThrows(ValidationException.class, () -> {
      userService.createUser(invalidPasswordRequest);
    });
  }

  @Test
  void testLogin_Success() {

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    when(jwtUtil.generateToken(anyString())).thenReturn("newToken");
    when(userRepository.save(any(User.class))).thenReturn(testUser);
    when(jwtUtil.validateToken(anyString())).thenReturn(true);
    when(jwtUtil.isTokenExpired(anyString())).thenReturn(false);
    when(jwtUtil.extractEmail(anyString())).thenReturn("john.duran@gmail.com");

    LoginResponse response = userService.login(loginRequest);

    assertNotNull(response);
    verify(userRepository, times(1)).save(any(User.class));
    verify(jwtUtil, times(1)).generateToken(anyString());
  }

  @Test
  void testLogin_UserNotFound() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    assertThrows(InvalidCredentialsException.class, () -> {
      userService.login(loginRequest);
    });

    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void testLogin_InvalidPassword() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

    assertThrows(InvalidCredentialsException.class, () -> {
      userService.login(loginRequest);
    });

    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void testLogin_UserInactive() {

    testUser.setIsActive(false);
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    assertThrows(InvalidCredentialsException.class, () -> {
      userService.login(loginRequest);
    });

    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void testLogin_NullEmail() {

    LoginRequest request = LoginRequest.builder()
        .email(null)
        .password("password123")
        .build();

    assertThrows(InvalidCredentialsException.class, () -> {
      userService.login(request);
    });

    verify(userRepository, never()).findByEmail(anyString());
  }

  @Test
  void testLogin_NullPassword() {
    LoginRequest request = LoginRequest.builder()
        .email("john.duran@gmail.com")
        .password(null)
        .build();

    assertThrows(InvalidCredentialsException.class, () -> {
      userService.login(request);
    });

    verify(userRepository, never()).findByEmail(anyString());
  }

}
