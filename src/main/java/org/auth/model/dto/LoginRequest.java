package org.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginRequest {
  @NotBlank(message = "Email is mandatory")
  private String email;

  @NotBlank(message = "Password is mandatory")
  private String password;
}
