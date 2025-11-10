package org.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDetail {

  private LocalDateTime timestamp;
  private int codigo;
  private String detail;

  public ErrorDetail(int codigo, String detail) {
    this.timestamp = LocalDateTime.now();
    this.codigo = codigo;
    this.detail = detail;
  }

}
