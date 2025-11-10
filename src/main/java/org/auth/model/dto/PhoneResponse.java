package org.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PhoneResponse {
  private Long number;
  private Integer cityCode;
  private String countryCode;
}
