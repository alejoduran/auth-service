package org.auth.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponse {

    private UUID id;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
}
