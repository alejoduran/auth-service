package org.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse {

  private UUID id;
  private LocalDateTime created;
  private LocalDateTime lastLogin;
  private String token;
  private Boolean isActive;
  private String name;
  private String email;
  private String password;
  private List<PhoneResponse> phones;

}
